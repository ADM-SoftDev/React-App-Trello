package es.adm.webshop.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LibroDTO {

    private Integer idLibro;
    private Integer idCategoria;
    private String titulo;
    private String isbn;
    private String descripcion;
    private String autor;
    private CategoriaDTO categoria;
    private BigDecimal precio;
    private String portada;
    private Integer numeroCopias;
    private String editorial;

}
