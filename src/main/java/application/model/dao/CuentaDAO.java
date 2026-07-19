package application.model.dao;

import application.model.entity.EntidadCuenta;
import application.model.utils.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class CuentaDAO {


    public EntidadCuenta buscarPorNumero(String numeroCuenta) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return em.find(
                    EntidadCuenta.class,
                    numeroCuenta
            );

        } finally {

            em.close();

        }

    }




    /**
     * Busca una cuenta y carga también el usuario propietario.
     * Sirve para mostrar el titular en transferencia.
     */
    public EntidadCuenta buscarCompletaPorNumero(String numeroCuenta) {

        EntityManager em = JPAUtil.getEntityManager();

        try {


            return em.createQuery(
                            "SELECT c FROM EntidadCuenta c " +
                                    "LEFT JOIN FETCH c.propietario " +
                                    "WHERE c.numero_Cuenta = :numero",
                            EntidadCuenta.class
                    )
                    .setParameter(
                            "numero",
                            numeroCuenta
                    )
                    .getSingleResult();



        } finally {

            em.close();

        }

    }





    public List<EntidadCuenta> listarPorUsuario(String dni) {

        EntityManager em = JPAUtil.getEntityManager();

        try {


            return em.createQuery(
                            "SELECT c FROM EntidadCuenta c " +
                                    "WHERE c.propietario.dni = :dni",
                            EntidadCuenta.class
                    )
                    .setParameter(
                            "dni",
                            dni
                    )
                    .getResultList();



        } finally {

            em.close();

        }

    }







    public void actualizar(EntidadCuenta cuenta) {


        EntityManager em = JPAUtil.getEntityManager();


        try {


            em.getTransaction().begin();


            em.merge(cuenta);


            em.getTransaction().commit();



        } catch(Exception e) {


            if(em.getTransaction().isActive()){

                em.getTransaction().rollback();

            }


            throw e;



        } finally {


            em.close();


        }


    }


}