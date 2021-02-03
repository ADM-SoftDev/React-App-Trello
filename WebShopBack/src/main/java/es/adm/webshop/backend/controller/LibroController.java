package es.adm.webshop.backend.controller;

import es.adm.webshop.backend.common.Constantes;
import es.adm.webshop.backend.common.Mensaje;
import es.adm.webshop.backend.common.Respuesta;
import es.adm.webshop.backend.dto.LibroDTO;
import es.adm.webshop.backend.error.ExcepcionBase;
import es.adm.webshop.backend.servicio.ILibrosService;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.lang.constant.ConstantDesc;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:8080"})
public class LibroController {

    @Autowired
    private ILibrosService libroServicio;

    Logger logger = LoggerFactory.getLogger(LibroController.class);

    /**
     * Obtener una lista de Libros filtrados
     * @param filtro --> getListaLibros(filtro)
     * @return Libro, por Filtro
     * @throws ExcepcionBase
     */
    @PostMapping(value = "/libro/filtro" , produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Respuesta<List<LibroDTO>>> getListaLibroByFiltro(@RequestBody LibroDTO filtro) throws ExcepcionBase {
        List<LibroDTO> lista = libroServicio.getListaLibros(filtro);
        Respuesta<List<LibroDTO>> response = new Respuesta<>();
        response.setDatos(lista);
        return new ResponseEntity<Respuesta<List<LibroDTO>>>(response, HttpStatus.OK);
    }

    /**
     * Obtener un  Libro a partir de su identificador
     * @param id
     * @return un Libro por codigo recibido
     */
    @GetMapping(value="/libros/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<LibroDTO>> getLibro(@PathVariable(value = "id") Integer id) {
        Respuesta<LibroDTO> response = new Respuesta<>();
        try {
            LibroDTO libro = libroServicio.getFindById(id);
            response.setDatos(libro);
            return new ResponseEntity<Respuesta<LibroDTO>>(response, HttpStatus.OK);
        } catch (ExcepcionBase e) {
            response.addMensaje(new Mensaje(e.getCodigo(), e.getMensaje(), "W"));
            if (Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO == e.getCodigo()) {
                return new ResponseEntity<Respuesta<LibroDTO>>(response, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Respuesta<LibroDTO>>(response, HttpStatus.OK);
            }
        }
    }

    /**
     * Obtener una Lista de Libros a partir de su titulo o autor
     * @param filtro
     * @return Lista de Libros, filtrado por su titulo o autor
     * @throws ExcepcionBase
     */
    @GetMapping(value="/libros/tituloAutor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<List<LibroDTO>>> getFiltrarLibroTituloAutor(@RequestParam(name = "filtro") String filtro) throws ExcepcionBase{
            Respuesta<List<LibroDTO>> response = new Respuesta<>();
            List<LibroDTO> lista = libroServicio.getListaLibroTituloAutor(filtro);
            response.setDatos(lista);
            return new ResponseEntity<Respuesta<List<LibroDTO>>>(response, HttpStatus.OK);
    }

}
