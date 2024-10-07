package app;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.Coordenada;
import model.Laser;

public class LaserView {
    // Atributos
    private final int SIZE_CELDA = 80;
    private final int DIM = 2;
    private final String X = "X";
    private final String Y = "Y";
    private Laser laser;

    public LaserView(Laser laser) {
        /* Constructor */
        this.laser = laser;
    }

    public void render(Pane panel) {
        /* Características visuales del laser */

        // Datos necesarios
        var sizeRecorrido = laser.getRecorrido().size();
        var posIni = laser.getPrimerPos();

        // Círculo ubicado en el origen del laser
        Circle origen = new Circle(8, Color.RED);
        int origenX = getPosPixeles(posIni,X);
        int origenY = getPosPixeles(posIni, Y);
        origen.setCenterX(origenX);
        origen.setCenterY(origenY);
        panel.getChildren().add(origen);

        // Se dibuja el recorrido del laser
        for (int i = 0; i < sizeRecorrido - 1; i++) {

            Coordenada ini = laser.getRecorrido().get(i);
            Coordenada fin = laser.getRecorrido().get(i + 1);

            int pixelesIniX = getPosPixeles(ini, X);
            int pixelesIniY = getPosPixeles(ini, Y);
            int pixelesFinX = getPosPixeles(fin, X);
            int pixelesFinY = getPosPixeles(fin, Y);

            Line line = new Line(pixelesIniX, pixelesIniY, pixelesFinX, pixelesFinY);
            line.setStroke(Color.RED);
            line.setStrokeWidth(4);
            panel.getChildren().add(line);
        }

    }

    private int getPosPixeles(Coordenada coord, String eje) {
        /*
            Recibe una medida expresada como Coordenada, y
            devuelve su equivalencia en la interfaz gráfica
        */

        if (eje.equals(X)) {
            return SIZE_CELDA * coord.getX()/DIM;
        } else {
            return SIZE_CELDA * coord.getY()/DIM;
        }
    }

}
