package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MenuController {

    @FXML private VBox vboxCerrarSesion;
    @FXML private VBox vboxRetirar;

    @FXML
    public void initialize() {

        if (vboxCerrarSesion != null) {
            vboxCerrarSesion.setOnMouseClicked(event -> cerrarSesion());
        }

        if (vboxRetirar != null) {
            vboxRetirar.setOnMouseClicked(event -> abrirRetiro());
        }
    }

    private void cerrarSesion() {
        cambiarPantalla("/Login.fxml");
    }

    private void abrirRetiro() {
        cambiarPantalla("/RetirarDinero.fxml");
    }

    private void cambiarPantalla(String rutaFxml) {

        try {

            URL url = getClass().getResource(rutaFxml);

            Parent root = FXMLLoader.load(url);

            Stage stage = (Stage) vboxCerrarSesion.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}