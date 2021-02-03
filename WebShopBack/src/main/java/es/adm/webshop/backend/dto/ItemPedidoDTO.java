package es.adm.webshop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {

    private Integer unidades;
    private Integer idPedido;
    private Integer idItem;
    private Integer idLibro;
    private String titulo;
    private BigDecimal precio;

}
