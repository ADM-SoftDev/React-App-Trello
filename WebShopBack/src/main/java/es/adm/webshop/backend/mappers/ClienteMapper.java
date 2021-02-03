package es.adm.webshop.backend.mappers;

import es.adm.webshop.backend.dto.ClienteDTO;
import es.adm.webshop.backend.dto.ItemPedidoDTO;
import es.adm.webshop.backend.modelo.ClienteEntity;
import es.adm.webshop.backend.modelo.ItemPedidoEntity;

import java.util.ArrayList;
import java.util.List;

public class ClienteMapper {

    public static ClienteEntity toENTITY(ClienteDTO source){

        ClienteEntity result = null;
        if (source != null) {
            result = new ClienteEntity();
            result.setIdcliente(source.getIdCliente());
            result.setNombre(source.getNombre());
            result.setApellido(source.getApellido());
            result.setDni(source.getDni());
            result.setTelefono(source.getTelefono());
            result.setEmail(source.getEmail());
            result.setDireccion(source.getDireccion());
            result.setCp(source.getCp());

        }
        return result;
    }

    public static ClienteDTO toDTO(ClienteEntity source){
        ClienteDTO result = null;
        if(source != null){
            result = new ClienteDTO();
            result.setIdCliente(source.getIdcliente());
            result.setNombre(source.getNombre());
            result.setApellido(source.getApellido());
            result.setDni(source.getDni());
            result.setTelefono(source.getTelefono());
            result.setEmail(source.getEmail());
            result.setDireccion(source.getDireccion());
            result.setCp(source.getCp());

        }
        return result;
    }


    public static List<ClienteDTO> toDto(List<ClienteEntity> source){
        List<ClienteDTO>  result =new ArrayList<>();
        if(source != null){
            for (ClienteEntity item: source) {
                result.add(toDTO(item));
            }
        }
        return result;
    }

    public static List<ClienteEntity> toEntity(List<ClienteDTO> source){
        List<ClienteEntity> result = new ArrayList<>();
        if(source != null){
            for (ClienteDTO item: source) {
                result.add(toENTITY(item));
            }
        }
        return result;
    }
}
