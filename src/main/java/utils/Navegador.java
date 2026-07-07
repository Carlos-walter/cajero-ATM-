package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase utilitaria para cambiar entre pantallas.
 *
 * Evita repetir el mismo código en todos
 * los controladores del proyecto.
 */
public class Navegador {

    /**
     * Cambia la pantalla actual.
     *
     * @param nodo Nodo de la pantalla actual
     *             (Button, TextField, VBox, etc.).
     * @param rutaFxml Ruta del archivo FXML.
     */
    public static void cambiarPantalla(Node nodo, String rutaFxml) {

        try {

            Parent root = FXMLLoader.load(
                    Navegador.class.getResource(rutaFxml)
            );

            Stage stage = (Stage)
                    nodo.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {

            System.out.println(
                    "Error al abrir: " + rutaFxml
            );

            e.printStackTrace();
        }
    }
}