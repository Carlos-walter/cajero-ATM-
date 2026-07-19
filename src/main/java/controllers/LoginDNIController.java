package controllers;

import application.model.Cajero;
import application.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.model.utils.Sesion;

public class LoginDNIController implements TecladoListener {

    @FXML
    private TextField txtDni;

    @FXML
    private Label lblBienvenidaPIN;

    @FXML
    private TecladoController tecladoController;

    private static final int MAX_DIGITOS = 8;


    @FXML
    public void initialize() {

        if (tecladoController != null) {
            tecladoController.setListener(this);
        }

        txtDni.setEditable(false);
        txtDni.clear();

        // Mensaje inicial
        lblBienvenidaPIN.setText("Ingrese su DNI de 8 dígitos");
    }



    @Override
    public void onDigito(String digito) {

        if (txtDni.getText().length() < MAX_DIGITOS) {

            txtDni.appendText(digito);

            // Limpia mensajes de error al escribir
            lblBienvenidaPIN.setText(
                    "Ingrese su DNI de 8 dígitos"
            );

        }

    }


    @Override
    public void onBorrar() {

        String texto = txtDni.getText();

        if (!texto.isEmpty()) {

            txtDni.setText(
                    texto.substring(0, texto.length() - 1)
            );

        }

    }


    @Override
    public void onEntrar() {

        String dni = txtDni.getText().trim();


        if (dni.length() < MAX_DIGITOS) {

            lblBienvenidaPIN.setText(
                    "El DNI debe tener 8 dígitos"
            );

            txtDni.clear();
            return;
        }


        if (!dni.matches("\\d{8}")) {

            lblBienvenidaPIN.setText(
                    "El DNI solo debe contener números"
            );

            txtDni.clear();
            return;
        }


        Usuario usuario = Cajero.getInstancia()
                .buscarUsuarioPorDNI(dni);


        if (usuario == null) {

            lblBienvenidaPIN.setText(
                    "DNI no encontrado"
            );

            txtDni.clear();
            return;
        }


        Sesion.setUsuarioActual(usuario);


        lblBienvenidaPIN.setText("");


        // Ir a pantalla de PIN
        cambiarPantalla("/Login.fxml");

    }


    private void cambiarPantalla(String rutaFXML) {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(rutaFXML)
            );


            Stage stage = (Stage)
                    txtDni.getScene().getWindow();


            stage.setScene(
                    new Scene(root)
            );

            stage.show();


        } catch (Exception e) {

            lblBienvenidaPIN.setText(
                    "Error al cargar la pantalla"
            );

            e.printStackTrace();

        }

    }

}