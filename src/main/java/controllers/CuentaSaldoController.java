package controllers;

import application.model.Cuenta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import application.model.utils.Sesion;

public class CuentaSaldoController {

    @FXML
    private VBox contenedorCuentas;

    @FXML
    private Button btnRegresar;

    @FXML
    public void initialize() {

        cargarCuentas();

    }

    private void cargarCuentas() {

        contenedorCuentas.getChildren().clear();

        if (Sesion.getUsuarioActual() == null) {
            System.out.println("No existe un usuario en sesión.");
            return;
        }

        try {

            for (Cuenta cuenta : Sesion.getUsuarioActual().getCuentas()) {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/CuentaItem.fxml")
                );

                Parent item = loader.load();

                CuentaItemController controller =
                        loader.getController();

                controller.setDatos(cuenta);
                controller.setControllerPadre(this);

                contenedorCuentas.getChildren().add(item);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML
    private void volverMenu(ActionEvent event) {

        cambiarPantalla("/Menu.fxml");

    }

    public void cambiarPantalla(String rutaFXML) {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(rutaFXML)
            );

            btnRegresar.getScene().setRoot(root);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}