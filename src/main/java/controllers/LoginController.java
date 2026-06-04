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

    @Override
    public void onEntrar() {
        if (PasswordField.getText().equals(PIN_CORRECTO)) {
            intentosFallidos = 0;
            cambiarEscena("Menu.fxml");
        } else {
            intentosFallidos++;
            vibrarInterfaz();
            PasswordField.clear();

            if (intentosFallidos >= 3) {
                lblBienvenidaPIN.setText("CUENTA BLOQUEADA ");
                lblBienvenidaPIN.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
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