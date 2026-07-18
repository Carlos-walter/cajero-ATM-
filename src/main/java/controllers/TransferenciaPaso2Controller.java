package controllers;

import application.model.Cajero;
import application.model.Cuenta;
import application.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TransferenciaPaso2Controller {

    @FXML private Label lblTitular;
    @FXML private Label lblCuentaDestino;
    @FXML private Label lblBanco;
    @FXML private Label lblTipoCuenta;
    @FXML private Button btnCorregir;
    @FXML private Button btnConfirmar;

    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;

    /**
     * Recibe las cuentas real de origen y destino
     * desde TransferenciaController (Paso 1).
     */
    public void setDatos(Cuenta cuentaOrigen, Cuenta cuentaDestino) {

        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;

        Usuario titular = cuentaDestino.getPropietario();

        lblTitular.setText(titular != null ? titular.getNombre() : "----");
        lblCuentaDestino.setText(cuentaDestino.getNumeroCuenta());
        lblBanco.setText("Banco Central");
        lblTipoCuenta.setText(cuentaDestino.getTipo());
    }

    @FXML
    public void volverPaso1() {
        cambiarPantalla("/Transferencia.fxml");
    }

    @FXML
    public void continuarPaso3() {
        abrirPaso3();
    }

    private void abrirPaso3() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TransferenciaPaso3.fxml"));
            Parent root = loader.load();

            TransferenciaPaso3Controller controller = loader.getController();
            controller.setDatos(cuentaOrigen, cuentaDestino);

            Stage stage = (Stage) btnConfirmar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cambiarPantalla(String rutaFxml) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFxml));
            Stage stage = (Stage) btnConfirmar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}