package application.model.dao;

import application.model.entity.EntidadUsuario;
import application.model.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class UsuarioDAO {


    public List<EntidadUsuario> obtenerTodos() {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return em.createQuery(
                    "SELECT u FROM EntidadUsuario u",
                    EntidadUsuario.class
            ).getResultList();


        } finally {

            em.close();

        }
    }



    public EntidadUsuario buscarPorDni(String dni) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return em.find(
                    EntidadUsuario.class,
                    dni
            );

        } finally {

            em.close();

        }
    }



    public EntidadUsuario autenticar(String dni, String pin) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            return em.createQuery(
                            "SELECT u FROM EntidadUsuario u WHERE u.dni=:dni AND u.pin=:pin",
                            EntidadUsuario.class)

                    .setParameter("dni", dni)
                    .setParameter("pin", pin)

                    .getSingleResult();


        } catch (NoResultException e) {

            return null;


        } finally {

            em.close();

        }

    }



    public void actualizar(EntidadUsuario usuario) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            em.merge(usuario);

            em.getTransaction().commit();


        } finally {

            em.close();

        }

    }

}