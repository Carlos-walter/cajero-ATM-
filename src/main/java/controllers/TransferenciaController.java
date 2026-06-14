package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class TransferenciaController implements TecladoListener {

    @FXML
    private TextField txtCuentaDestino;

    @FXML
    private Label lblMensaje;


    @FXML
    private TecladoController tecladoController;

    /**
     * Longitud máxima permitida para una cuenta.
     */
    private static final int MAX_DIGITOS = 22;

    @FXML
    public void initialize() {

        if (tecladoController != null) {

            tecladoController.setListener(this);

        }

        // Asegura que el campo empiece vacío
        txtCuentaDestino.clear();

        actualizarContador();
    }

    /**
     * Actualiza:
     * Soportando hasta 22 dígitos (X/22 en pantalla)
     */
    private void actualizarContador() {

        int cantidad = txtCuentaDestino.getText().length();

        lblMensaje.setText(
                "Soportando hasta 22 dígitos ("
                        + cantidad
                        + "/22 en pantalla)."
        );
    }


    /**
     * Borra completamente el número ingresado.
     */
    @FXML
    public void borrarTodo() {

        txtCuentaDestino.clear();

        actualizarContador();
    }
    @FXML
    private Button btnCancelar;
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
        }
    }

    // ==========================
    // TECLADO NUMÉRICO
    // ==========================

    /**
     * Agrega un dígito al TextField.
     * Máximo: 22 caracteres.
     */
    @Override
    public void onDigito(String digito) {

        if (txtCuentaDestino.getText().length() < MAX_DIGITOS) {

            txtCuentaDestino.appendText(digito);

            actualizarContador();
        }
    }

    /**
     * Borra únicamente el último carácter.
     */
    @Override
    public void onBorrar() {

        String texto = txtCuentaDestino.getText();

        if (!texto.isEmpty()) {

            txtCuentaDestino.setText(
                    texto.substring(0, texto.length() - 1)
            );

            actualizarContador();
        }
    }



    /**
     * Se ejecuta al presionar CONFIRMAR del teclado.
     *
     * FUTURA LÓGICA CON BASE DE DATOS:
     *
     * 1. Validar que la cuenta no esté vacía.
     * 2. Verificar que tenga exactamente 22 dígitos.
     * 3. SELECT * FROM cuentas WHERE numero_cuenta = ?
     * 4. Si no existe → mostrar error.
     * 5. Si existe → obtener datos del destinatario.
     * 6. Guardar la cuenta seleccionada.
     * 7. Abrir TransferenciaPaso2.fxml
     */
    @Override
    public void onEntrar() {

        String cuenta = txtCuentaDestino.getText();

        if (cuenta.isBlank()) {

            lblMensaje.setText("Ingrese una cuenta destino.");

            return;
        }

        if (cuenta.length() != 22) {

            lblMensaje.setText(
                    "La cuenta debe tener 22 dígitos."
            );

            return;
        }


        // TODO:
        // SELECT * FROM cuentas WHERE numero_cuenta = ?

        System.out.println(
                "Cuenta ingresada: " + cuenta
        );

        // TODO:
        // abrir TransferenciaPaso2.fxml
    }
}