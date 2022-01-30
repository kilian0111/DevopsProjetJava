package main.java.common;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serial;
import java.io.Serializable;

/**
 * Retourne les conversations d'un utilisateur
 */
@Embeddable
public class UtilisateursConversationsId implements Serializable {

    private UserSafeData utilisateur;
    private Conversations conversations;

    @Serial
    private static final long serialVersionUID =  1350092881346723537L;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="UT_ID", referencedColumnName="UT_ID")
    public UserSafeData getUtilisateur() {return utilisateur;}
    public void setUtilisateur(UserSafeData utilisateur) {this.utilisateur = utilisateur;}

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="CO_ID", referencedColumnName="CO_ID")
    public Conversations getConversations() {return conversations;}
    public void setConversations(Conversations conversations) {this.conversations = conversations;}

}
