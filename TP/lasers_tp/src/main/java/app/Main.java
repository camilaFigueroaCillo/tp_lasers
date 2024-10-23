package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Nivel;
import model.ProcesadorArchivo;

import java.io.IOException;
import java.util.Map;

public class Main extends Application {
    private Nivel nivel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Juego de Lasers");
        mostrarMenu(primaryStage);
    }

    public void mostrarMenu(Stage stage) throws IOException {
        /*
            Carga en la interfaz el archivo FXML que contiene la organización del menú,
            y le asigna los listeners a cada elemento del menú. Luego, muestra la ventana
        */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menuGraphic.fxml"));
        Parent root = loader.load();

        StackPane panelCentro = new StackPane(root);
        panelCentro.setAlignment(Pos.CENTER);

        Map<String, String> mapaNiveles = armarNiveles();
        asignarListeners(mapaNiveles, root, stage);

        Scene scene = new Scene(panelCentro, 640, 550);
        stage.setScene(scene);
        stage.show();
    }

    private void cargarNivel(String rutaNivel, Stage stage) throws IOException {
        // Se crea el nivel (procesando el archivo .dat) y la vista (a partir de ese nivel)
        this.nivel = new Nivel(new ProcesadorArchivo(getClass().getResource(rutaNivel)));

        // Se crea e inicializa el controlador que se comunicará con la información procesada y con la parte gráfica
        comenzarJuego(stage);
    }

    private Map<String, String> armarNiveles() {
        /* Arma un diccionario con clave=botonNivel y valor=rutaNivel */

        return Map.of("#nivel1", "/level1.dat", "#nivel2", "/level2.dat", "#nivel3", "/level3.dat",
                "#nivel4", "/level4.dat", "#nivel5", "/level5.dat", "#nivel6", "/level6.dat"
        );
    }

    private void asignarListeners(Map<String, String> mapaNiveles, Parent root, Stage stage) {
        for (Map.Entry<String, String> tupla : mapaNiveles.entrySet()) {
            /* --- EJEMPLO -> (botonNivel1, rutaNivel1) --- */

            // Agregamos el listener a botonNivel1
            Button button = (Button) root.lookup(tupla.getKey());

            // Cuando se presione botonNivel, se ejecuta la función cargarNivel, que recibe por parámetro rutaNivel
            button.setOnAction(e -> {
                try {
                    cargarNivel(tupla.getValue(), stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    public void comenzarJuego(Stage primaryStage) throws IOException {
        // Cargo el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gamePane2.fxml"));
        Pane root = loader.load();
        Pane panel = (Pane) root.lookup("#tableGame");

        nivel.moverLasers();

        // Dibujo tablero
        var tablero = new TableroView(nivel.getTablero(), nivel);
        tablero.render(panel, root);

        // Seteo handlers de botones
        setButtons(root, primaryStage);

        // Mostrar escena
        Scene scene = new Scene(root, 640, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setButtons(Parent root, Stage stage) {
        /* Establece el comportamiento de los botones que figuran en el menú inicial */

        Button menu = (Button) root.lookup("#back_menu_button");
        Button reglas = (Button) root.lookup("#reglas_button");

        // agrega el listener para el boton 'menu'
        menu.setOnAction(e -> {
            try {
                mostrarMenu(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // agrega el listener para el boton 'reglas'
        reglas.setOnAction(e -> mostrarReglas());
    }

    private void mostrarReglas() {
        /* Muestra una ventana que contiene las reglas del juego */
        Stage reglas = new Stage();
        reglas.setTitle("Reglas del juego");

        VBox texto = new VBox(10);
        Label renglon_1 = new Label("Reglas: ");
        Label renglon_2 = new Label("1. Elegir un bloque a mover y clickearlo");
        Label renglon_3 = new Label("1. a. El único bloque inamovible es el de color gris oscuro, llamado OPACO FIJO. En caso de seleccionarlo, saldrá un error.");

        Label renglon_4 = new Label("2. Elegir una celda vacia para mover el bloque, y clickearla");
        Label renglon_5 = new Label("2. a. Las celdas vacías (es decir, aquellas donde se puede colocar un bloque) se reconocen por ser de color GRIS CLARO");

        Label renglon_6 = new Label("3. El juego se gana cuando todos los objs fueron apuntados por el laser");

        texto.getChildren().addAll(renglon_1, renglon_2, renglon_3, renglon_4, renglon_5, renglon_6);

        Scene scene = new Scene(texto, 400, 400);
        reglas.setScene(scene);
        reglas.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
