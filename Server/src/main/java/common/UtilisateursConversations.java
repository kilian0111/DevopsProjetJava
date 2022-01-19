package main.java.common;



import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "T_UTILISATEUR")
public class UtilisateursConversations implements Serializable{

        private PkComposer pkComposer;
        private Long utilisateurId;
        private List<Conversations> conversations;
        private Boolean mute;
        private Date dateDerniereLecture;

        @Serial
        private  static  final  long serialVersionUID =  1352192881346723535L;

    @EmbeddedId
    public PkComposer getPkComposer() {return pkComposer;}
    public void setPkComposer(PkComposer pkComposer) {this.pkComposer = pkComposer;}

    @Column(name = "UT_ID")
    public Long getUtilisateurId() {return utilisateurId;}
    public void setUtilisateurId(Long utilisateurId) {this.utilisateurId = utilisateurId;}

    @ManyToMany
    @JoinColumn(name="CO_ID", referencedColumnName="CO_ID")
    public List<Conversations> getConversations() {return conversations;}
    public void setConversations(List<Conversations> conversations) {this.conversations = conversations;}

    @Column(name = "UC_MUTE")
    public Boolean getMute() {return mute;}
    public void setMute(Boolean mute) {this.mute = mute;}

    @Column(name = "UC_DT_LU")
    public Date getDateDerniereLecture() {return dateDerniereLecture;}
    public void setDateDerniereLecture(Date dateDerniereLecture) {this.dateDerniereLecture = dateDerniereLecture;}

    @Embeddable
    public class PkComposer implements Serializable {
        protected Long utilisateurId;
        protected Long conversationId;

        public PkComposer() {}

        public PkComposer(Long utilisateurId, Long conversationId) {
            this.utilisateurId = utilisateurId;
            this.conversationId = conversationId;
        }

        public Long getUtilisateurId() {
            return utilisateurId;
        }

        public void setUtilisateurId(Long utilisateurId) {
            this.utilisateurId = utilisateurId;
        }

        public Long getConversationId() {
            return conversationId;
        }

        public void setConversationId(Long conversationId) {
            this.conversationId = conversationId;
        }
    }
}
