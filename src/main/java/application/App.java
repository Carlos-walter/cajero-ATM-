package application;

import application.model.Cajero;
import application.model.Cuenta;
import application.model.MongoManager;
import application.model.Usuario;
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

        // =====================================================
        // DATOS TEMPORALES
        // Se eliminarán cuando se conecte SQL Server.
        // =====================================================

        Usuario u1 = new Usuario(
                "Ivan",
                "1234",
                "12345678"
        );

        Cuenta c1 = new Cuenta(
                "1111111111111111111111",
                "Ahorros",
                5000,
                u1
        );

        Cuenta c2 = new Cuenta(
                "2222222222222222222222",
                "Corriente",
                12000,
                u1
        );

        Cuenta c3 = new Cuenta(
                "3333333333333333333333",
                "CTS",
                3000,
                u1
        );

        u1.agregarCuenta(c1);
        u1.agregarCuenta(c2);
        u1.agregarCuenta(c3);

        // =====================================================

        Usuario u2 = new Usuario(
                "Carlos",
                "4321",
                "87654321"
        );

        Cuenta c4 = new Cuenta(
                "4444444444444444444444",
                "Ahorros",
                8000,
                u2
        );

        Cuenta c5 = new Cuenta(
                "5555555555555555555555",
                "Corriente",
                15000,
                u2
        );

        u2.agregarCuenta(c4);
        u2.agregarCuenta(c5);

        // =====================================================

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(u1);
        usuarios.add(u2);

        /*
        =====================================================
        FUTURO (SQL SERVER)

        List<Usuario> usuarios =
                UsuarioDAO.obtenerUsuarios();

        =====================================================
        */

        Cajero.inicializar(usuarios);

        Parent root = FXMLLoader.load(
                getClass().getResource("/LoginDNI.fxml")
        );

        primaryStage.setTitle("Cajero Automático");

        primaryStage.setScene(new Scene(root));

        primaryStage.setMaximized(true);
        // primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    @Override
    public void stop() {

        try {

            System.out.println("Apagando el sistema del cajero...");

            MongoManager.getInstancia().cerrarConexion();

        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}