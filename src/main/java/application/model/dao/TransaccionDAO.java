package application.model.dao;

import application.model.entity.EntidadCuenta;
import application.model.entity.EntidadTransaccion;
import application.model.utils.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class TransaccionDAO {


    public boolean guardar(EntidadTransaccion transaccion) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            em.persist(transaccion);

            em.getTransaction().commit();

            return true;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

            return false;

        } finally {

            em.close();

        }
    }


    public List<EntidadTransaccion> listarPorCuenta(EntidadCuenta cuenta) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return em.createQuery(
                            "SELECT t FROM EntidadTransaccion t " +
                                    "WHERE t.cuentaOrigen = :cuenta " +
                                    "OR t.cuentaDestino = :cuenta " +
                                    "ORDER BY t.fecha DESC",
                            EntidadTransaccion.class)

                    .setParameter("cuenta", cuenta)
                    .getResultList();

        } finally {

            em.close();

        }
    }
}