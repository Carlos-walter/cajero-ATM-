package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * UPDATE cuentas
 * SET estado = 'BLOQUEADA'
 * WHERE id_cuenta = ?
 */
public class BloquearCuentaController {

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnBloquearCuenta;

    /**
     * Regresa al menú principal.
     */
    @FXML
    public void volverMenu() {

        cambiarPantalla("/Menu.fxml");
    }

    /**
     * Simula el bloqueo de la cuenta.
     *
     * FUTURO:
     * Aquí se ejecutará el UPDATE en la base
     * de datos para cambiar el estado de la
     * cuenta a BLOQUEADA.
     */
    @FXML
    public void bloquearCuenta() {

        System.out.println(
                "Cuenta bloqueada correctamente."
        );

        cambiarPantalla(
                "/CuentaBloqueada.fxml"
        );
    }

    /**
     * Método reutilizable para cambiar de pantalla.
     */
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