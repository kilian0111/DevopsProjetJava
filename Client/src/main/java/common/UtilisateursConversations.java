package main.java.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Contient les données d'une conversation liées à un utilisateur
 * Date de dernière lecture
 */
public class UtilisateursConversations implements Serializable{

    private UtilisateursConversationsId id;
    private Date mute;
    private Date dateDerniereLecture;

    @Serial
    private static final long serialVersionUID =  1350092881346723536L;

    public UtilisateursConversationsId getId() {return id;}
    public void setId(UtilisateursConversationsId id) {this.id = id;}

    public Date getMute() {return mute;}
    public void setMute(Date mute) {this.mute = mute;}

    public Date getDateDerniereLecture() {return dateDerniereLecture;}
    public void setDateDerniereLecture(Date dateDerniereLecture) {this.dateDerniereLecture = dateDerniereLecture;}

    public String nomConv(){
        return this.id.getConversations().getConversationNom();
    }


}
