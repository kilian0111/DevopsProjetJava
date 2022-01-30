package main.java.repository;

import main.java.common.GameChifoumi;
import main.java.common.GameMancheChifoumi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GameMancheChifomiJpaRepository {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("punit");

    public static void updateMancheGame(GameMancheChifoumi GameMancheChifoumi) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(GameMancheChifoumi);
        em.getTransaction().commit();
    }

    public static void saveMancheGame(GameMancheChifoumi GameMancheChifoumi) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(GameMancheChifoumi);
        em.getTransaction().commit();
    }

}
