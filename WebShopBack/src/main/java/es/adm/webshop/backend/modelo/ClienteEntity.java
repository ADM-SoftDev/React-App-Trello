package es.adm.webshop.backend.modelo;

import lombok.Data;

import javax.persistence.*;

@Entity(name="ClienteEntity")
@Table( name= "cliente", schema = "webshopdb")
@Data
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idcliente;

    private String nombre;
    private String apellido;
    private String dni;
    private Integer telefono;
    private String email;
    private String direccion;
    private Integer cp;

}
