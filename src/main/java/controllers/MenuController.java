package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * Controlador para la pantalla del Menú Principal.
 * Gestiona la navegación y las acciones de los botones.
 */
public class MenuController {

    @FXML private VBox vboxCerrarSesion;

    @FXML
    public void initialize() {
        // Vinculamos la acción de clic al VBox de "Cerrar Sesión"
        if (vboxCerrarSesion != null) {
            vboxCerrarSesion.setOnMouseClicked(event -> cerrarSesion());
        }
    }

    /**
     * Carga el archivo Login.fxml para volver a la pantalla de inicio.
     */
    private void cerrarSesion() {
        try {
            // CORRECCIÓN: La ruta es "/Login.fxml" porque el archivo está en la raíz de resources
            URL url = getClass().getResource("/Login.fxml");

            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) vboxCerrarSesion.getScene().getWindow();

            if (stage != null) {
                stage.setScene(new Scene(root));
                stage.show();
            }

        } catch (IOException e) {
            System.err.println("Error al intentar volver al Login:");
            e.printStackTrace();
        }
    }
}