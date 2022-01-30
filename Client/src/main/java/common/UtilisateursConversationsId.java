package main.java.common;


import java.io.Serial;
import java.io.Serializable;

/**
 * Retourne les conversations d'un utilisateur
 */
public class UtilisateursConversationsId implements Serializable {

    private UserSafeData utilisateur;
    private Conversations conversations;

    @Serial
    private static final long serialVersionUID =  1350092881346723537L;

    public UserSafeData getUtilisateur() {return utilisateur;}
    public void setUtilisateur(UserSafeData utilisateur) {this.utilisateur = utilisateur;}

    public Conversations getConversations() {return conversations;}
    public void setConversations(Conversations conversations) {this.conversations = conversations;}

}
