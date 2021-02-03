package es.adm.webshop.backend.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CestaDTO {

    private String localizador;
    private String estado;
    private Date fecha;
    private List<ItemCestaDTO> items;

}
