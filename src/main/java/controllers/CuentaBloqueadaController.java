package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Controlador de la pantalla Cuenta Bloqueada.
 *
 * Esta pantalla puede abrirse desde dos escenarios:
 *
 * 1. El usuario decide bloquear voluntariamente su cuenta.
 * 2. El sistema bloquea automáticamente la cuenta al superar
 *    el número máximo de intentos de PIN incorrectos.
 *
 * Actualmente los datos del ticket son simulados.
 *
 * FUTURO:
 * - Obtener la información desde la base de datos.
 * - Registrar el evento de bloqueo.
 * - Guardar el motivo del bloqueo.
 * - Generar un código único de referencia.
 */
public class CuentaBloqueadaController {



    @FXML
    private Label lblCodigoRef;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblEstablecimiento;

    @FXML
    private Label lblEstado;

    @FXML
    private Button btnFinalizar;


    @FXML
    public void initialize() {

        cargarTicket();
    }

    /**
     * Llena el ticket con información simulada.
     *
     * FUTURO:
     * Estos datos deberán venir desde la base de datos
     * después de registrar el bloqueo.
     */
    private void cargarTicket() {

        lblCodigoRef.setText(
                "CODIGO REF: SEC-45920-ATM-BCR"
        );

        lblFecha.setText(
                "FECHA SUCESO: "
                        + LocalDate.now().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + " - "
                        + LocalTime.now().format(
                        DateTimeFormatter.ofPattern("HH:mm"))
        );

        lblEstablecimiento.setText(
                "ESTABLECIMIENTO: ATM-7721-LIMA"
        );

        lblEstado.setText(
                "DETALLE ESTADO: BLOQUEADO PERMANENTE"
        );
    }


    // BOTÓN FINALIZAR


    /**
     * Finaliza la operación y regresa al Login.
     *
     * FUTURO:
     * - Cerrar sesión.
     * - Limpiar datos temporales.
     * - Registrar hora de salida.
     */
    @FXML
    private void volverLogin() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/LoginDNI.fxml")
            );

            Stage stage = (Stage)
                    btnFinalizar
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}