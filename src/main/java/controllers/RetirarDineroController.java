package controllers;

import application.model.Cajero;
import application.model.Cuenta;
import application.model.Transaccion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.model.utils.Sesion;


public class RetirarDineroController implements TecladoListener {


    @FXML
    private TextField txtMonto;

    @FXML
    private Label txtMesajefinal;

    @FXML
    private TecladoController tecladoController;


    private Cuenta cuenta;


    private static final String MONTO_INICIAL = "S/ 0.00";


    @FXML
    public void initialize() {


        if (tecladoController != null) {

            tecladoController.setListener(this);

        }


        cuenta = Sesion
                .getUsuarioActual()
                .getCuentaSeleccionada();


        txtMonto.setText(
                MONTO_INICIAL
        );


        txtMesajefinal.setText(
                "Saldo Disponible: S/ "
                        + String.format(
                        "%.2f",
                        cuenta.getSaldo()
                )
        );

    }



    @FXML
    public void montoRapido(ActionEvent event) {


        Button boton =
                (Button) event.getSource();


        String monto =
                boton.getText()
                        .replace("S/ ", "");


        txtMonto.setText(monto);

    }


    @Override
    public void onDigito(String digito) {


        String texto =
                txtMonto.getText();


        if(texto.equals(MONTO_INICIAL)){

            txtMonto.setText(digito);

        }else{

            txtMonto.appendText(digito);

        }

    }



    @Override
    public void onBorrar() {


        String texto =
                txtMonto.getText();


        if(texto.equals(MONTO_INICIAL)){

            return;

        }


        texto =
                texto.substring(
                        0,
                        texto.length()-1
                );


        if(texto.isEmpty()){

            txtMonto.setText(
                    MONTO_INICIAL
            );

        }else{

            txtMonto.setText(texto);

        }

    }




    @Override
    public void onEntrar() {


        String texto =
                txtMonto.getText();


        if(texto.equals(MONTO_INICIAL)
                || texto.isBlank()){


            txtMesajefinal.setText(
                    "Ingrese un monto."
            );

            return;

        }



        int monto =
                Integer.parseInt(texto);




        if(monto <= 0){


            txtMesajefinal.setText(
                    "Monto inválido."
            );

            return;

        }


        if(monto % 10 != 0){


            txtMesajefinal.setText(
                    "El cajero solo entrega billetes."
            );

            return;

        }



        if(monto > cuenta.getSaldo()){


            txtMesajefinal.setText(
                    "Saldo insuficiente."
            );

            return;

        }




        Transaccion transaccion =
                Cajero.getInstancia()
                        .retirar(monto);




        if(transaccion != null){


            abrirVoucher(transaccion);


        }else{


            txtMesajefinal.setText(
                    "No fue posible realizar el retiro."
            );


        }



        txtMonto.setText(
                MONTO_INICIAL
        );


    }



    private void abrirVoucher(
            Transaccion transaccion
    ){


        try{


            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/Voucher.fxml"
                                    )
                    );


            Parent root =
                    loader.load();



            VoucherController controller =
                    loader.getController();



            controller.cargarVoucher(
                    transaccion
            );



            Stage stage =
                    (Stage)
                            txtMonto
                                    .getScene()
                                    .getWindow();



            stage.setScene(
                    new Scene(root)
            );


            stage.show();



        }catch(Exception e){


            e.printStackTrace();

        }


    }




    @FXML
    public void irMenu(){


        cambiarPantalla(
                "/Menu.fxml"
        );


    }


    private void cambiarPantalla(
            String ruta
    ){


        try{


            Parent root =
                    FXMLLoader.load(
                            getClass()
                                    .getResource(
                                            ruta
                                    )
                    );



            Stage stage =
                    (Stage)
                            txtMonto
                                    .getScene()
                                    .getWindow();



            stage.setScene(
                    new Scene(root)
            );


            stage.show();



        }catch(Exception e){


            e.printStackTrace();

        }

    }


}