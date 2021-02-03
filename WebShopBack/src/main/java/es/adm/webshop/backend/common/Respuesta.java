package es.adm.webshop.backend.common;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Respuesta<T> {

    private T datos;
    private List<Mensaje> mensajes;

    /**
     * Servicio Mensajeria para Rest
     * @param mensaje
     * Retorna la informacion a devolver al servicio Rest
     */
    public void addMensaje(Mensaje mensaje){
        if(mensaje == null){
            this.mensajes =new ArrayList<>();
        }else {
            this.mensajes.add(mensaje);
        }

    }

}
