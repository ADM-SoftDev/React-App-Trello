package es.adm.webshop.backend.servicio;

import es.adm.webshop.backend.dto.LibroDTO;
import es.adm.webshop.backend.error.ExcepcionBase;

import java.util.List;
import java.util.Optional;

public interface ILibrosService {

    public List<LibroDTO> getListaLibros(LibroDTO filtro) throws ExcepcionBase;
    public List<LibroDTO> getListaLibroTituloAutor(String filtro) throws ExcepcionBase;
    public LibroDTO getLibroById(Integer id) throws ExcepcionBase;
}
