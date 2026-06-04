package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Aquí carga tu archivo .fxml (el diseño que hiciste en Scene Builder)
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));

        primaryStage.setTitle("Cajero Automático - Interfaz");
        primaryStage.setScene(new Scene(root, 450, 350)); // Tamaño de la ventana
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}