package es.adm.webshop.backend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mensaje {

    private int codigo;
    private String mensaje;
    private String tipo;
    private String origen;

    /**
     * Notificacion de Mensajes para el usuario
     * @param codigo
     * @param mensaje
     * @param origen
     */
    public Mensaje(int codigo, String mensaje, String origen) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.origen = origen;
    }

}
