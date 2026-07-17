package controllers;

import application.model.Cajero;
import application.model.Transaccion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

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

        // Datos de prueba.
        // Cuando se abra desde una operación,
        // cargarVoucher(...) reemplazará estos valores.

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
     * Llena el voucher con una transacción real.
     */
    public void cargarVoucher(Transaccion transaccion) {

        DateTimeFormatter fecha =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DateTimeFormatter hora =
                DateTimeFormatter.ofPattern("HH:mm:ss");

        lblFechaLlenar.setText(
                transaccion.getFechaTransaccion().format(fecha)
        );

        lblHoraLlenar.setText(
                transaccion.getFechaTransaccion().format(hora)
        );

        lblATMLlenar.setText("ATM-001");

        lblOperacionLlenar.setText(
                transaccion.getTipoTransaccion()
                        .toString()
                        .toUpperCase()
        );

        if (Cajero.getInstancia().getUsuarioActual() != null) {

            lblClienteLlenar.setText(
                    Cajero.getInstancia()
                            .getUsuarioActual()
                            .getNombre()
            );

            lblSaldoDisponibleLlenar.setText(
                    "S/ "
                            + String.format(
                            "%.2f",
                            Cajero.getInstancia()
                                    .getUsuarioActual()
                                    .getCuentaSeleccionada()
                                    .getSaldo()
                    )
            );

        } else {

            lblClienteLlenar.setText("----");
            lblSaldoDisponibleLlenar.setText("----");

        }

        lblCuentaOrigenLlenar.setText(
                ocultarCuenta(
                        transaccion.getCuentaOrigen()
                )
        );

        lblCuentaDestinoLlenar.setText(
                ocultarCuenta(
                        transaccion.getCuentaDestino()
                )
        );

        lblMontoLlenar.setText(
                "S/ "
                        + String.format(
                        "%.2f",
                        transaccion.getMonto()
                )
        );

        lblComisionLlenar.setText("S/ 0.00");

    }

    /**
     * Oculta los primeros dígitos de una cuenta.
     */
    private String ocultarCuenta(String cuenta) {

        if (cuenta == null)
            return "-";

        if (cuenta.equalsIgnoreCase("CAJERO"))
            return "CAJERO";

        if (cuenta.equalsIgnoreCase("EXTERNO"))
            return "EXTERNO";

        if (cuenta.length() <= 4)
            return cuenta;

        return "**************"
                + cuenta.substring(cuenta.length() - 4);

    }

    /**
     * Imprimir voucher.
     */
    @FXML
    public void imprimirVoucher() {

        System.out.println("Imprimiendo voucher...");

    }

    /**
     * Finalizar.
     */
    @FXML
    public void irMenu() {

        cambiarPantalla("/Menu.fxml");

    }

    /**
     * Cambio de pantalla reutilizable.
     */
    private void cambiarPantalla(String rutaFXML) {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(rutaFXML)
                    );

            Stage stage =
                    (Stage) btnFinalizar
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}