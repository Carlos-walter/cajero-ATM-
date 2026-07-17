package controllers;

import application.model.Cajero;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Sesion;

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

    /**
     * Configura los eventos de clic
     * para cada opción del menú.
     */
    @FXML
    public void initialize() {

        // CERRAR SESIÓN

        if (vboxCerrarSesion != null) {

            vboxCerrarSesion.setOnMouseClicked(
                    event -> cerrarSesion()
            );
        }

        // ==========================
        // RETIRAR DINERO
        // ==========================
        if (vboxRetirar != null) {

            vboxRetirar.setOnMouseClicked(
                    event -> abrirRetiro()
            );
        }

        // ==========================
        // TRANSFERENCIA
        // ==========================
        if (vboxTransferencia != null) {

            vboxTransferencia.setOnMouseClicked(
                    event -> abrirTransferencia()
            );
        }

        // ==========================
        // CAMBIAR PIN
        // ==========================
        if (vboxCambiarPin != null) {

            vboxCambiarPin.setOnMouseClicked(
                    event -> abrirCambiarPin()
            );
        }

        // ==========================
        // FUNCIONES FUTURAS
        // ==========================

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

// BLOQUEAR CUENTA

        if (vboxBloquear != null) {

            vboxBloquear.setOnMouseClicked(
                    event -> abrirBloquearCuenta()
            );
        }
    }

    /**
     * Regresa a la pantalla de Login.
     *
     * FUTURO:
     * Limpiar la sesión del usuario.
     */
    private void cerrarSesion() {

        Cajero.getInstancia().cerrarSesion();

        Sesion.cerrarSesion();

        cambiarPantalla("/LoginDNI.fxml");
    }

    /**
     * Abre la pantalla de retiro.
     */
    private void abrirRetiro() {

        cambiarPantalla("/RetirarDinero.fxml");
    }

    /**
     * Abre la pantalla de transferencia.
     */
    private void abrirTransferencia() {

        cambiarPantalla("/Transferencia.fxml");
    }
    /**
     * Abre la pantalla para seleccionar
     * la cuenta del usuario.
     */
    private void abrirCuentaSaldo() {

        cambiarPantalla("/CuentaSaldo.fxml");

    }
    /**
     * Abre la pantalla del historial de movimientos.
     */
    private void abrirHistorial() {

        cambiarPantalla("/Historial.fxml");

    }
    //  Abre la pantalla de BloquearCuenta
    private void abrirBloquearCuenta() {

        cambiarPantalla("/BloquearCuenta.fxml");
    }

    /**
     * Abre la pantalla para validar
     * el PIN actual del usuario.
     *
     * Flujo futuro:
     *
     * Paso 1 -> PIN actual
     * Paso 2 -> Nuevo PIN
     * Paso 3 -> Confirmación
     */
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