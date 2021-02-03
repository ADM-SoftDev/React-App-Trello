package es.adm.webshop.backend.servicio.impl;

import es.adm.webshop.backend.dto.CategoriaDTO;
import es.adm.webshop.backend.error.ExcepcionBase;
import es.adm.webshop.backend.servicio.ICategoriaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriServicioImpl implements ICategoriaService {

    private List<CategoriaDTO> listaCategoria = null;



    private CategoriServicioImpl(){
        this.listaCategoria = new ArrayList<>();
        this.listaCategoria.add(new CategoriaDTO(9,"Programacion"));
        this.listaCategoria.add(new CategoriaDTO(10,"Redes"));
    }

    @Override
    public List<CategoriaDTO> getListaCetegorias() throws ExcepcionBase {
        List<CategoriaDTO> lista = new ArrayList<>();
        lista.addAll(listaCategoria);
        return lista;
    }

}
