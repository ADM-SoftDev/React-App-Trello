package es.adm.webshop.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDTO {

    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String dni;
    private Integer telefono;
    private String email;
    private String direccion;
    private Integer cp;

}
