package es.adm.webshop.backend.modelo;

import es.adm.webshop.backend.common.EstadoPedido;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name= "PedidoEntity")
@Table( name= "pedido", schema = "webshopdb")
@Data
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idpedido;

    private String localizador;
    private Date fechaPedido;

    @OneToOne(targetEntity = ClienteEntity.class)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

    @Column(name="estado")
    private String estadoBD;

    private String comentario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @OrderBy(value = "id_libro")
    private List<ItemPedidoEntity> itemsPedido;

    public EstadoPedido getEstado(){
        EstadoPedido estadoN = null;
        for (EstadoPedido estadoA : EstadoPedido.values()){
            if(estadoA.equalsName(this.estadoBD)){
                estadoN = estadoA;
                break;
            }
        }
        return estadoN;
    }

    public void setEstado(EstadoPedido estado){
        this.estadoBD = estado.getCodigo();
    }

}
