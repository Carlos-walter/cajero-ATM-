package application.model;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import io.github.cdimascio.dotenv.Dotenv;

public class MongoManager {
    private static MongoManager instancia;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private MongoManager() {
        try {
            Dotenv dotenv;
            dotenv = Dotenv.load();
            String uriAtlas = dotenv.get("Mongo_URI");
            ConnectionString connectionString = new ConnectionString(uriAtlas);
            this.mongoClient = MongoClients.create(connectionString);
            this.database = mongoClient.getDatabase("atm_db");
            System.out.println("MongoManager inicializado con éxito apuntando a MongoDB Atlas...");
        } catch (Exception e) {
            System.err.println("Error crítico al inicializar la conexión con Atlas: " + e.getMessage());
        }
    }

    public static MongoManager getInstancia() {
        if (instancia == null || instancia.mongoClient == null) {
            instancia = new MongoManager();
        }
        return instancia;
    }

    public MongoCollection<Document> getColeccion(String nombre) {
        return database.getCollection(nombre);
    }

    public void cerrarConexion() {
        try {
            if (mongoClient != null) {
                this.mongoClient.close();
                this.database = null;
                this.mongoClient = null;
                System.out.println("Se cerro la conexion con MongoDB");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}