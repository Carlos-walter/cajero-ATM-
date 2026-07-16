package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VoucherController {

    @FXML
    private Label lblFechaLlenar;

    @FXML
    private Label lblHoraLlenar;

    @FXML
    private Label lblATMLlenar;

    @FXML
    private Label lblOperacionLlenar;

    @FXML
    private Label lblClienteLlenar;

    @FXML
    private Label lblCuentaOrigenLlenar;

    @FXML
    private Label lblCuentaDestinoLlenar;

    @FXML
    private Label lblMontoLlenar;

    @FXML
    private Label lblComisionLlenar;

    @FXML
    private Label lblSaldoDisponibleLlenar;

    @FXML
    private Button btnImprimir;

    @FXML
    private Button btnFinalizar;

    @FXML
    public void initialize() {

      
        // DATOS DE PRUEBA


        lblFechaLlenar.setText("16/07/2026");
        lblHoraLlenar.setText("10:45:32");
        lblATMLlenar.setText("ATM-001");

        lblOperacionLlenar.setText("TRANSFERENCIA");

        lblClienteLlenar.setText("Carlos Chapoñan");

        lblCuentaOrigenLlenar.setText("**************1234");

        lblCuentaDestinoLlenar.setText("**************5678");

        lblMontoLlenar.setText("S/ 100.00");

        lblComisionLlenar.setText("S/ 0.00");

        lblSaldoDisponibleLlenar.setText("S/ 4,150.00");

    }

    /**
     * Imprimir voucher.
     * (Por implementar)
     */
    @FXML
    public void imprimirVoucher() {

        System.out.println("Imprimiendo voucher...");

    }

    /**
     * Finaliza la operación y vuelve al menú principal.
     */
    @FXML
    public void irMenu() {

        cambiarPantalla("/Menu.fxml");

    }

    /**
     * Método reutilizable para cambiar de pantalla.
     */
    private void cambiarPantalla(String rutaFXML) {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(rutaFXML)
            );

            Stage stage = (Stage) btnFinalizar
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));

            stage.show();

        } catch (Exception e) {

            System.out.println("Error al abrir: " + rutaFXML);

            e.printStackTrace();

        }

    }

}