package es.adm.webshop.backend.modelo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity (name= "libro")
@Table( name= "libro", schema = "webshopdb")
@Data
public class LibroEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idlibro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", referencedColumnName = "idcategoria")
    private CategoriaEntity categoria;

    private String titulo;

    @Column(unique = true)
    private String isbn;

    private String descripcion;
    private String autor;
    private BigDecimal precio;
    private String portada;
    private Integer stock;
    private String editorial;

}
