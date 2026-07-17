package controllers;

import application.model.Cuenta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    // Controlador de la pantalla principal
    private CuentaSaldoController controllerPadre;

    @FXML
    public void initialize() {

    }

    public void setDatos(Cuenta cuenta) {

        this.cuenta = cuenta;

        lblTipoCuenta.setText(cuenta.getTipo());

        lblNumeroCuenta.setText(ocultarNumero(cuenta.getNumeroCuenta()));

        lblSaldo.setText(String.format("Saldo: S/ %.2f", cuenta.getSaldo()));

        lblEstado.setText("Activa");
    }

    public void setControllerPadre(CuentaSaldoController controllerPadre) {
        this.controllerPadre = controllerPadre;
    }

    private String ocultarNumero(String numero) {

        if (numero.length() <= 4) {
            return numero;
        }

        return "**** **** **** **** **"
                + numero.substring(numero.length() - 4);
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

}