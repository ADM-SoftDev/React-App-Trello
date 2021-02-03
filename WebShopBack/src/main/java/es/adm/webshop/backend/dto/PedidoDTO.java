package es.adm.webshop.backend.dto;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.adm.webshop.backend.common.MyDateDeserializer;
import es.adm.webshop.backend.common.MyDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PedidoDTO {

    private Integer idPedido;
    private String localizador;
    private String estado;
    @JsonSerialize(using = MyDateSerializer.class)
    @JsonDeserialize(using = MyDateDeserializer.class)
    private Date fecha;
    private List<ClienteDTO> cliente;
    private List<ItemPedidoDTO> items;
    private String comentario;

}
