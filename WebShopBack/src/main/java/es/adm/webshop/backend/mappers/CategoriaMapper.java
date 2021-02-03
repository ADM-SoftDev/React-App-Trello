package es.adm.webshop.backend.mappers;

import es.adm.webshop.backend.dto.CategoriaDTO;
import es.adm.webshop.backend.modelo.CategoriaEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoriaMapper {

    public static List<CategoriaDTO> toCategoriaDTO (List<CategoriaEntity> entidades)  {

        List<CategoriaDTO> dto = new ArrayList<>();
        if(entidades != null){
            for (CategoriaEntity entidad: entidades) {
                dto.add(toCategoriaDTO(entidad));
            }
        }
        return dto;
    }

    public static CategoriaDTO toCategoriaDTO(CategoriaEntity categoria) {
        CategoriaDTO  dto = null;
        if(categoria != null){
            dto = new CategoriaDTO((categoria.getIdcategoria()),categoria.getNombre());
        }
        return dto;
    }
}
