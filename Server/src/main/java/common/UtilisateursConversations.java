package main.java.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "T_UTILISATEUR_CONVERSATION")
public class UtilisateursConversations implements Serializable{

    private UtilisateursConversationsId id;
    private Date mute;
    private Date dateDerniereLecture;

    @Serial
    private static final long serialVersionUID =  1350092881346723536L;

    @EmbeddedId
    public UtilisateursConversationsId getId() {return id;}
    public void setId(UtilisateursConversationsId id) {this.id = id;}

    @Column(name = "UC_MUTE")
    public Date getMute() {return mute;}
    public void setMute(Date mute) {this.mute = mute;}

    @Column(name = "UC_DT_LU")
    public Date getDateDerniereLecture() {return dateDerniereLecture;}
    public void setDateDerniereLecture(Date dateDerniereLecture) {this.dateDerniereLecture = dateDerniereLecture;}

}
