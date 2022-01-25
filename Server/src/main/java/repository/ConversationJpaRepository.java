package main.java.repository;

import main.java.common.Conversations;
import main.java.common.UtilisateursConversations;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConversationJpaRepository {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("punit");

    public static void deleteUtilisateursConversation(Conversations conversations) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(conversations);
        em.getTransaction().commit();
    }
    public static void updateMessage(Conversations conversations) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(conversations);
        em.getTransaction().commit();

    }

    public static void saveUtilisateursConversations(Conversations conversations) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(conversations);
        em.getTransaction().commit();
    }
}
