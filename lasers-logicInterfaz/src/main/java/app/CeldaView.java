package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Celda;

public class CeldaView {
    // Dimensiones de la celda
    private static final int SIZE_CELDA = 80;
    private static final int DIM = 2;

    private Celda celda; // Instancia de Celda a la que se le va a aplicar esta vista

    public CeldaView(Celda c) {
        this.celda = c;
    }

    public Rectangle render() {
        // Se crea la celda y se establece su posición en la interfaz gráfica
        var posIni = celda.getPosIni();

        int fila = posIni.getY();
        int col = posIni.getX();

        Rectangle rect = new Rectangle(SIZE_CELDA, SIZE_CELDA);
        rect.setX(SIZE_CELDA * col/DIM);
        rect.setY(SIZE_CELDA * fila/DIM);

        // Estilo visual por defecto (para las celdas vacías)
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);

        var bloque = celda.getBloque(); // obtenemos el bloque de la celda

        // Para las celdas sin bloque: si tiene piso se pinta de gris claro, caso contrario se deja el blanco
        if (bloque == null) {
            if (celda.esPiso()) {
                rect.setFill(Color.LIGHTGRAY);
                return rect;
            } else {
                return rect;
            }
        }

        // Para las celdas con bloque: se elige el color la celda según el tipo de bloque que contiene
        switch (bloque.getTipo()) {
            case OPACOFIJO:
                rect.setFill(Color.DIMGREY);
                break;
            case OPACOMOVIL:
                rect.setFill(Color.GREY);
                break;
            case ESPEJO:
                rect.setFill(Color.DARKCYAN);
                break;
            case CRISTAL:
                rect.setFill(Color.CYAN);
                break;
            case VIDRIO:
                rect.setFill(Color.LIGHTBLUE);
                break;
        }

        return rect;
    }


}
