package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Controlador del Paso 3 de transferencia.
 *
 * Funciones:
 * - Capturar el monto mediante teclado táctil.
 * - Validar que exista un monto ingresado.
 * - Continuar hacia el voucher de transferencia.
 */
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

    /**
     * Guarda el monto digitado.
     */
    private String monto = "";

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




     //Agrega números al monto.

    @Override
    public void onDigito(String digito){


        if(monto.length() < 8){

            monto += digito;

            actualizarMonto();

        }

    }



    /**
     * Elimina el último número ingresado.
     */
    @Override
    public void onBorrar(){


        if(!monto.isEmpty()){


            monto =
                    monto.substring(
                            0,
                            monto.length() - 1
                    );


            actualizarMonto();

        }

    }



    /**
     * Botón CONFIRMAR del teclado.
     *
     * Abre el voucher.
     */
    @Override
    public void onEntrar(){
        irVoucher();

    }





    /**
     * Actualiza el TextField mostrando
     * el monto actual.
     */
    private void actualizarMonto(){


        if(monto.isEmpty()){


            txtMonto.setText(
                    "S/ 0.00"
            );


        }else{


            txtMonto.setText(
                    "S/ "
                            + monto
                            + ".00"
            );


        }


    }


    /**
     * Regresa al Paso 2.
     */
    @FXML
    public void volverPaso2(){


        cambiarPantalla(
                "/TransferenciaPaso2.fxml"
        );


    }



    /**
     * Valida el monto y abre el voucher.
     */
    private void irVoucher(){



        if(monto.isEmpty()){


            System.out.println(
                    "Ingrese un monto"
            );


            return;

        }



        double cantidad =
                Double.parseDouble(monto);



        System.out.println(
                "Monto confirmado: S/ "
                        + cantidad
        );





        cambiarPantalla(
                "/Voucher.fxml"
        );


    }




    /**
     * Método reutilizable para cambiar escenas.
     *
     * @param ruta ruta del archivo FXML
     */
    private void cambiarPantalla(String ruta){


        try{


            Parent root =
                    FXMLLoader.load(
                            getClass()
                                    .getResource(ruta)
                    );



            Stage stage =
                    (Stage)
                            btnRegresar
                                    .getScene()
                                    .getWindow();



            stage.setScene(
                    new Scene(root)
            );


            stage.show();



        }catch(Exception e){


            System.out.println(
                    "Error al abrir: "
                            + ruta
            );


            e.printStackTrace();


        }


    }

}