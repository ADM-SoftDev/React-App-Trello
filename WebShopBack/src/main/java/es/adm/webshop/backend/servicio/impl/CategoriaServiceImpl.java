package es.adm.webshop.backend.servicio.impl;

import es.adm.webshop.backend.mappers.CategoriaMapper;
import es.adm.webshop.backend.dto.CategoriaDTO;
import es.adm.webshop.backend.error.ExcepcionBase;
import es.adm.webshop.backend.modelo.CategoriaEntity;
import es.adm.webshop.backend.repository.CategoriaRepository;
import es.adm.webshop.backend.servicio.ICategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private CategoriaRepository repository;

    Logger logger = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    /**
     * Obtener una Lista de Categorias
     * @return lista de categorias existente en la BBDD
     * @throws ExcepcionBase
     */
    @Override
    public List<CategoriaDTO> getListaCetegorias() throws ExcepcionBase {

        List<CategoriaEntity> lista = repository.findAll();
        List<CategoriaDTO> resul = CategoriaMapper.toCategoriaDTO(lista);
        return resul;
    }
}
