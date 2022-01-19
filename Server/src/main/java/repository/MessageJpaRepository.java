package main.java.repository;

import main.java.common.Conversations;
import main.java.common.Message;
import main.java.common.User;
import main.java.common.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class MessageJpaRepository {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("punit");

    public static void deleteMessage(Message message) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(message);
        em.getTransaction().commit();
    }
    public static void updateMessage(Message message) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(message);
        em.getTransaction().commit();

    }
    public static List<Message> getMessageByConversationId(Long conversationId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Message> query = em.createQuery("SELECT e FROM Message e WHERE e.id = :id", Message.class);
        query.setParameter("id", conversationId);
        return query.getResultList();
    }

    public static void saveMessage(Message message) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(message);
        em.getTransaction().commit();
    }
}
