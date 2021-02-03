package es.adm.webshop.backend.servicio;

import es.adm.webshop.backend.dto.CategoriaDTO;
import es.adm.webshop.backend.error.ExcepcionBase;
import java.util.List;

public interface ICategoriaService {

    public List<CategoriaDTO> getListaCetegorias() throws ExcepcionBase;
}
