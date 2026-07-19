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