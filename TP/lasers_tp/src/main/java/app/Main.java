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
import model.LectorArchivo;
import model.Nivel;
import model.ProcesadorArchivo;

import java.io.*;
import java.net.URL;
import java.util.Map;

public class Main extends Application {
    private Nivel nivel;
    private LectorArchivo lectorArchivo = new LectorArchivo();
    private LectorArchivo lectorReglas = new LectorArchivo();

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

        URL ruta = getClass().getResource(rutaNivel);
        var infoNivel = lectorArchivo.getCaracteresArchivo(ruta);
        this.nivel = new Nivel(new ProcesadorArchivo(infoNivel));

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

    private void comenzarJuego(Stage primaryStage) throws IOException {
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
        reglas.setOnAction(e -> {
            try {
                mostrarReglas();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void mostrarReglas() throws IOException {

        /* Muestra una ventana que contiene las reglas del juego */

        Stage reglas = new Stage();
        reglas.setTitle("Reglas del juego");

        VBox texto = new VBox(10);
        URL ruta = getClass().getResource("/reglas.txt");
        var reglasJuego = lectorReglas.getLineasArchivo(ruta);

        for (String regla: reglasJuego) {
            Label label = new Label(regla);
            texto.getChildren().add(label);
        }

        Scene scene = new Scene(texto, 600, 500);
        reglas.setScene(scene);
        reglas.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
