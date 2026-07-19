package controllers;

import application.model.Cajero;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import application.model.utils.Sesion;

public class CambiarPinController implements TecladoListener {

    /**
     * Estados del flujo de cambio de PIN.
     */
    private enum EstadoCambioPin {
        VALIDAR_PIN_ACTUAL,
        INGRESAR_NUEVO_PIN,
        CONFIRMAR_NUEVO_PIN,
        FINALIZADO
    }

    @FXML
    private Text Pinactual;

    @FXML
    private PasswordField txtPin;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnCancelar;

    @FXML
    private TecladoController tecladoController;


    private String pinActualSistema = "1234";


    private String pinNuevoTemporal = "";


    private EstadoCambioPin estado = EstadoCambioPin.VALIDAR_PIN_ACTUAL;


    private static final int MAX_DIGITOS = 4;

    @FXML
    public void initialize() {

        if (tecladoController != null) {
            tecladoController.setListener(this);
            System.out.println("Teclado conectado");
        }

        txtPin.clear();
        lblMensaje.setText("");
        estado = EstadoCambioPin.VALIDAR_PIN_ACTUAL;
        actualizarInstruccion();
    }


    private void actualizarInstruccion() {

        switch (estado) {
            case VALIDAR_PIN_ACTUAL:
                Pinactual.setText("1. Ingrese su PIN de seguridad actual");
                break;

            case INGRESAR_NUEVO_PIN:
                Pinactual.setText("2. Ingrese su nueva clave");
                break;

            case CONFIRMAR_NUEVO_PIN:
                Pinactual.setText("3. Repita su nueva clave");
                break;

            case FINALIZADO:
                Pinactual.setText("PIN cambiado correctamente");
                break;
        }
    }


    private boolean esPinValido(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }




    @Override
    public void onDigito(String digito) {

        if (estado == EstadoCambioPin.FINALIZADO) {
            return;
        }

        lblMensaje.setText("");

        if (txtPin.getText().length() < MAX_DIGITOS) {
            txtPin.appendText(digito);
        }
    }

    @Override
    public void onBorrar() {

        if (estado == EstadoCambioPin.FINALIZADO) {
            return;
        }

        String texto = txtPin.getText();

        if (!texto.isEmpty()) {
            txtPin.setText(texto.substring(0, texto.length() - 1));
        }
    }

    @Override
    public void onEntrar() {

        if (estado == EstadoCambioPin.FINALIZADO) {
            lblMensaje.setText("El PIN ya fue cambiado.");
            return;
        }

        String pinIngresado = txtPin.getText();

        if (pinIngresado.isBlank()) {
            lblMensaje.setText("Ingrese un PIN.");
            return;
        }

        if (!esPinValido(pinIngresado)) {
            lblMensaje.setText("El PIN debe tener 4 dígitos.");
            return;
        }

        switch (estado) {

            case VALIDAR_PIN_ACTUAL:

                if (!Sesion.getUsuarioActual().validarPin(pinIngresado)) {

                    lblMensaje.setText("PIN incorrecto.");
                    txtPin.clear();
                    return;
                }

                txtPin.clear();
                lblMensaje.setText("Ingrese el nuevo PIN.");

                estado = EstadoCambioPin.INGRESAR_NUEVO_PIN;
                actualizarInstruccion();

                break;

            case INGRESAR_NUEVO_PIN:

                pinNuevoTemporal = pinIngresado;

                txtPin.clear();

                estado = EstadoCambioPin.CONFIRMAR_NUEVO_PIN;

                lblMensaje.setText("Repita el nuevo PIN.");

                actualizarInstruccion();

                break;

            case CONFIRMAR_NUEVO_PIN:

                if (!pinIngresado.equals(pinNuevoTemporal)) {

                    lblMensaje.setText("Los PIN no coinciden.");

                    txtPin.clear();

                    estado = EstadoCambioPin.INGRESAR_NUEVO_PIN;

                    actualizarInstruccion();

                    return;
                }

                boolean cambio =
                        Cajero.getInstancia()
                                .cambiarPin(pinNuevoTemporal);

                if (cambio) {

                    lblMensaje.setText("PIN cambiado correctamente.");

                    estado = EstadoCambioPin.FINALIZADO;

                } else {

                    lblMensaje.setText("No fue posible cambiar el PIN.");
                }

                txtPin.clear();

                actualizarInstruccion();

                break;
        }
    }

    /**
     * Cancela la operación y vuelve al menú.
     */
    @FXML
    public void volverMenu() {

        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Menu.fxml")
            );

            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            lblMensaje.setText("Error al volver al menú.");
        }
    }
}