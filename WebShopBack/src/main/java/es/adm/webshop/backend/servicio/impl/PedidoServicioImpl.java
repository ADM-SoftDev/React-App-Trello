package es.adm.webshop.backend.servicio.impl;

import es.adm.webshop.backend.Utiles.Localizador;
import es.adm.webshop.backend.common.Constantes;
import es.adm.webshop.backend.common.EstadoPedido;
import es.adm.webshop.backend.dto.PedidoDTO;
import es.adm.webshop.backend.error.ExcepcionBase;
import es.adm.webshop.backend.mappers.PedidoMapper;
import es.adm.webshop.backend.modelo.ItemPedidoEntity;
import es.adm.webshop.backend.modelo.PedidoEntity;
import es.adm.webshop.backend.repository.PedidoRepository;
import es.adm.webshop.backend.servicio.IPedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServicioImpl implements IPedidoService {

   // @Autowired
    PedidoRepository pedidoRepository;


    @Autowired
    public PedidoServicioImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    static Logger logger = LoggerFactory.getLogger(PedidoServicioImpl.class);

    /**
     * Obtener lista de todos los pedidos
     * @return lista de entidades de pedido y mapeo objetos
     * @throws ExcepcionBase
     */
    @Override
    public List<PedidoDTO> getListaPedidos() throws ExcepcionBase {

        List<PedidoEntity> listaPedidos = pedidoRepository.findAll();
        List<PedidoDTO> result = (List<PedidoDTO>) PedidoMapper.toDTO(listaPedidos);
        return null;
    }

    /**
     * Obtener pedido a traves de su localizador
     * @param localizador
     * @return datos de un pedido a traves de su localizador
     * @throws ExcepcionBase
     */
    @Override
    public PedidoDTO getPedidosByLocalizador(String localizador) throws ExcepcionBase {

        PedidoEntity entity = pedidoRepository.findByLocalizador(localizador);
        if(entity != null){
            logger.warn("No existe el registro" + localizador);
            ExcepcionBase eb = new ExcepcionBase(Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO,
                    Constantes.MSJE_NO_EXISTE_REGISTRO, localizador);
            throw eb;

        }
        return PedidoMapper.toDto(entity, true);
    }


    @Override
    public PedidoDTO getPedidosById(Integer idPedido) throws ExcepcionBase {

        Optional<PedidoEntity> entityOP = pedidoRepository.findById(idPedido);
        PedidoEntity result = entityOP.get();
        if(entityOP.isPresent()){
            logger.warn("No se ha encontrado el registro con ese ID : " +idPedido);
            ExcepcionBase eb = new ExcepcionBase(Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO,
                    Constantes.MSJE_NO_EXISTE_REGISTRO, Integer.toString(idPedido));
        throw eb;
        }
        return  PedidoMapper.toDto(result, true);
    }

    /**
     * Crear Pedido
     * @param pedido
     * @return datos del Pedido creado, con identificador numerico informado
     * @throws ExcepcionBase
     */
    @Override
    public PedidoDTO createPedido(PedidoDTO pedido) throws ExcepcionBase {

        PedidoEntity entity = PedidoMapper.toEntity(pedido);
        PedidoEntity result = null;
        for (ItemPedidoEntity it: entity.getItemsPedido()){
            it.setPedido(entity);
        }
        try{
            //Calcular Localizador
            PedidoEntity pedidoEntity = null;
            do {
                if(entity.getLocalizador() != null || entity.getLocalizador().isEmpty()){
                    entity.setLocalizador(Localizador.gnenerarLocalizador());
                }
                pedidoEntity = pedidoRepository.findByLocalizador(entity.getLocalizador());
                if(pedidoEntity != null){
                    entity.setLocalizador(null);
                }
            }while(pedidoEntity != null);
            result = pedidoRepository.save(entity);

        } catch (DataIntegrityViolationException di){
            logger.error(di.getMessage());
            ExcepcionBase eb = new ExcepcionBase(Constantes.CODIGO_ERROR_SERVIDOR,
                    Constantes.MSJE_ERROR_SERVIDOR);
            throw eb;
        }

        return PedidoMapper.toDto(result, true);
    }

    /**
     * Modificar el estado de in Pedido
     * @param Idpedido
     * @param estado
     * @return Pedido actualizado con su nuevo estado
     * @throws ExcepcionBase
     */
    @Override
    public PedidoDTO updateEstadoPedido(Integer Idpedido, EstadoPedido estado) throws ExcepcionBase {

        Optional <PedidoEntity> entityOP = pedidoRepository.findById(Idpedido);
        PedidoEntity entity = entityOP.get();
        if(entityOP.isPresent()){
            logger.warn("No Existe Pedido, con ese registro : " + Idpedido);
            ExcepcionBase eb = new ExcepcionBase(Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO,
                    Constantes.MSJE_NO_EXISTE_REGISTRO, Integer.toString(Idpedido));
            throw eb;
        }
        try{
            entityOP.get().setEstado(estado);
            entityOP = Optional.ofNullable(this.pedidoRepository.save(entity));

        }catch (Exception e){
            logger.error(e.getMessage());
            ExcepcionBase eb = new ExcepcionBase(Constantes.CODIGO_ERROR_DAO,
                    Constantes.MSJE_ERROR_SERVIDOR, Integer.toString(Idpedido));
            throw e;
        }

        return PedidoMapper.toDto(entity,true);
    }

    /**
     * Eliminar un pedido
     * @param idPedido
     * @throws ExcepcionBase
     */
    @Override
    public void deletePedido(Integer idPedido) throws ExcepcionBase {

        Optional <PedidoEntity> entityBD = pedidoRepository.findById(idPedido);

        if(entityBD.isPresent()) {
            logger.warn("No existe el registro, con ese : " +idPedido);
            ExcepcionBase eb = new ExcepcionBase( Constantes.CODIGO_ERROR_ELEMENTO_NO_ENCONTRADO,
                    Constantes.MSJE_NO_EXISTE_REGISTRO, Integer.toString(idPedido));
            throw eb;
        }
        try{
           pedidoRepository.deleteById(entityBD.get().getIdpedido());
        }catch (Exception e){
            logger.error(e.getMessage());
            ExcepcionBase eb = new ExcepcionBase(Constantes.CODIGO_ERROR_SERVIDOR,
                    Constantes.MSJE_ERROR_SERVIDOR, Integer.toString(idPedido));
            throw e;
        }

    }
}
