package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Objetivo;

public class ObjetivoView {
    // Atributos
    private final int DIM = 2;
    private static final int SIZE_CELDA = 80;
    private final int fila;
    private final int col;
    private Color color;

    public ObjetivoView(Objetivo obj, Color color) {
        /* Constructor */
        var pos = obj.getCoordenada();
        this.fila = pos.getY();
        this.col = pos.getX();
        this.color = color;
    }

    public Circle crearObjetivo() {
        /*Dibuja y devuelve el objetivo*/

        // Marca el color y el borde
        Circle objGrafico = new Circle(10, color);
        objGrafico.setStroke(Color.FIREBRICK);

        // Marca la ubicación
        objGrafico.setCenterX(SIZE_CELDA * col/DIM);
        objGrafico.setCenterY(SIZE_CELDA * fila/DIM);

       return objGrafico;
    }
}
