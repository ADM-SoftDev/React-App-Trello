package es.adm.webshop.backend.mappers;

import es.adm.webshop.backend.common.EstadoPedido;
import es.adm.webshop.backend.dto.ClienteDTO;
import es.adm.webshop.backend.dto.ItemPedidoDTO;
import es.adm.webshop.backend.dto.PedidoDTO;
import es.adm.webshop.backend.modelo.ClienteEntity;
import es.adm.webshop.backend.modelo.ItemPedidoEntity;
import es.adm.webshop.backend.modelo.PedidoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoMapper {

    public static PedidoEntity toEntity(PedidoDTO source){

        PedidoEntity result = null;
        if (source != null) {
            result = new PedidoEntity();
            result.setIdpedido(source.getIdPedido());
            result.setLocalizador(source.getLocalizador());
            result.setFechaPedido(source.getFecha());
            result.setEstado(EstadoPedido.valueOf(source.getEstado()));
            result.setComentario(source.getComentario());
            result.setCliente((ClienteEntity) ClienteMapper.toEntity(source.getCliente()));
            result.setItemsPedido(ItemsPedidoMapper.toENTITY(source.getItems()));

        }
        return result;
    }

    public static PedidoDTO toDto(PedidoEntity source, boolean incluirItems){
        PedidoDTO result = null;
        if(source != null){
            result = new PedidoDTO();
            result.setIdPedido(source.getIdpedido());
            result.setLocalizador(source.getLocalizador());
            result.setFecha(source.getFechaPedido());
            result.setComentario(source.getComentario());
            result.setEstado(source.getEstadoBD());
            result.setCliente((List<ClienteDTO>) ClienteMapper.toDTO(source.getCliente()));
            if(incluirItems){
                result.setItems(ItemsPedidoMapper.toDTO(source.getItemsPedido()));
            }
        }
        return result;
    }
    public static List<PedidoDTO> toDTO(List<PedidoEntity> source,boolean items){
        List<PedidoDTO> result = new ArrayList<>();
        if(source != null){
            for (PedidoEntity item: source) {
                result.add(toDto(item,items));
            }
        }
        return result;
    }

    public static List<PedidoDTO> toDTO(List<PedidoEntity> source) {
        return toDTO(source,false);
    }


    public static List<PedidoEntity> toENTITY(List<PedidoDTO> source){
        List<PedidoEntity> result = new ArrayList<>();
        if(source != null){
            for (PedidoDTO item: source) {
                result.add(toEntity(item));
            }
        }
        return result;
    }
}
