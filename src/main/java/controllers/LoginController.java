package controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import application.model.Cajero;
import utils.Sesion;

/**
 * Controlador principal para la pantalla de Login.
 * Gestiona la validación del PIN y la navegación hacia el menú principal.
 */
public class LoginController implements TecladoListener {

    @FXML private PasswordField PasswordField;
    @FXML private TecladoController tecladoController;
    @FXML private Label lblBienvenidaPIN;

    private int intentosFallidos = 0;
    private final String PIN_CORRECTO = "1234";

    /**
     * Inicializa el controlador y vincula el teclado al listener.
     */
    @FXML
    public void initialize() {
        if (tecladoController != null) {
            tecladoController.setListener(this);
        }
    }

    // --- Métodos de la Interfaz TecladoListener ---

    @Override
    public void onDigito(String digito) {
        if (PasswordField.getText().length() < 4) {
            PasswordField.appendText(digito);
        }
    }

    @Override
    public void onBorrar() {
        String texto = PasswordField.getText();
        if (texto.length() > 0) {
            PasswordField.setText(texto.substring(0, texto.length() - 1));
        }
    }
    /**
     * Valida el PIN ingresado.
     *
     * Flujo actual:
     * 1. Compara el PIN ingresado con el PIN simulado.
     * 2. Si es correcto, reinicia el contador de intentos
     *    y abre el menú principal.
     * 3. Si es incorrecto, incrementa el contador de intentos,
     *    limpia el campo y ejecuta una animación de error.
     * 4. Al tercer intento fallido, la cuenta queda bloqueada
     *    y se muestra la pantalla de bloqueo.
     *
     * FUTURO (Base de Datos):
     * 1. Buscar la cuenta mediante el DNI ingresado.
     * 2. Obtener el PIN almacenado.
     * 3. Comparar el PIN ingresado con el registrado.
     * 4. Actualizar el número de intentos fallidos.
     * 5. Si llega a 3 intentos:
     *
     *    UPDATE cuentas
     *    SET estado = 'BLOQUEADA'
     *    WHERE id_cuenta = ?
     *
     * 6. Mostrar CuentaBloqueada.fxml.
     */
    @Override
    public void onEntrar() {

        String dni = "12345678";
        String pin = PasswordField.getText();

        boolean acceso = Cajero.getInstancia().autenticarUsuario(dni, pin);

        if (acceso) {

            Sesion.setUsuarioActual(
                    Cajero.getInstancia().getUsuarioActual()
            );

            cambiarEscena("Menu.fxml");

        } else {

            intentosFallidos++;

            PasswordField.clear();

            vibrarInterfaz();

            lblBienvenidaPIN.setText(
                    "PIN incorrecto."
            );

            if (intentosFallidos >= 3) {

                cambiarEscena("CuentaBloqueada.fxml");
            }
        }
    }

    /**
     * Realiza el cambio de escena hacia el archivo FXML especificado.
     * @param fxml Nombre del archivo fxml a cargar (ej: "Menu.fxml").
     */
    private void cambiarEscena(String fxml) {
        try {
            // Buscamos en la raíz del classpath
            URL url = getClass().getResource("/" + fxml);

            if (url == null) return;

            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) PasswordField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta una animación de vibración sobre el campo de contraseña.
     */
    private void vibrarInterfaz() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), PasswordField);
        tt.setFromX(-10f);
        tt.setByX(20f);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }
}