package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProcesadorArchivo {
    private static final String EM = "E";
    private static final String OBJ = "G";

    private List<char[]> tablero = new ArrayList<>();
    private List<char[]> emisores = new ArrayList<>();
    private List<char[]> objetivos = new ArrayList<>();

    public ProcesadorArchivo(List<char[]> lineasArchivo) {
        desglosarInfo(lineasArchivo);
    }

    private void desglosarInfo(List<char[]> lineasArchivo) {
        /*
           Esta función se encarga de recorrer el ArrayList "lineasArchivo", y setear los atributos
           correspondientes de cada sección del archivo.
       */

       // Sección 1 (tablero)
       int i;
       for (i = 0; i < lineasArchivo.size(); i++) {
           char[] linea = lineasArchivo.get(i);

           if (linea.length == 0) {
               break;
           } else {
               tablero.add(linea);
           }
       }

       //Sección 2
       int j;
       for (j = i; j < lineasArchivo.size(); j++) {
           char[] linea = lineasArchivo.get(j);

           if (linea.length == 0) {
           } else if (String.valueOf(linea[0]).equals(EM)) {
               emisores.add(linea);
           } else if (String.valueOf(linea[0]).equals(OBJ)) {
               objetivos.add(linea);
           }
       }
    }

    public List<char[]> getInfoTablero() {
        //Retorna una lista de arreglos con la información del tablero
        return tablero;
    }

    public List<List<String>> getInfoEmisores() {
        /*
            Se recorren los emisores (lista de char[], donde cada char[] es un emisor)
            y se va guardando la info de cada uno en una lista de Strings (cada string
            es un dato del emisor)

            ------ EJEMPLO ------

            lasers = [
                [columna, fila, direccion],
                [columna, fila, direccion],
            ]

        */

        List<List<String>> lasers = new ArrayList<>();

        // Recorremos la lista de emisores
        for (char[] linea: emisores) {
            List<String> nuevoLaser = new ArrayList<>();

            // Se eliminan los espacios en blanco guardados en el char[]
            String sinEspacios = new String(linea).replace(" ", "");
            char[] res = sinEspacios.toCharArray();

            // Se guarda la información sobre el láser
            nuevoLaser.add(Character.toString(res[1])); // columna
            nuevoLaser.add(Character.toString(res[2])); // fila
            nuevoLaser.add("" + res[3] + res[4]);       // dirección (concatenamos los últimos 2 caracteres)

            lasers.add(nuevoLaser);
        }

        return lasers;
    }

    public List<List<Integer>> getInfoObjetivos() {
        /*
            Se recorren los objetivos (lista de char[], donde cada char[] es un emisor)
            y se va guardando la info de cada uno en una lista de Integer (cada int
            es un dato del objetivo)

            ------ EJEMPLO ------

            lasers = [
                [columna, fila],
                [columna, fila]
            ]
        */

        List<List<Integer>> objs = new ArrayList<>();

        // Recorremos la lista de objetivos
        for (char[] linea: objetivos) {
            List<Integer> nuevoObj = new ArrayList<>();

            // Se eliminan los espacios en blanco guardados en el char[]
            String sinEspacios = new String(linea).replace(" ", "");
            char[] res = sinEspacios.toCharArray();

            // Se guarda la información sobre el objetivo
            nuevoObj.add(Character.getNumericValue(res[1])); // columna
            nuevoObj.add(Character.getNumericValue(res[2])); // fila

            objs.add(nuevoObj);
        }

        return objs;
    }

}
