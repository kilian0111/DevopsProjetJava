package main.java.common.repository;

import main.java.common.User;
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
    public static void getUserById() {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT e FROM User e", User.class);
        List<User> resultList = query.getResultList();

    }
    public static void saveUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

    }
}


