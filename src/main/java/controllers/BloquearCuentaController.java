package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class BloquearCuentaController {

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnBloquearCuenta;


    @FXML
    public void volverMenu() {

        cambiarPantalla("/Menu.fxml");
    }


    @FXML
    public void bloquearCuenta() {

        System.out.println(
                "Cuenta bloqueada correctamente."
        );

        cambiarPantalla(
                "/CuentaBloqueada.fxml"
        );
    }


    private void cambiarPantalla(String rutaFxml) {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(rutaFxml)
            );

            Stage stage = (Stage)
                    btnVolver
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        } catch (Exception e) {

            System.out.println(
                    "Error al abrir: " + rutaFxml
            );

            e.printStackTrace();
        }
    }
}