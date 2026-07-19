package application.model.database;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class HibernateUtil {


    private static final EntityManagerFactory FACTORY;


    static {

        try {

            FACTORY = Persistence
                    .createEntityManagerFactory(
                            "PersistenciaCAJERO_AUTOMATICO"
                    );


            System.out.println(
                    "Hibernate conectado correctamente a SQL Server"
            );


        } catch (Exception e) {


            System.out.println(
                    "Error iniciando Hibernate"
            );


            e.printStackTrace();


            throw new RuntimeException(e);

        }

    }



    public static EntityManager getEntityManager(){

        return FACTORY.createEntityManager();

    }

}