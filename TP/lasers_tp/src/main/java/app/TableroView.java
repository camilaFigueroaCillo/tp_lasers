package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.*;

import java.util.List;

import static model.Tipo.OPACOFIJO;

public class TableroView {
    // Atributos
    private Rectangle bloqueSeleccionado = null;
    private double originalX, originalY;
    private static final int SIZE_CELDA = 80;
    private Nivel nivel;
    private List<Celda> celdas;
    private int filas;
    private int cols;

    public TableroView(List<Celda> celdas, int cols, int filas, Nivel nivel) {
        /* Constructor */
        this.nivel = nivel;
        this.celdas = celdas;
        this.filas = filas;
        this.cols = cols;
    }

    public void render(Pane panel, Pane root) {
        /* Agrega el tablero a la interfaz gráfica */
        Group group = new Group();
        panel.getChildren().add(group);
        dibujarTablero(panel, root, group);
        dibujarObjetivos(panel);
        dibujarLaser(panel);
    }

    private void dibujarTablero(Pane panel, Pane root, Group group) {
        /*
            Se recorre la lista de celdas y se va agregando una por una
            a la interfaz, ordenadas en forma de cuadrícula
        */
        
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < cols; col++) {
                var celda = celdas.get(fila*cols + col);    // obtenemos la celda
                var cv = new CeldaView(celda);              // creamos su instancia de vista

                var bloque = cv.render();
                bloque.setOnMouseClicked(e -> manejarClick(bloque, root, panel));
                group.getChildren().add(bloque);
            }
        }

    }

    private void manejarClick(Rectangle bloque, Pane root, Pane panel) {
        if (bloqueSeleccionado == null) {
            // Si no hay bloque seleccionado, ???
            bloqueSeleccionado = bloque;
            originalX = bloque.getX();
            originalY = bloque.getY();
            bloque.setStroke(Color.BLACK);
            bloque.setStrokeWidth(3);
        } else {
            double tempX = bloque.getX();
            double tempY = bloque.getY();

            // Calculo las nuevas posiciones en filas y columnas
            int nuevaFila = (int) (tempY / SIZE_CELDA);
            int nuevaCol = (int) (tempX / SIZE_CELDA);
            int viejaFila = (int) (originalY / SIZE_CELDA);
            int viejaCol = (int) (originalX / SIZE_CELDA);

            Celda celdaAnterior = celdas.get(viejaFila * cols + viejaCol);
            Celda celdaNueva = celdas.get(nuevaFila * cols + nuevaCol);


            // Se intenta ejecutar la jugada
            if (nivel.moverBloques(celdaAnterior, celdaNueva)) {
                // El modelo se actualizó, con lo cual se cambian de lugar los bloques visuales

                bloque.setX(originalX);
                bloque.setY(originalY);
                bloqueSeleccionado.setX(tempX);
                bloqueSeleccionado.setY(tempY);

                //Actualizo lasers y objetivos
                nivel.moverLasers();
                panel.getChildren().removeIf(node -> node instanceof Line);
                panel.getChildren().removeIf(node -> node instanceof Circle);
                dibujarLaser(panel);
                dibujarObjetivos(panel);

                //Si hay ganador cambio el fondo a verde
                if (nivel.hayGanador()) {
                    root.setStyle("-fx-background-color: #00FF00;");
                }

            } else {
                // Ocurrió un error (se informa al jugador)
                Bloque b = celdaAnterior.getBloque();
                Bloque c = celdaNueva.getBloque();

                if  (b != null && b.getTipo() == OPACOFIJO) {
                    mostrarAlerta(OPACOFIJO);
                } else if (c != null || b == null) {
                    mostrarAlerta(null);
                }
            }

            bloqueSeleccionado.setStrokeWidth(1);
            bloqueSeleccionado = null;
        }
    }

    private void dibujarObjetivos(Pane panel) {
        /*
            Para cada objetivo se crea su instancia de vista
            y se agrega a la interfaz grafica
        */

        for (Objetivo obj: nivel.getObjetivos()) {
            var objetivo = new ObjetivoView(obj);
            var circle = objetivo.agregarObjetivo();
            panel.getChildren().add(circle);
        }
    }

    private void dibujarLaser(Pane panel) {
        /*
            Para cada laser en la lista de lasers,
            se crea su instancia de vista y se agrega
            a la interfaz grafica
        */

        for (Laser l: nivel.getLasers()) {
            var laser = new LaserView(l);
            laser.render(panel);
        }
    }

    private void mostrarAlerta(Tipo tipo) {
        /*
            Muestra una alerta en caso de que el usuario haya cometido un
            error al realizar un movimiento (intentar mover un bloque fijo,
            o elegir como destino a una celda sin piso)
        */

        Stage alerta = new Stage();
        alerta.setTitle("Alerta");
        Label renglon;

        if (tipo == OPACOFIJO) {
            renglon = new Label("Recuerda que los bloques fijos no pueden moverse!");
        } else {
            renglon = new Label("El origen debe ser un bloque, y el destino una celda vacia");
        }

        Scene scene = new Scene(renglon, 400, 400);
        alerta.setScene(scene);
        alerta.show();
    }
}