package es.adm.webshop.backend.Utiles;

import java.util.Random;

public class Localizador {

    public static  String gnenerarLocalizador(){
        String res = " ";
        Random randon = new Random();
        String[] opciones = {
                "A","B", "C","D","E","F", "G","H","J",
                "K","L","M","N","P","Q","R","S","T",
                "U","V","W","X","Y","Z","0", "1","2",
                "3","4","5","6","7","8","9"
        };
        for (String codigo:opciones) {
            res += opciones[randon.nextInt(opciones.length)];
        }
        return res;

    }
    public static String genenerarLocalizador12(){
        String res = " ";
        Random randon = new Random();
        String[] opciones = {
                "A","B", "C","D","E","F", "G","H","J",
                "K","L","M","N","P","Q","R","S","T",
                "U","V","W","X","Y","Z","0", "1","2",
                "3","4","5","6","7","8","9"
        };

        for(int i=0; i<12; i++){
            res += opciones[randon.nextInt(opciones.length)];
        }

        return res;

    }
}
