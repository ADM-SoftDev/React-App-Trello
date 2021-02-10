package es.adm.webshop.backend.controller;

import es.adm.webshop.backend.common.Constantes;
import es.adm.webshop.backend.common.EstadoPedido;
import es.adm.webshop.backend.common.Mensaje;
import es.adm.webshop.backend.common.Respuesta;
import es.adm.webshop.backend.dto.PedidoDTO;
import es.adm.webshop.backend.error.ExcepcionBase;
import es.adm.webshop.backend.servicio.IPedidoService;
import org.mockito.internal.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = {"http://localhost:8080"})
public class PedidoController {

    @Autowired
    IPedidoService servicioPedidos;

    Logger logger = LoggerFactory.getLogger(PedidoController.class);

    Respuesta<List<PedidoDTO>> listaresponse = new Respuesta<>();
    Respuesta<PedidoDTO> response = new Respuesta<>();

    /**
     * Obtener lista de Pedidos
     * Filtrar por campo localizador,
     * @return ListaPedidos
     * @autor: Alexander Delgado
     */
    @GetMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<List<PedidoDTO>>> buscarPedidos(@PathVariable (name="localizador", required = false) String localizador) throws ExcepcionBase {

        List<PedidoDTO> listaPedido = new ArrayList<>();
        if(!StringUtils.isEmpty(localizador)){
            PedidoDTO pedido = localizadorPedido(localizador);
            if(pedido!=null){
                listaPedido.add(pedido);
            }else {
                listaPedido.addAll(servicioPedidos.getListaPedidos());
            }
        }


        if(listaPedido.isEmpty()){
            logger.info("Pedido no encontrado: " +listaresponse.getDatos());
            return new ResponseEntity<Respuesta<List<PedidoDTO>>>( listaresponse, HttpStatus.NOT_FOUND);
        } else {
            listaresponse.setDatos(listaPedido);
            return new ResponseEntity<Respuesta<List<PedidoDTO>>>( listaresponse, HttpStatus.OK);
        }
    }

    /**
     * Cambiar Estado o modificar un Pedido
     * @param id pasa al metodo updateEstadoPedido
     * @param pedido pasa al metodo updateEstadoPedido
     * @return pedido
     * @throws ExcepcionBase
     */
    @PutMapping(value = "/pedidos/{id}", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseEntity<Respuesta<PedidoDTO>> cambiarEstadoPedido(@PathVariable (name = "id") Integer id, @RequestBody PedidoDTO pedido ) throws ExcepcionBase{

        HttpStatus status = null;
        try{
            EstadoPedido estado = EstadoPedido.valueOf(pedido.getEstado());
            if(estado != null) {
                PedidoDTO pedidoBD = servicioPedidos.updateEstadoPedido(id, estado);
                response.setDatos(pedidoBD);
                status = HttpStatus.ACCEPTED;
            }else {
                logger.error("Error : Estado Invalido" + pedido.getEstado() );
                ExcepcionBase eb = new ExcepcionBase(Constantes.CODIGO_ERROR_SERVIDOR, Constantes.MSJE_ERROR_SERVIDOR);
                throw eb;
            }
        }catch (ExcepcionBase ex){
            response.addMensaje(new Mensaje(ex.getCodigo(),ex.getMensaje(),  "E",ex.getOrigen()));
        }
        return new ResponseEntity<Respuesta<PedidoDTO>>( response, status);
    }

    /**
     * Eliminar Pedido,
     * @param id de un pedido --> deletePedido(id)
     * @return status
     * @throws ExcepcionBase
     */
    @DeleteMapping(value= "/pedido/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<Void>> eliminarPedido(@PathVariable (name = "id") Integer id) throws ExcepcionBase {

        Respuesta<Void> respuesta = new Respuesta<>();
        HttpStatus status = null;
        try{
            servicioPedidos.deletePedido(id);
            respuesta.addMensaje(new Mensaje(Constantes.CODIGO_ok, Constantes.MSJE_REGISTRO_ELIMINADO,"P"));
            status = HttpStatus.CREATED;
        }catch (ExcepcionBase eb) {
            respuesta.addMensaje(new Mensaje(eb.getCodigo(), eb.getMensaje(),"E",eb.getOrigen()));
            if(eb.getCodigo() == Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO){
                status = HttpStatus.NOT_FOUND;
            }else {
                status = HttpStatus.OK;
            }
        }
        return new ResponseEntity<Respuesta<Void>>(respuesta,status);
    }

    /**
     * Localizar un pedido por su identificador numerico
     * @param id -->getPedidoById()
     * @return Pedido
     * @throws ExcepcionBase
     */
    @GetMapping(value ="/pedido/{idPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<PedidoDTO>> getLocalizarPedido(@PathVariable (name="idPedido") Integer id)throws ExcepcionBase{

        PedidoDTO pedido = null;
        try{
            pedido = servicioPedidos.getPedidosById(id);

        }catch(ExcepcionBase eb){
            logger.warn("No existe un pedido con ese Id de pedidos, verifique que sea correcto ! : " + id);
        }
        if(pedido == null){
            return new ResponseEntity<Respuesta<PedidoDTO>>(response, HttpStatus.NO_CONTENT);
        }else {
            response.setDatos(pedido);
            return new ResponseEntity<Respuesta<PedidoDTO>>(response, HttpStatus.OK);
        }
    }

    /**
     * Metodo auxiliar para localizar un pedido por localizador
     * @param localizador : getPedidoByLocalizador
     * @return Pedido
     */
    private PedidoDTO localizadorPedido(String localizador) {

        PedidoDTO pedido = null;
        try{
            pedido = servicioPedidos.getPedidosByLocalizador(localizador);

        }catch (ExcepcionBase eb){
             logger.warn("No existe un pedido con ese localizador de pedidos, verifique que sea correcto ! : " + localizador);
        }
        return pedido;
    }
}
