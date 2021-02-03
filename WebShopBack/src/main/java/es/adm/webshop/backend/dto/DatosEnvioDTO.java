package es.adm.webshop.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosEnvioDTO {

    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String direccion;
    private int cp;

}
