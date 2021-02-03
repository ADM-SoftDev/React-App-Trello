package es.adm.webshop.backend.common;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateSerializer extends JsonSerializer<Date> {


    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,JsonProcessingException  {

        DateFormat formateo = new SimpleDateFormat("dd-MM-yyyy");
        jsonGenerator.writeString(formateo.format(date));
    }
}
