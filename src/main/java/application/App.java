package application;

import application.model.Cajero;
import application.model.Cuenta;
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

        // ==========================
        // CREAR USUARIOS DE PRUEBA
        // ==========================

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

        u1.agregarCuenta(c1);

        Usuario u2 = new Usuario(
                "Carlos",
                "4321",
                "87654321"
        );

        Cuenta c2 = new Cuenta(
                "2222222222222222222222",
                "Ahorros",
                8000,
                u2
        );

        u2.agregarCuenta(c2);

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(u1);
        usuarios.add(u2);

        // Inicializar el cajero
        Cajero.inicializar(usuarios);

        // Cargar Login.fxml
        Parent root = FXMLLoader.load(
                getClass().getResource("/Login.fxml")
        );

        primaryStage.setTitle("Cajero Automático - Interfaz");
        primaryStage.setScene(new Scene(root, 450, 350));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}