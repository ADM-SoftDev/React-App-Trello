package es.adm.webshop.backend.modelo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity(name="CategoriaEntity")
@Table( name= "categoria", schema = "webshopdb", indexes={ @Index(name = "name_index", columnList =  "nombre", unique = true)})
@Data
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Integer idcategoria;

    private String nombre;
}
