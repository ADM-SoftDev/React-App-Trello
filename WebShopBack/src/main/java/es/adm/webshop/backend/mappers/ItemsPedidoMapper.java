package es.adm.webshop.backend.mappers;

import es.adm.webshop.backend.dto.ItemPedidoDTO;
import es.adm.webshop.backend.dto.PedidoDTO;
import es.adm.webshop.backend.modelo.ItemPedidoEntity;
import es.adm.webshop.backend.modelo.PedidoEntity;

import java.util.ArrayList;
import java.util.List;

public class ItemsPedidoMapper {

    public static ItemPedidoEntity toEntity(ItemPedidoDTO source) {
        ItemPedidoEntity result = null;
        if(source != null){
            result = new ItemPedidoEntity();
            result.setIdcompra(source.getIdPedido());
            result.setId_libro(source.getIdLibro());
            result.setPedido(new PedidoEntity());
            result.getPedido();
            result.setTitulo(source.getTitulo());
            result.setPrecio(source.getPrecio());
            result.setUnidades(source.getUnidades());
        }
        return result;
    }

    public static ItemPedidoDTO toDto(ItemPedidoEntity source){
        ItemPedidoDTO result = null;
        if(result != null){
            result = new ItemPedidoDTO();
            result.setIdItem(source.getIdcompra());
            result.setIdLibro(source.getId_libro());
            result.setIdPedido(source.getPedido().getIdpedido());
            result.setTitulo(source.getTitulo());
            result.setPrecio(source.getPrecio());
            result.setUnidades(source.getUnidades());
        }
        return result;
    }

    public static List<ItemPedidoDTO> toDTO(List<ItemPedidoEntity> source){
        List<ItemPedidoDTO>  result =new ArrayList<>();
        if(source != null){
            for (ItemPedidoEntity item: source) {
                result.add(toDto(item));
            }
        }
        return result;
    }

    public static List<ItemPedidoEntity> toENTITY(List<ItemPedidoDTO> source){
        List<ItemPedidoEntity> result = new ArrayList<>();
        if(source != null){
            for (ItemPedidoDTO item: source) {
                result.add(toEntity(item));
            }
        }
        return result;
    }
}
