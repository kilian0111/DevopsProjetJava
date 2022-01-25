package main.java.common;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


public class UtilisateursConversations implements Serializable{

        private PkComposer pkComposer;
        private Long utilisateurId;
        private Conversations conversations;
        private Boolean mute;
        private Date dateDerniereLecture;

        @Serial
        private  static  final  long serialVersionUID =  1352192881346723535L;


    public PkComposer getPkComposer() {return pkComposer;}
    public void setPkComposer(PkComposer pkComposer) {this.pkComposer = pkComposer;}


    public Long getUtilisateurId() {return utilisateurId;}
    public void setUtilisateurId(Long utilisateurId) {this.utilisateurId = utilisateurId;}


    public Conversations getConversations() {return conversations;}
    public void setConversations(Conversations conversations) {this.conversations = conversations;}


    public Boolean getMute() {return mute;}
    public void setMute(Boolean mute) {this.mute = mute;}


    public Date getDateDerniereLecture() {return dateDerniereLecture;}
    public void setDateDerniereLecture(Date dateDerniereLecture) {this.dateDerniereLecture = dateDerniereLecture;}

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
