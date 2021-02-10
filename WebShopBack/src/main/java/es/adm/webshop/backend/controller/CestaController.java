package es.adm.webshop.backend.controller;

import es.adm.webshop.backend.Utiles.Localizador;
import es.adm.webshop.backend.common.Constantes;
import es.adm.webshop.backend.common.EstadoPedido;
import es.adm.webshop.backend.common.Mensaje;
import es.adm.webshop.backend.common.Respuesta;
import es.adm.webshop.backend.dto.*;
import es.adm.webshop.backend.error.ExcepcionBase;
import es.adm.webshop.backend.servicio.ILibrosService;
import es.adm.webshop.backend.servicio.IPedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = {"http://localhost:8080"})
public class CestaController {

    @Autowired
    ILibrosService libroServicio;

    @Autowired
    IPedidoService pedidoService;

    Logger logger = LoggerFactory.getLogger(CestaController.class);

    private static Map<String, CestaDTO> carritoCompra = new HashMap<>();

    /**
     *Crear una Cesta. se le asignara in localizador y crea un nuevo carrito de compra
     * @return cesta
     * @throws ExcepcionBase
     */
    @PostMapping(value = "/cesta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<CestaDTO>> crearCesta() throws ExcepcionBase {

        CestaDTO cesta =  new CestaDTO();
        cesta.setEstado(Constantes.ESTADO_00);
        cesta.setFecha(new Date());

        do {
            cesta.setLocalizador(Localizador.genenerarLocalizador12());
        }while(CestaController.carritoCompra.containsKey(cesta.getLocalizador()));

        CestaController.carritoCompra.put(cesta.getLocalizador(),cesta);
        cesta.setItems(new ArrayList<>());

        Respuesta<CestaDTO> respuesta = new Respuesta<>();
        respuesta.setDatos(cesta);

        return new ResponseEntity<Respuesta<CestaDTO>>(respuesta, HttpStatus.OK);
    }

    /**
     *Obtener una cesta, a traves de su localizador,
     * @param localizador
     * containsKey,Metodo comprueba si existe una cesta con el localizador del objeto Map, invoca a get obtener una referencia
     * @return El contenido del carrito de compra
     * @throws ExcepcionBase
     */
    @GetMapping(value = "/cesta/{localizador}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<CestaDTO>> getCestabyLocalizador(@PathVariable(name = "localizador") String localizador ) throws ExcepcionBase {

        CestaDTO cesta = null;
        if(CestaController.carritoCompra.containsKey(localizador)){
            cesta = CestaController.carritoCompra.get(localizador);
        }

        Respuesta<CestaDTO> response = new Respuesta<>();
        if(cesta != null){
            response.setDatos(cesta);
            return new ResponseEntity<Respuesta<CestaDTO>>( response, HttpStatus.OK);
        }else {
            response.addMensaje(new Mensaje(Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO,Constantes.MSJE_NO_EXISTE_REGISTRO, localizador ));
            return new ResponseEntity<Respuesta<CestaDTO>>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     *Actualizar Cesta de la Compra de Libros,Añade un libro al carrito de la compra,crando un pedido
     * @param localizador
     * @param item
     * @return Datos del pedido
     * @throws ExcepcionBase
     */
    @PostMapping(value="/cesta/{localizador}/items", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Respuesta<CestaDTO>> updateItemsCesta(@PathVariable(name="localizador") String localizador, @RequestBody ItemCestaDTO item)throws ExcepcionBase {

        CestaDTO cesta = null;

        Respuesta<CestaDTO> respuesta = new Respuesta<>();
        ItemCestaDTO itemBD = null;
        LibroDTO libro = null;
        try {


            if (CestaController.carritoCompra.containsKey(localizador)) {
                CestaController.carritoCompra.get(localizador);
                Integer idLibro = item.getLibro().getIdLibro();
                libro = libroServicio.getLibroById(
                        item.getLibro().getIdLibro());
                //libro = libroServicio.getFindById(item.getLibro().getIdLibro());
                itemBD = buscarItem(cesta,
                        item.getLibro().getIdLibro());
                //itemBD = buscarItem(cesta, item.getLibro().getIdLibro());

                if (item == null && item.getUnidades() > 0) {
                    itemBD = new ItemCestaDTO();
                    itemBD.setLibro(libro);
                    itemBD.setUnidades(item.getUnidades());
                    cesta.getItems().add(itemBD);
                } else {
                    if (item.getUnidades() > 0) {
                        itemBD.setUnidades(item.getUnidades());
                    } else {
                        cesta.getItems().remove(itemBD);
                    }
                }
                respuesta.setDatos(cesta);
                return new ResponseEntity<Respuesta<CestaDTO>>(respuesta, HttpStatus.CREATED);
            } else {
                respuesta.addMensaje(new Mensaje(Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO, Constantes.MSJE_NO_EXISTE_REGISTRO, localizador));
                return new ResponseEntity<Respuesta<CestaDTO>>(respuesta, HttpStatus.NOT_FOUND);
            }
        } catch (ExcepcionBase eb){
            respuesta.addMensaje(new Mensaje(eb.getCodigo(), eb.getMensaje(), localizador));
            if(eb.getCodigo() == Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO) {
                return new ResponseEntity<Respuesta<CestaDTO>>(respuesta, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Respuesta<CestaDTO>>(respuesta, HttpStatus.ACCEPTED);
            }
        }
    }

    /**
     * Crear Pedido a partir de Cesta de la Compra,Confima los datos del carrito de la compra, creando el pedido
     * @param localizador
     * @param pedido
     * @return Datos del Pedido
     * @throws ExcepcionBase
     */
    @PostMapping(value ="/cesta/{localizador}/pedido", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Respuesta<PedidoDTO>> crearPedido(@PathVariable (name="localizador") String localizador, @RequestBody PedidoDTO pedido)throws ExcepcionBase{

        CestaDTO cesta = null;
        Respuesta<PedidoDTO> respuesta = new Respuesta<>();
        try{
            if(CestaController.carritoCompra.containsKey(localizador)){
                CestaController.carritoCompra.get(localizador);
                if(cesta.getItems() != null && !cesta.getItems().isEmpty()){
                    pedido.setLocalizador(localizador);
                    pedido.setEstado(EstadoPedido.CREADO.name());
                    pedido.setItems(new ArrayList<>());
                    pedido.setFecha(new Date());

                    for (ItemCestaDTO item:cesta.getItems()) {
                        ItemPedidoDTO itemPedido = new ItemPedidoDTO();
                        itemPedido.setIdLibro(item.getLibro().getIdLibro());
                        itemPedido.setTitulo(item.getLibro().getTitulo());
                        itemPedido.setPrecio(item.getLibro().getPrecio());
                        itemPedido.setUnidades(item.getLibro().getNumeroCopias());
                    }

                    PedidoDTO pedidoBD = pedidoService.createPedido(pedido);
                    respuesta.setDatos(pedidoBD);
                    //Eliminar Pedido
                    CestaController.carritoCompra.remove(localizador);
                }

            } else {
                ExcepcionBase e = new ExcepcionBase(
                        Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO, Constantes.MSJE_NO_EXISTE_REGISTRO, localizador
                );throw e;

               }
        } catch (ExcepcionBase eb){
            respuesta.addMensaje( new Mensaje(eb.getCodigo(),eb.getMensaje(), eb.getOrigen()));
            if(eb.getCodigo() == Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO){
                return new ResponseEntity<Respuesta<PedidoDTO>>(respuesta, HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<Respuesta<PedidoDTO>>(respuesta, HttpStatus.OK);
            }

        }
    return new ResponseEntity<Respuesta<PedidoDTO>>(respuesta, HttpStatus.CREATED);
    }

    /**
     *
     * @param cesta
     * @param idLibro
     * @return
     */
    private ItemCestaDTO buscarItem(CestaDTO cesta, Integer idLibro){
        ItemCestaDTO item = null;

        for (ItemCestaDTO it:cesta.getItems()){
            if(it.getLibro().getIdLibro().equals(idLibro)){
                item = it;
                break;
            }
        }
        return item;
    }

}
