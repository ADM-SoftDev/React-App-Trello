package es.adm.webshop.backend.servicio.impl;

import es.adm.webshop.backend.mappers.LibroMapper;
import es.adm.webshop.backend.common.Constantes;
import es.adm.webshop.backend.dto.LibroDTO;
import es.adm.webshop.backend.error.ExcepcionBase;
import es.adm.webshop.backend.modelo.LibroEntity;
import es.adm.webshop.backend.repository.LibroRepository;
import es.adm.webshop.backend.servicio.ILibrosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicioImpl implements ILibrosService {

    static Logger logger = LoggerFactory.getLogger(LibroServicioImpl.class);

    LibroRepository libroRepo;

    /**
     * Repositorio de libros
     * @param libroRepo
     */
    @Autowired
    public LibroServicioImpl(LibroRepository libroRepo) {
        this.libroRepo = libroRepo;
    }

    /**
     * Obtener lista de filtrada de los libros
     * @param filtro(ILibro, titulo, autor,categoria, descripcion)
     * @return lista de libros que cumple el filtro, pasado como argumento
     * @throws ExcepcionBase
     */
    @Override
    public List<LibroDTO> getListaLibros(LibroDTO filtro) throws ExcepcionBase {
        Integer idCategoria = filtro.getIdCategoria();
        List<LibroEntity> lista = null;
        if(filtro != null){
            lista = libroRepo.findFiltered(
                    filtro.getIdLibro(),
                    filtro.getIdCategoria(),
                    filtro.getTitulo(),
                    filtro.getIsbn(),
                    filtro.getAutor()
            );
        } else {
            lista = libroRepo.findFiltered(null,null,null,null,null);
        }
        List<LibroDTO> resul = LibroMapper.toLibroDto(lista);
        return resul;
    }

    /**
     *Obtener lista de libros por titulo o autor
     * @param filtro
     * @return lista de todos libros existentes en la BBDD,que coincida con el filtro
     * @throws ExcepcionBase
     */
    @Override
    public List<LibroDTO> getListaLibroTituloAutor(String filtro) throws ExcepcionBase {

        List<LibroEntity> lista = libroRepo.findByTituloAutor("%"+filtro+"%");
        List<LibroDTO> resultado = LibroMapper.toLibroDto(lista);
        return resultado;
    }

    /**
     * Obtener un libro a partir de su identificador
     * @param id
     * @return objeto Libro a partir de su identificador
     * @throws ExcepcionBase
     */
    @Override
    public LibroDTO getLibroById(Integer id) throws ExcepcionBase {
        Optional <LibroEntity> entity = libroRepo.findById(id);
        if(entity.isPresent()){
            logger.warn("No existe el registro :" + id);
            ExcepcionBase eb = new ExcepcionBase(
                    Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO, Constantes.MSJE_NO_EXISTE_REGISTRO, Integer.toString(id)
            );
            throw eb;
        }
        return (LibroDTO) LibroMapper.toLibroDto(entity);
    }
}
