package es.adm.webshop.backend.mappers;

import es.adm.webshop.backend.dto.LibroDTO;
import es.adm.webshop.backend.modelo.LibroEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroMapper {

    public static List<LibroDTO> toLibroDto(List<LibroEntity> entidades) {
        List<LibroDTO> libroDto = new ArrayList<>();
        if(entidades != null) {
            for (LibroEntity entidad:entidades) {
               // libroDto.add(toLibroDto(Optional.ofNullable(entidad)));
                libroDto.add(toLibroDto(Optional.ofNullable(entidad)));
            }
        }
        return libroDto;
    }

    public static LibroDTO toLibroDto(Optional<LibroEntity> entidad) {

        LibroDTO dtoLibro = null;
        if(entidad != null){
            dtoLibro = new LibroDTO();
            dtoLibro.setAutor(entidad.get().getAutor());
            if(entidad.get().getCategoria() != null) {
                dtoLibro.setCategoria(CategoriaMapper.toCategoriaDTO(entidad.get().getCategoria()));
                dtoLibro.setIdCategoria(entidad.get().getCategoria().getIdcategoria());
            }
            dtoLibro.setDescripcion(entidad.get().getDescripcion());
            dtoLibro.setPortada(entidad.get().getPortada());
            dtoLibro.setIdLibro(entidad.get().getIdlibro());
            dtoLibro.setIsbn(entidad.get().getIsbn());
            dtoLibro.setPrecio(entidad.get().getPrecio());
            dtoLibro.setTitulo(entidad.get().getTitulo());
            dtoLibro.setNumeroCopias(entidad.get().getStock());
            dtoLibro.setEditorial(entidad.get().getEditorial());
        }
        return dtoLibro;
    }

}
