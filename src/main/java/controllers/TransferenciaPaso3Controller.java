package controllers;

import application.model.Cajero;
import application.model.Cuenta;
import application.model.Transaccion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class TransferenciaPaso3Controller implements TecladoListener {

    @FXML
    private TextField txtMonto;

    /**
     * Controlador del teclado incluido
     * mediante fx:include.
     */
    @FXML
    private TecladoController tecladoController;

    @FXML
    private Button btnRegresar;

    private String monto = "";

    /**
     * Cuentas real de origen y destino,
     * recibidas desde TransferenciaPaso2Controller.
     */
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;

    @FXML
    public void initialize(){

        // Conecta el teclado con esta pantalla
        if(tecladoController != null){
            tecladoController.setListener(this);
        }

        // Evita escritura manual
        txtMonto.setEditable(false);

        actualizarMonto();
    }

    public void setDatos(Cuenta cuentaOrigen, Cuenta cuentaDestino) {
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }


    @Override
    public void onDigito(String digito){

        if(monto.length() < 8){
            monto += digito;
            actualizarMonto();
        }
    }

    @Override
    public void onBorrar(){

        if(!monto.isEmpty()){
            monto = monto.substring(0, monto.length() - 1);
            actualizarMonto();
        }
    }


    @Override
    public void onEntrar(){
        irVoucher();
    }


    private void actualizarMonto(){

        if(monto.isEmpty()){
            txtMonto.setText("S/ 0.00");
        } else {
            txtMonto.setText("S/ " + monto + ".00");
        }
    }


    @FXML
    public void volverPaso2(){
        cambiarPantalla("/TransferenciaPaso2.fxml");
    }


    private void irVoucher(){

        if (monto.isEmpty()) {
            System.out.println("Ingrese un monto");
            return;
        }

        double cantidad = Double.parseDouble(monto);

        if (cantidad <= 0) {
            System.out.println("Monto inválido");
            return;
        }

        if (cantidad > cuentaOrigen.getSaldo()) {
            System.out.println("Saldo insuficiente");
            return;
        }

        Transaccion transaccion = Cajero.getInstancia().transferir(
                cuentaOrigen.getNumeroCuenta(),
                cuentaDestino.getNumeroCuenta(),
                cantidad
        );

        if (transaccion == null) {
            System.out.println("No fue posible realizar la transferencia");
            return;
        }

        abrirVoucher(transaccion);
    }


    private void abrirVoucher(Transaccion transaccion) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Voucher.fxml"));
            Parent root = loader.load();

            VoucherController controller = loader.getController();
            controller.cargarVoucher(transaccion);

            Stage stage = (Stage) btnRegresar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cambiarPantalla(String ruta){

        try{
            Parent root = FXMLLoader.load(getClass().getResource(ruta));
            Stage stage = (Stage) btnRegresar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        }catch(Exception e){
            System.out.println("Error al abrir: " + ruta);
            e.printStackTrace();
        }
    }
}