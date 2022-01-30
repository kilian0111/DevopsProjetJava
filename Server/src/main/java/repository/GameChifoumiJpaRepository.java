package main.java.repository;

import main.java.common.GameChifoumi;
import main.java.common.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class GameChifoumiJpaRepository {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("punit");


    public static void updateGame(GameChifoumi gameChifoumi) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(gameChifoumi);
        em.getTransaction().commit();
    }

    public static void saveGame(GameChifoumi gameChifoumi) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(gameChifoumi);
        em.getTransaction().commit();
    }
}
