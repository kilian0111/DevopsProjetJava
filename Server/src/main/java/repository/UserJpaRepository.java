package main.java.repository;

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

    public static void deleteUserRecord(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }
    public static void updateUserRecord(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();

    }
    public static User getUserById(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT e FROM User e WHERE e.id = :id", User.class);
        query.setParameter("id", userId);
        return query.getSingleResult();
    }

    public static User seConnecter(String identifiant, String mdp) {
        try{
            EntityManager em = entityManagerFactory.createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT e FROM User e WHERE (e.pseudo = :identifiant OR e.mail = :identifiant) ", User.class);
            query.setParameter("identifiant", identifiant);
            User user =  query.getSingleResult();
            return Utils.convertMdpWithSalt(mdp, user.getSalt()).equals(user.getMdp()) ? user : null;
        }catch(Exception e){
            return new User();
        }
    }

    public static User getUserByEmail(String email){
        try{
            EntityManager em = entityManagerFactory.createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT e FROM User e WHERE (e.mail = :identifiant) ", User.class);
            query.setParameter("identifiant", email);
            User user =  query.getSingleResult();
            return user;
         }catch(Exception e){
             return new User();
         }
    }
    public static void saveUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }
}


