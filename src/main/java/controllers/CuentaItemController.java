package controllers;

import application.model.Cuenta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import application.model.utils.Sesion;

public class CuentaItemController {

    @FXML
    private Label lblTipoCuenta;

    @FXML
    private Label lblNumeroCuenta;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblSaldo;

    @FXML
    private Button btnSeleccionar;

    private Cuenta cuenta;

    private CuentaSaldoController controllerPadre;

    @FXML
    private void seleccionarCuenta() {

        if (cuenta == null) {
            System.out.println("No hay cuenta seleccionada.");
            return;
        }

        if (Sesion.getUsuarioActual() == null) {
            System.out.println("No existe una sesión activa.");
            return;
        }

        Sesion.getUsuarioActual().setCuentaSeleccionada(cuenta);

        System.out.println("Cuenta seleccionada: " + cuenta.getNumeroCuenta());

        if (controllerPadre != null) {
            controllerPadre.cambiarPantalla("/Menu.fxml");
        }

    }

    public void setDatos(Cuenta cuenta) {

        this.cuenta = cuenta;

        lblTipoCuenta.setText(cuenta.getTipo());

        lblNumeroCuenta.setText(ocultarNumero(cuenta.getNumeroCuenta()));

        lblEstado.setText("Activa");

        lblSaldo.setText(String.format("Saldo: S/ %.2f", cuenta.getSaldo()));
    }

    public void setControllerPadre(CuentaSaldoController controllerPadre) {
        this.controllerPadre = controllerPadre;
    }

    private String ocultarNumero(String numero) {

        if (numero.length() <= 4) {
            return numero;
        }

        return "**** **** **** **** **" +
                numero.substring(numero.length() - 4);
    }

}