package controllers;

import application.model.Cuenta;
import application.model.Usuario;
import application.model.entity.EntidadCuenta;
import application.model.dao.CuentaDAO;
import application.model.entity.EntidadUsuario;
import application.model.utils.Sesion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class TransferenciaController implements TecladoListener {


    @FXML
    private TextField txtCuentaDestino;


    @FXML
    private Label lblMensaje;


    @FXML
    private TecladoController tecladoController;


    @FXML
    private Button btnCancelar;



    private static final int MAX_DIGITOS = 15;


    private final CuentaDAO cuentaDAO = new CuentaDAO();



    @FXML
    public void initialize() {


        if (tecladoController != null) {

            tecladoController.setListener(this);

        }


        txtCuentaDestino.clear();

        actualizarContador();

    }




    private void actualizarContador() {


        int cantidad =
                txtCuentaDestino.getText().length();


        lblMensaje.setText(
                "Cuenta: "
                        + cantidad
                        + "/15 dígitos"
        );

    }




    @Override
    public void onDigito(String digito) {


        if(txtCuentaDestino.getText().length() < MAX_DIGITOS){


            txtCuentaDestino.appendText(digito);

            actualizarContador();

        }

    }




    @Override
    public void onBorrar() {


        String texto =
                txtCuentaDestino.getText();



        if(!texto.isEmpty()){


            txtCuentaDestino.setText(
                    texto.substring(
                            0,
                            texto.length()-1
                    )
            );


            actualizarContador();

        }

    }




    @FXML
    public void borrarTodo(){


        txtCuentaDestino.clear();

        actualizarContador();

    }






    @Override
    public void onEntrar(){


        String numeroCuenta =
                txtCuentaDestino.getText().trim();



        if(numeroCuenta.isEmpty()){


            lblMensaje.setText(
                    "Ingrese una cuenta."
            );

            return;

        }



        if(numeroCuenta.length() != 15){


            lblMensaje.setText(
                    "La cuenta debe tener 20 dígitos."
            );

            return;

        }



        // BUSCAR EN SQL
        EntidadCuenta entidadDestino =
                cuentaDAO.buscarPorNumero(numeroCuenta);



        if(entidadDestino == null){


            lblMensaje.setText(
                    "La cuenta no existe."
            );

            return;

        }




        // CONVERTIR ENTIDAD -> MODELO

        EntidadUsuario entidadUsuario =
                entidadDestino.getPropietario();


        Usuario usuarioDestino =
                new Usuario(
                        entidadUsuario.getNombre(),
                        entidadUsuario.getPin(),
                        entidadUsuario.getDni()
                );


        Cuenta cuentaDestino =
                new Cuenta(
                        entidadDestino.getNumero_Cuenta(),
                        entidadDestino.getTipo(),
                        entidadDestino.getSaldo(),
                        usuarioDestino
                );


        Cuenta cuentaOrigen =
                Sesion.getUsuarioActual()
                        .getCuentaSeleccionada();




        if(cuentaOrigen == null){


            lblMensaje.setText(
                    "No hay cuenta seleccionada."
            );

            return;

        }





        if(cuentaOrigen.getNumeroCuenta()
                .equals(cuentaDestino.getNumeroCuenta())){


            lblMensaje.setText(
                    "No puede transferir a su propia cuenta."
            );

            return;

        }



        abrirPaso2(
                cuentaOrigen,
                cuentaDestino
        );

    }







    private void abrirPaso2(
            Cuenta origen,
            Cuenta destino
    ){


        try{


            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/TransferenciaPaso2.fxml"
                                    )
                    );


            Parent root =
                    loader.load();



            TransferenciaPaso2Controller controller =
                    loader.getController();



            controller.setDatos(
                    origen,
                    destino
            );



            Stage stage =
                    (Stage)
                            btnCancelar
                                    .getScene()
                                    .getWindow();



            stage.setScene(
                    new Scene(root)
            );


            stage.show();



        }catch(Exception e){


            e.printStackTrace();


            lblMensaje.setText(
                    "Error al abrir transferencia."
            );

        }


    }







    @FXML
    public void volverMenu(){


        try{


            Parent root =
                    FXMLLoader.load(
                            getClass()
                                    .getResource(
                                            "/Menu.fxml"
                                    )
                    );


            Stage stage =
                    (Stage)
                            btnCancelar
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