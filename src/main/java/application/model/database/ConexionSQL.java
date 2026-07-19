package application.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQL {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=CAJERO_AUTOMATICO;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";

    private static final String USUARIO = "sa";
    private static final String PASSWORD = "123456";

    public static Connection obtenerConexion() {

        try {

            Connection conexion =
                    DriverManager.getConnection(
                            URL,
                            USUARIO,
                            PASSWORD
                    );

            System.out.println("Conectado correctamente a SQL Server.");

            return conexion;

        } catch (SQLException e) {

            System.out.println("Error al conectar con SQL Server.");

            e.printStackTrace();

            return null;

        }

    }

}