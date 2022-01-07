package main.java.common.repository;

import main.java.common.User;
import main.java.common.Utils;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class UserJpaRepository {


    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("punit");

    public static void deleteStudentRecord(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.remove(user);
    }
    public static void updateStudentRecord(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.merge(user);
    }
    public static void getUserById(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT e FROM User e WHERE e.id = :id", User.class);
        query.setParameter("id", userId);
        List<User> resultList = query.getResultList();
    }

    public static User seConnecter(String identifiant, String mdp) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT e FROM User e WHERE (e.pseudo = :identifiant OR e.mail = :identifiant) AND e.actif = true ", User.class);
        query.setParameter("identifiant", identifiant);
        User user =  query.getSingleResult();
        return Utils.convertMdpWithSalt(mdp, user.getSalt()).equals(user.getMdp()) ? user : null;
    }
    public static void saveUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }
}


