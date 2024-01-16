package com.example.gestiondepedidosfx2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La clase HelloApplication es la clase principal de la aplicación que utiliza JavaFX para gestionar pedidos.
 */
public class HelloApplication extends Application {
    private static Stage myStage;

    /**
     * El método start inicia la aplicación, carga la escena FXML de inicio (login.fxml) y muestra la ventana principal.
     *
     * @param stage El objeto Stage que representa la ventana principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar la escena FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        Image imagen = new Image(HelloApplication.class.getClassLoader().getResource("icon.png").toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        stage.setTitle("Gestión de Pedidos");
        stage.setScene(scene);
        stage.getIcons().add(imagen);
        stage.show();
    }

    /**
     * El método loadFXML carga una escena FXML en la ventana principal.
     *
     * @param ruta La ruta al archivo FXML que se desea cargar.
     * @throws IOException Si ocurre un error al cargar la escena FXML.
     */
    public static void loadFXML(String ruta) {
        try {
            Image imagen = new Image(HelloApplication.class.getClassLoader().getResource("icon.png").toExternalForm());
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
            myStage.getIcons().add(imagen);
            myStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * El método main es el punto de entrada principal de la aplicación.
     *
     * @param args Argumentos de la línea de comandos, si los hubiera.
     */
    public static void main(String[] args) {
        launch();
    }
}