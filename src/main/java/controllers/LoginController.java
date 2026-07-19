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
import application.model.utils.Sesion;


public class LoginController implements TecladoListener {

    @FXML private PasswordField PasswordField;
    @FXML private TecladoController tecladoController;
    @FXML private Label lblBienvenidaPIN;

    private int intentosFallidos = 0;

    @FXML
    public void initialize() {
        if (tecladoController != null) {
            tecladoController.setListener(this);
        }
    }



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

        String dni = Sesion.getUsuarioActual().getDni();
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


    private void vibrarInterfaz() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), PasswordField);
        tt.setFromX(-10f);
        tt.setByX(20f);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }
}