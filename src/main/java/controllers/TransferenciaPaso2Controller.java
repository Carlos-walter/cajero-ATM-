package controllers;

import application.model.Cuenta;
import application.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class TransferenciaPaso2Controller {


    @FXML
    private Label lblTitular;

    @FXML
    private Label lblCuentaDestino;

    @FXML
    private Label lblBanco;

    @FXML
    private Label lblTipoCuenta;


    @FXML
    private Button btnCorregir;

    @FXML
    private Button btnConfirmar;



    private Cuenta cuentaOrigen;

    private Cuenta cuentaDestino;



    public void setDatos(
            Cuenta origen,
            Cuenta destino
    ){

        this.cuentaOrigen = origen;
        this.cuentaDestino = destino;


        if(cuentaDestino == null){

            System.out.println(
                    "ERROR: cuenta destino es null"
            );

            return;
        }



        Usuario titular =
                cuentaDestino.getPropietario();



        lblTitular.setText(
                titular != null
                        ? titular.getNombre()
                        : "SIN DATOS"
        );


        lblCuentaDestino.setText(
                cuentaDestino.getNumeroCuenta()
        );


        lblBanco.setText(
                "Banco Central"
        );


        lblTipoCuenta.setText(
                cuentaDestino.getTipo()
        );


    }





    @FXML
    public void continuarPaso3(){


        if(cuentaOrigen == null){


            System.out.println(
                    "ERROR: cuenta origen perdida en paso 2"
            );


            return;
        }



        if(cuentaDestino == null){


            System.out.println(
                    "ERROR: cuenta destino perdida en paso 2"
            );


            return;
        }



        abrirPaso3();

    }





    private void abrirPaso3(){


        try{


            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/TransferenciaPaso3.fxml"
                                    )
                    );



            Parent root =
                    loader.load();



            TransferenciaPaso3Controller controller =
                    loader.getController();



            controller.setDatos(
                    cuentaOrigen,
                    cuentaDestino
            );



            Stage stage =
                    (Stage)
                            btnConfirmar
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
    public void volverPaso1(){


        cambiarPantalla(
                "/Transferencia.fxml"
        );


    }





    private void cambiarPantalla(
            String ruta
    ){


        try{


            Parent root =
                    FXMLLoader.load(
                            getClass()
                                    .getResource(ruta)
                    );


            Stage stage =
                    (Stage)
                            btnConfirmar
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