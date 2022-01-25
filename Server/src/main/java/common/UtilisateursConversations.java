package main.java.common;



import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "T_UTILISATEUR_CONVERSATION")
public class UtilisateursConversations implements Serializable{

        private PkComposer pkComposer;
        private Boolean mute;
        private Date dateDerniereLecture;

    public UtilisateursConversations() {
        super();
    }

    public UtilisateursConversations(PkComposer pkComposer) {
        this.pkComposer = pkComposer;
    }

    public UtilisateursConversations(Boolean mute) {
        this.mute = mute;
    }

    public UtilisateursConversations(Date dateDerniereLecture) {
        this.dateDerniereLecture = dateDerniereLecture;
    }

    public UtilisateursConversations(PkComposer pkComposer, Boolean mute, Date dateDerniereLecture) {
        this.pkComposer = pkComposer;
        this.mute = mute;
        this.dateDerniereLecture = dateDerniereLecture;
    }

    @Serial
        private  static  final  long serialVersionUID =  1352192881346723535L;

    @EmbeddedId
    public PkComposer getPkComposer() {return pkComposer;}
    public void setPkComposer(PkComposer pkComposer) {this.pkComposer = pkComposer;}

    @Column(name = "UC_MUTE")
    public Boolean getMute() {return mute;}
    public void setMute(Boolean mute) {this.mute = mute;}

    @Column(name = "UC_DT_LU")
    public Date getDateDerniereLecture() {return dateDerniereLecture;}
    public void setDateDerniereLecture(Date dateDerniereLecture) {this.dateDerniereLecture = dateDerniereLecture;}

    @Embeddable
    public class PkComposer implements Serializable {
        protected Long utilisateurId;
        protected Conversations conversations;

        public PkComposer() {
            super();
        }



        public PkComposer(Long utilisateurId, Conversations conversations) {
            super();
            this.utilisateurId = utilisateurId;
            this.conversations = conversations;
        }

        @Column(name = "UT_ID")
        public Long getUtilisateurId() {
            return utilisateurId;
        }

        public void setUtilisateurId(Long utilisateurId) {
            this.utilisateurId = utilisateurId;
        }
        @OneToOne
        @JoinColumn(name="CO_ID", referencedColumnName="CO_ID")
        public Conversations getConversations() {
            return conversations;
        }

        public void setConversations(Conversations conversations) {
            this.conversations = conversations;
        }
    }
}
