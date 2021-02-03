package es.adm.webshop.backend.controller;

import es.adm.webshop.backend.common.Respuesta;
import es.adm.webshop.backend.dto.CategoriaDTO;
import es.adm.webshop.backend.error.ExcepcionBase;
import es.adm.webshop.backend.servicio.ICategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:8080"})
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    /**
     * Listado de Categoria
     * No recibe parametros,
     * @return Lista de Categoria
     * @throws ExcepcionBase
     */
    @GetMapping(value="/categorias" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta<List<CategoriaDTO>>> getListaCategorias() throws ExcepcionBase {

        List<CategoriaDTO> categorias = categoriaService.getListaCetegorias();
        Respuesta<List<CategoriaDTO>> response = new Respuesta<>();

        if(categorias == null || categorias.isEmpty()){
            return new ResponseEntity<Respuesta<List<CategoriaDTO>>>(response,HttpStatus.NO_CONTENT);
        }else {
            response.setDatos(categorias);
            return new ResponseEntity<Respuesta<List<CategoriaDTO>>>(response, HttpStatus.OK);
        }
    }
}
