package es.adm.webshop.backend.modelo;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name="ItemPedidoEntity")
@Table( name= "compra", schema = "webshopdb")
@Data
public class ItemPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idcompra;

    private Integer id_libro;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoEntity pedido;

    private String titulo;
    private BigDecimal precio;
    private Integer unidades;
}
