package app;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Celda;
import model.Tipo;

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

        var bloque = celda.getBloque(); // obtenemos el bloque de la celda
        rect.setStroke(Color.BLACK);

        // Para las celdas sin bloque: si tiene piso se pinta de gris claro, caso contrario se deja en blanco

        if (bloque == null) {
            definirColorCelda(rect, celda.esPiso());
            return rect;
        }

        // Para las celdas con bloque: se elige el color la celda según el tipo de bloque que contiene

        definirRellenoBloque(rect, bloque.getTipo());

        return rect;
    }

    private void definirColorCelda(Rectangle rect, boolean esPiso) {
        if (esPiso) {
            rect.setFill(Color.WHITE);
        } else {
            rect.setStrokeWidth(0);
            rect.setFill(Color.TRANSPARENT);
        }
    }

    private void definirRellenoBloque(Rectangle rect, Tipo tipo) {

        switch (tipo) {
            case OPACOFIJO:
                rellenarConImagen("/closed.jpg", rect);
                break;
            case OPACOMOVIL:
                rellenarConImagen("/fijo.jpg", rect);
                break;
            case ESPEJO:
                rellenarConImagen("/mirror.png", rect);
                break;
            case CRISTAL:
                rellenarConImagen("/crustal.jpg", rect);
                break;
            case VIDRIO:
                rellenarConImagen("/glass.jpg", rect);
                break;
        }

    }

    private void rellenarConImagen(String ruta, Rectangle rect) {
        Image imagen = new Image(getClass().getResourceAsStream(ruta));
        ImagePattern patron = new ImagePattern(imagen);
        rect.setFill(patron);

    }

}
