package main.java.repository;

import main.java.common.Message;
import main.java.common.UtilisateursConversations;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class UtilisateursConversationJpaRepository {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("punit");

    public static void deleteUtilisateursConversation(UtilisateursConversations utilisateursConversations) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(utilisateursConversations);
        em.getTransaction().commit();
    }
    public static void updateMessage(UtilisateursConversations utilisateursConversations) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(utilisateursConversations);
        em.getTransaction().commit();

    }
    public static List<UtilisateursConversations> getUtilisateurConversationByUserId(Long utilisateurId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<UtilisateursConversations> query = em.createQuery("SELECT e FROM UtilisateursConversations e WHERE e.pkComposer.utilisateurId = :id", UtilisateursConversations.class);
        query.setParameter("id", utilisateurId);
        return query.getResultList();
    }

    public static void saveUtilisateursConversations(UtilisateursConversations utilisateursConversations) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(utilisateursConversations);
        em.getTransaction().commit();
    }

}
