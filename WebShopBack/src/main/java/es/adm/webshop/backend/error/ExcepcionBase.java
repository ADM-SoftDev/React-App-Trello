package es.adm.webshop.backend.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExcepcionBase extends Exception{

    private int codigo;
    private String mensaje;
    private String origen;

    public ExcepcionBase(int codigo,String mensaje ){
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

}
