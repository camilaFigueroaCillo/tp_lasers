package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tablero {
    private static final int DIM = 2;
    private int cols;
    private int filas;
    private List<Celda> celdas;
    private HashMap<Coordenada, Celda> centros;

    public Tablero(List<char[]> tablero) {
        /* crea el tablero, representado como una lista de celdas. */
        this.filas = tablero.size();
        this.cols = tablero.get(0).length;
        this.celdas = llenarTablero(tablero, armarTablero());
        this.centros = calcularCentros();
    }

    private List<Celda> armarTablero() {
        var table = new ArrayList<Celda>();
        for (int i = 0; i < filas * DIM; i += DIM) {
            for (int j = 0; j < cols * DIM; j += DIM) {
                Celda celda = new Celda(i, j, DIM);
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
                Celda celdaActual = celdas.get(i); //accedo a la celda

                if (tipo == Tipo.VACIO) {
                    //Si es una celda sin piso, la inhabilito
                    celdaActual.inhabilitar();
                }
                if (tipo != Tipo.PISO && tipo != Tipo.VACIO) {
                    Bloque b = tipo.crearBloque(celdaActual);
                    celdaActual.agregarBloque(b);
                }
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
