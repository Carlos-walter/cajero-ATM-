package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controlador del Paso 2 de la transferencia.
 *
 * Funciones:
 * - Mostrar los datos del destinatario.
 * - Regresar al Paso 1 para corregir la cuenta.
 * - Confirmar los datos y avanzar al Paso 3.
 *
 * FUTURO:
 * Los datos mostrados serán obtenidos desde la base de datos
 * mediante una consulta utilizando el número de cuenta
 * ingresado en el Paso 1.
 */
public class TransferenciaPaso2Controller {

    @FXML
    private Label lblTitular;

    @FXML
    private Label lblCuentaDestino;

    @FXML
    private Label lblBanco;

    @FXML
    private Label lblTipoCuenta;

    @FXML
    private Button btnCorregir;

    @FXML
    private Button btnConfirmar;

    /**
     * Inicializa la pantalla.
     *
     * FUTURO:
     * Aquí se cargarán los datos obtenidos desde
     * la base de datos.
     */
    @FXML
    public void initialize() {

        // ==========================
        // DATOS SIMULADOS
        // ==========================

        lblTitular.setText(
                "JUAN CARLOS PÉREZ"
        );

        lblCuentaDestino.setText(
                "1111111111111111111111"
        );

        lblBanco.setText(
                "BANCO CENTRAL"
        );

        lblTipoCuenta.setText(
                "Caja de Ahorro"
        );

        /*
         * FUTURO - BASE DE DATOS
         *
         * SELECT nombre,
         *        numero_cuenta,
         *        banco,
         *        tipo_cuenta
         * FROM cuentas
         * WHERE numero_cuenta = ?
         *
         * Con esos datos actualizar:
         *
         * lblTitular
         * lblCuentaDestino
         * lblBanco
         * lblTipoCuenta
         */
    }

    /**
     * Regresa al Paso 1 para modificar
     * la cuenta destino.
     */
    @FXML
    public void volverPaso1() {

        cambiarPantalla(
                "/Transferencia.fxml"
        );
    }

    /**
     * Confirma que los datos son correctos
     * y continúa al Paso 3.
     */
    @FXML
    public void continuarPaso3() {

        cambiarPantalla(
                "/TransferenciaPaso3.fxml"
        );
    }

    /**
     * Método reutilizable para cambiar
     * entre pantallas.
     *
     * @param rutaFxml Ruta del archivo FXML.
     */
    private void cambiarPantalla(String rutaFxml) {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(rutaFxml)
            );

            Stage stage = (Stage)
                    btnConfirmar
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        } catch (Exception e) {

            System.out.println(
                    "Error al abrir: "
                            + rutaFxml
            );

            e.printStackTrace();
        }
    }
}