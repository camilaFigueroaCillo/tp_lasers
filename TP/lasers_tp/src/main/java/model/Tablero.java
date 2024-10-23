package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tablero {
    private int dimension;
    private int cols;
    private int filas;
    private List<Celda> celdas;
    private HashMap<Coordenada, Celda> centros;

    public Tablero(List<char[]> tablero, int dimension) {
        /* crea el tablero, representado como una lista de celdas. */
        this.dimension = dimension;
        this.filas = tablero.size();
        this.cols = tablero.get(0).length;
        this.celdas = llenarTablero(tablero, armarTablero());
        this.centros = calcularCentros();
    }

    private List<Celda> armarTablero() {
        var table = new ArrayList<Celda>();
        for (int i = 0; i < filas * dimension; i += dimension) {
            for (int j = 0; j < cols * dimension; j += dimension) {
                Celda celda = new Celda(i, j);
                table.add(celda);
            }
        }
        return table;
    }

    private List<Celda> llenarTablero(List<char[]> tablero, List<Celda> celdas) {
        int i = 0;
        for (char[] arr: tablero) {
            //Por cada arreglo en tablero
            for (char c: arr) {
                //Por cada caracter en el arreglo
                Tipo tipo = Tipo.convertirATipo(c); //convierto el string a un tipo
                Celda celdaActual = celdas.get(i);
                celdaActual.setTipo(tipo);
                i++;

            }
        }
        return celdas;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return cols;
    }

    public List<Celda> getCeldas() {
        return celdas;
    }

    private HashMap<Coordenada, Celda> calcularCentros() {
        var centros = new HashMap<Coordenada, Celda>();

        for (int i = 0; i < celdas.size(); i++) {
            Celda actual = celdas.get(i);
            if (actual.getBloque() != null ) {
                var centroCelda = actual.getCentro();
                centros.put(centroCelda, actual);
            }
        }
        return centros;
    }

    public HashMap<Coordenada, Celda> getCentros() {
        return centros;
    }






}
