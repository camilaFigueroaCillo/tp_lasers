package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LectorArchivo {
    private List<String> lineasArchivo;

    private void leerArchivo(URL ruta) throws IOException {

        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(ruta.toURI())))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        lineasArchivo = lineas;
    }

    public List<String> getLineasArchivo(URL ruta) throws IOException {
        leerArchivo(ruta);
        return lineasArchivo;
    }

    public List<char[]> getCaracteresArchivo(URL ruta) throws IOException {
         /*
           Cada línea del archivo (string) se convierte a un char[], y ese
           arreglo se guarda en un ArrayList llamado "caracteres". Luego, se
           devuelve ese arreglo.
       */
        leerArchivo(ruta);
        List<char[]> caracteres = new ArrayList<>();
        for (String linea: lineasArchivo) {
            caracteres.add(linea.toCharArray());
        }
        return caracteres;
    }
}
