package es.adm.webshop.backend.common;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import es.adm.webshop.backend.error.ExcepcionBase;

public class MyDateDeserializer extends JsonDeserializer<Date> {


    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        SimpleDateFormat formateo = new SimpleDateFormat("dd-mm-yyyy");
        String date = jsonParser.getText();
        try{
            return formateo.parse(date);
        }catch (ParseException pe){
            pe.getMessage();
            pe.printStackTrace();
            return null;
        }
    }
}
