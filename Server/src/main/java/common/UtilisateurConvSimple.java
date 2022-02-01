package main.java.common;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_UTILISATEUR_CONVERSATION")
public class UtilisateurConvSimple implements Serializable {

    /**
     * Contient les données d'une conversation liées à un utilisateur
     * Date de dernière lecture
     */

    private UtilisateurConvIdSimple id;
    private Date mute;
    private Date dateDerniereLecture;

    @Serial
    private static final long serialVersionUID =  1550892881346723536L;

    @EmbeddedId
    public UtilisateurConvIdSimple getId() {return id;}
    public void setId(UtilisateurConvIdSimple id) {this.id = id;}

    @Column(name = "UC_MUTE")
    public Date getMute() {return mute;}
    public void setMute(Date mute) {this.mute = mute;}

    @Column(name = "UC_DT_LU")
    public Date getDateDerniereLecture() {return dateDerniereLecture;}
    public void setDateDerniereLecture(Date dateDerniereLecture) {this.dateDerniereLecture = dateDerniereLecture;}


}
