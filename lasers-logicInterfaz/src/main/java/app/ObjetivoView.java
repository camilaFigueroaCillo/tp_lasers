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
    private Objetivo obj;

    public ObjetivoView(Objetivo obj) {
        /* Constructor */
        var pos = obj.getCoordenada();
        this.fila = pos.getY();
        this.col = pos.getX();
        this.obj = obj;
    }

    public Circle agregarObjetivo() {
        /* Agrega el objetivo a la interfaz gráfica */
        
        Circle objGrafico;

        // Marca el color y el borde según si fue apuntado o no
        if (obj.fueApuntado()) {
            objGrafico = new Circle(10, Color.INDIANRED);
        } else {
            objGrafico = new Circle(10, Color.WHITESMOKE);
            objGrafico.setStroke(Color.RED);
        }

        // Marca la ubicación
        objGrafico.setCenterX(SIZE_CELDA * col/DIM);
        objGrafico.setCenterY(SIZE_CELDA * fila/DIM);

       return objGrafico;
    }
}
