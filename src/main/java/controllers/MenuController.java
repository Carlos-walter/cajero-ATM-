package controllers;

import application.model.Cajero;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import application.model.utils.Sesion;

import java.io.IOException;
import java.net.URL;

public class MenuController {

    @FXML
    private VBox vboxCerrarSesion;

    @FXML
    private VBox vboxRetirar;

    @FXML
    private VBox vboxTransferencia;

    @FXML
    private VBox vboxCambiarPin;

    @FXML
    private VBox vboxSaldo;

    @FXML
    private VBox vboxHistorial;

    @FXML
    private VBox vboxBloquear;


    @FXML
    public void initialize() {

        // CERRAR SESIÓN

        if (vboxCerrarSesion != null) {

            vboxCerrarSesion.setOnMouseClicked(
                    event -> cerrarSesion()
            );
        }

        if (vboxRetirar != null) {

            vboxRetirar.setOnMouseClicked(
                    event -> abrirRetiro()
            );
        }

        if (vboxTransferencia != null) {

            vboxTransferencia.setOnMouseClicked(
                    event -> abrirTransferencia()
            );
        }

        if (vboxCambiarPin != null) {

            vboxCambiarPin.setOnMouseClicked(
                    event -> abrirCambiarPin()
            );
        }

        if (vboxSaldo != null) {

            vboxSaldo.setOnMouseClicked(
                    event -> abrirCuentaSaldo()
            );

        }

        if (vboxHistorial != null) {

            vboxHistorial.setOnMouseClicked(
                    event -> abrirHistorial()
            );

        }


        if (vboxBloquear != null) {

            vboxBloquear.setOnMouseClicked(
                    event -> abrirBloquearCuenta()
            );
        }
    }


    private void cerrarSesion() {

        Cajero.getInstancia().cerrarSesion();

        Sesion.cerrarSesion();

        cambiarPantalla("/LoginDNI.fxml");
    }

    private void abrirRetiro() {

        cambiarPantalla("/RetirarDinero.fxml");
    }


    private void abrirTransferencia() {

        cambiarPantalla("/Transferencia.fxml");
    }

    private void abrirCuentaSaldo() {

        cambiarPantalla("/CuentaSaldo.fxml");

    }

    private void abrirHistorial() {

        cambiarPantalla("/Historial.fxml");

    }

    private void abrirBloquearCuenta() {

        cambiarPantalla("/BloquearCuenta.fxml");
    }


    private void abrirCambiarPin() {

        cambiarPantalla("/CambiarPin.fxml");
    }

    /**
     * Método reutilizable para
     * cambiar entre pantallas.
     *
     * @param rutaFxml ruta del archivo FXML
     */
    private void cambiarPantalla(String rutaFxml) {

        try {

            URL url = getClass().getResource(rutaFxml);

            if (url == null) {

                System.out.println(
                        "No se encontró el archivo: "
                                + rutaFxml
                );

                return;
            }

            Parent root = FXMLLoader.load(url);

            Stage stage = (Stage)
                    vboxCerrarSesion
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {

            System.out.println(
                    "Error al abrir: "
                            + rutaFxml
            );

            e.printStackTrace();
        }
    }
}