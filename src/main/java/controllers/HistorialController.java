package controllers;

import application.model.Cajero;
import application.model.Transaccion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistorialController {

    @FXML
    private TableView<Transaccion> tblHistorial;

    @FXML
    private TableColumn<Transaccion, String> colFecha;

    @FXML
    private TableColumn<Transaccion, String> colOperacion;

    @FXML
    private TableColumn<Transaccion, String> colReferencia;

    @FXML
    private TableColumn<Transaccion, String> colMonto;

    @FXML
    private TableColumn<Transaccion, String> colVoucher;

    @FXML
    private Button btnRegresar;

    @FXML
    public void initialize() {

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        colFecha.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue()
                                .getFechaTransaccion()
                                .format(formato)
                )
        );

        colOperacion.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue()
                                .getTipoTransaccion()
                                .toString()
                                .toUpperCase()
                )
        );

        colReferencia.setCellValueFactory(cell -> {

            String referencia =
                    cell.getValue().getCuentaDestino();

            if (referencia == null)
                referencia = "-";

            return new SimpleStringProperty(referencia);

        });

        colMonto.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        "S/ "
                                + String.format(
                                "%.2f",
                                cell.getValue().getMonto()
                        )
                )
        );

        colVoucher.setCellValueFactory(cell ->
                new SimpleStringProperty("Ver")
        );

        cargarHistorial();

        tblHistorial.setRowFactory(tv -> {

            TableRow<Transaccion> fila =
                    new TableRow<>();

            fila.setOnMouseClicked(event -> {

                if (event.getClickCount() == 2
                        && !fila.isEmpty()) {

                    abrirVoucher(
                            fila.getItem()
                    );

                }

            });

            return fila;

        });

    }

    /**
     * Carga las operaciones del usuario.
     */
    private void cargarHistorial() {

        List<Transaccion> historial =
                Cajero.getInstancia()
                        .verHistorial(30);

        tblHistorial.setItems(
                FXCollections.observableArrayList(historial)
        );

    }

    /**
     * Abre el voucher de la transacción.
     */
    private void abrirVoucher(Transaccion transaccion) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/Voucher.fxml")
                    );

            Parent root = loader.load();

            VoucherController controller =
                    loader.getController();

            controller.cargarVoucher(transaccion);

            Stage stage =
                    (Stage) btnRegresar
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * Regresa al menú principal.
     */
    @FXML
    public void volverMenu() {

        cambiarPantalla("/Menu.fxml");

    }

    /**
     * Método reutilizable para cambiar de pantalla.
     */
    private void cambiarPantalla(String ruta) {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(ruta)
                    );

            Stage stage =
                    (Stage) btnRegresar
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}