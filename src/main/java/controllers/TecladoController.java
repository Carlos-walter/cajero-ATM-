package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TecladoController {
    private TecladoListener listener;

    public void setListener(TecladoListener listener) {
        this.listener = listener;
    }

    @FXML
    void handleNumero(ActionEvent event) {
        if (listener != null) {
            Button btn = (Button) event.getSource();
            listener.onDigito(btn.getText());
        }
    }

    @FXML
    void handleBorrar(ActionEvent event) {
        if (listener != null) listener.onBorrar();
    }

    @FXML
    void handleEntrar(ActionEvent event) {
        if (listener != null) {
            // El teclado solo grita: "¡Alguien presionó Entrar!"
            listener.onEntrar();
        }
    }
}