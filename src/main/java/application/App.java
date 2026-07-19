package application;


import application.model.Cajero;
import application.model.MongoManager;
import application.model.Usuario;
import application.model.dao.UsuarioDAO;
import application.model.entity.EntidadUsuario;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;



public class App extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {



        UsuarioDAO usuarioDAO = new UsuarioDAO();



        List<Usuario> usuarios = new ArrayList<>();



        List<EntidadUsuario> entidades =
                usuarioDAO.obtenerTodos();




        for(EntidadUsuario entidad : entidades){


            Usuario usuario =
                    new Usuario(entidad);



            usuarios.add(usuario);

        }




        Cajero.inicializar(usuarios);





        Parent root =
                FXMLLoader.load(
                        getClass()
                                .getResource("/LoginDNI.fxml")
                );



        primaryStage.setTitle(
                "Cajero Automático"
        );



        primaryStage.setScene(
                new Scene(root)
        );



        primaryStage.setMaximized(true);



        primaryStage.show();

    }




    @Override
    public void stop(){


        try{


            MongoManager
                    .getInstancia()
                    .cerrarConexion();



        }catch(Exception e){


            System.err.println(
                    e.getMessage()
            );

        }


    }





    public static void main(String[] args){

        launch(args);

    }

}