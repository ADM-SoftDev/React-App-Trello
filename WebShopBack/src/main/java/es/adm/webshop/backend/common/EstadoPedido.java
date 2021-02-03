package es.adm.webshop.backend.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum EstadoPedido {
    CREADO("C"),
    ENTRAMITE("T"),
    PAGADO("P"),
    ENVIADO("E"),
    DEVUELTO("D"),
    FINALIZADO("F"),
    CANCELADO("CA");

    private final String codigo;

    EstadoPedido(String codigo) {
        this.codigo = codigo;
    }

    public boolean equalsName(String otherName){
        return codigo.equals(otherName);
    }

    @Override
    public String toString() {
        return "EstadoPedido{" +
                "codigo='" + codigo + '\'' +
                '}';
    }
}
