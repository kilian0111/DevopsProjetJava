package main.java.common;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class UtilisateurConvIdSimple implements Serializable {


    /**
     * Retourne les conversations d'un utilisateur
     */
    private Long userId;
    private Long conversationsId;

    @Serial
    private static final long serialVersionUID =  1352262881346723537L;

    @Column(name="UT_ID")
    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}

    @Column(name="CO_ID")
    public Long getConversationsId() {return conversationsId;}
    public void setConversationsId(Long conversationsId) {this.conversationsId = conversationsId;}
}
