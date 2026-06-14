package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class CambiarPinController implements TecladoListener {

    @FXML
    private PasswordField txtPin;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnCancelar;

    @FXML
    private TecladoController tecladoController;

    /**
     * PIN actual simulado.
     *
     * Más adelante este valor deberá
     * obtenerse desde la base de datos.
     */
    private final String pinActualSistema = "1234";

    /**
     * Inicializa la pantalla.
     */
    @FXML
    public void initialize() {

        if (tecladoController != null) {

            tecladoController.setListener(this);

            System.out.println("Teclado conectado");
        }

        txtPin.clear();
        lblMensaje.setText("");
    }

    // ==========================
    // TECLADO NUMÉRICO
    // ==========================

    /**
     * Agrega un dígito al PIN.
     *
     * El PIN tiene un máximo de 4 dígitos.
     */
    @Override
    public void onDigito(String digito) {

        // Limpia mensajes anteriores
        lblMensaje.setText("");

        if (txtPin.getText().length() < 4) {

            txtPin.appendText(digito);
        }
    }

    /**
     * Borra únicamente el último dígito.
     */
    @Override
    public void onBorrar() {

        String texto = txtPin.getText();

        if (!texto.isEmpty()) {

            txtPin.setText(
                    texto.substring(0, texto.length() - 1)
            );
        }
    }

    /**
     * Confirma el PIN actual.
     *
     * Validaciones:
     * 1. No puede estar vacío.
     * 2. Debe tener exactamente 4 dígitos.
     * 3. Debe coincidir con el PIN registrado.
     */
    @Override
    public void onEntrar() {

        String pinIngresado = txtPin.getText();

        if (pinIngresado.isBlank()) {

            lblMensaje.setText(
                    "Ingrese su PIN actual."
            );

            return;
        }

        if (pinIngresado.length() != 4) {

            lblMensaje.setText(
                    "El PIN debe tener 4 dígitos."
            );

            return;
        }

        if (!pinIngresado.equals(pinActualSistema)) {

            lblMensaje.setText(
                    "PIN incorrecto."
            );

            txtPin.clear();

            return;
        }

        lblMensaje.setText(
                "PIN verificado correctamente."
        );

        System.out.println(
                "PIN correcto. Ir al paso 2."
        );

        // TODO:
        // Abrir pantalla NuevoPin.fxml
        // para ingresar el nuevo PIN.
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

            Stage stage = (Stage) btnCancelar
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            lblMensaje.setText(
                    "Error al volver al menú."
            );
        }
    }
}