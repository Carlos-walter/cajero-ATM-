package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RetirarDineroController implements TecladoListener {

    @FXML
    private TextField txtMonto;

    @FXML
    private Label txtMesajefinal;

    @FXML
    private TecladoController tecladoController;

    /**
     * Saldo disponible temporal.
     *
     * En una aplicación real este valor
     * vendría desde la base de datos.
     */
    private int saldoDisponible = 500;

    /**
     * Texto mostrado cuando aún no se ha ingresado un monto.
     */
    private static final String MONTO_INICIAL = "S/ 0.00";

    @FXML
    public void initialize() {

        if (tecladoController != null) {

            tecladoController.setListener(this);

            System.out.println("Teclado conectado");
        }

        // Mostrar monto inicial en pantalla.
        txtMonto.setText(MONTO_INICIAL);

        txtMesajefinal.setText(
                "Saldo Disponible: S/ " + saldoDisponible
        );
    }

    /**
     * Botones de retiro rápido.
     *
     * Permiten seleccionar un monto sin
     * necesidad de utilizar el teclado.
     */
    @FXML
    public void montoRapido(ActionEvent event) {

        Button btn = (Button) event.getSource();

        String valor = btn.getText()
                .replace("S/ ", "");

        txtMonto.setText(valor);

        System.out.println(
                "Monto seleccionado: S/ " + valor
        );
    }

    /**
     * Limpia el monto ingresado y vuelve
     * a mostrar el valor inicial.
     */
    @FXML
    public void limpiarMonto() {

        txtMonto.setText(MONTO_INICIAL);

        txtMesajefinal.setText(
                "Ingrese un nuevo monto."
        );
    }


    @Override
    public void onDigito(String digito) {

        String texto = txtMonto.getText();

        if (texto.equals(MONTO_INICIAL)) {

            txtMonto.setText(digito);

        } else {

            txtMonto.appendText(digito);
        }
    }

    /**
     * Borra únicamente el último carácter.
     *
     * Si el campo queda vacío, vuelve al
     * valor inicial "S/ 0.00".
     */
    @Override
    public void onBorrar() {

        String texto = txtMonto.getText();

        if (texto.equals(MONTO_INICIAL)) {
            return;
        }

        if (!texto.isEmpty()) {

            texto = texto.substring(0, texto.length() - 1);

            if (texto.isEmpty()) {

                txtMonto.setText(MONTO_INICIAL);

            } else {

                txtMonto.setText(texto);
            }
        }
    }

    /**
     * Procesa el retiro de dinero.
     *
     * Validaciones:
     * 1. Debe ingresarse un monto.
     * 2. El monto debe ser mayor que cero.
     * 3. El cajero no trabaja con monedas.
     */
    @Override
    public void onEntrar() {

        String texto = txtMonto.getText();

        if (texto.equals(MONTO_INICIAL)
                || texto.isBlank()) {

            txtMesajefinal.setText(
                    "Ingrese un monto."
            );

            return;
        }

        try {

            int monto = Integer.parseInt(texto);

            if (monto <= 0) {

                txtMesajefinal.setText(
                        "Monto inválido."
                );

                return;
            }

            if (monto % 10 != 0) {

                txtMesajefinal.setText(
                        "El cajero no trabaja con monedas."
                );

                return;
            }

            if (monto > saldoDisponible) {

                txtMesajefinal.setText(
                        "Saldo insuficiente."
                );

                return;
            }

            // Descontar dinero del saldo.
            saldoDisponible -= monto;

            txtMesajefinal.setText(
                    "Retiro exitoso. Saldo restante: S/ "
                            + saldoDisponible
            );

            // Volver al valor inicial.
            txtMonto.setText(MONTO_INICIAL);

        } catch (NumberFormatException e) {

            txtMesajefinal.setText(
                    "Ingrese únicamente números."
            );
        }
    }
}