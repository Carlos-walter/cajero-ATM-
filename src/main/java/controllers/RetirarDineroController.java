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

    // Simulación temporal
    private double saldoDisponible = 500.0;

    @FXML
    public void initialize() {

        if (tecladoController != null) {
            tecladoController.setListener(this);
            System.out.println("Teclado conectado");
        }

        txtMesajefinal.setText(
                "Saldo Disponible: S/ " + saldoDisponible
        );
    }

    /**
     * Botones rápidos:
     * S/10, S/20, S/50, S/100, S/200
     */
    @FXML
    public void montoRapido(ActionEvent event) {

        Button btn = (Button) event.getSource();

        String valor = btn.getText()
                .replace("S/ ", "");

        txtMonto.setText(valor);

        System.out.println(
                "Monto seleccionado: " + valor
        );
    }

    /**
     * Limpia el cuadro de texto.
     */
    @FXML
    public void limpiarMonto() {

        txtMonto.clear();

        txtMesajefinal.setText(
                "Monto limpiado"
        );
    }

    // ======================
    // TECLADO NUMÉRICO
    // ======================

    @Override
    public void onDigito(String digito) {

        txtMonto.appendText(digito);
    }

    @Override
    public void onBorrar() {

        String texto = txtMonto.getText();

        if (!texto.isEmpty()) {

            txtMonto.setText(
                    texto.substring(0, texto.length() - 1)
            );
        }
    }

    @Override
    public void onEntrar() {

        String texto = txtMonto.getText();

        if (texto.isBlank()) {

            txtMesajefinal.setText(
                    "Ingrese un monto"
            );

            return;
        }

        try {

            double monto = Double.parseDouble(texto);

            if (monto <= 0) {

                txtMesajefinal.setText(
                        "Monto inválido"
                );

                return;
            }

            if (monto > saldoDisponible) {

                txtMesajefinal.setText(
                        "Saldo insuficiente"
                );

                return;
            }

            saldoDisponible -= monto;

            txtMesajefinal.setText(
                    "Retiro exitoso. Saldo: S/ "
                            + saldoDisponible
            );

            txtMonto.clear();

        } catch (NumberFormatException e) {

            txtMesajefinal.setText(
                    "Ingrese un número válido"
            );
        }
    }
}