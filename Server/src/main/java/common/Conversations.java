package main.java.common;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_UTILISATEUR_CONVERSATION")
public class Conversations implements Serializable {

    private Long conversationId;
    private String conversationNom;
    private Date dateCreationConv;
    private Long utilisateurCreateurID;
    private List<Message> lesMessages;

    @Serial
    private  static  final  long serialVersionUID =  1350083881342723135L;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "CO_ID",updatable = false, nullable = false)
    public Long getConversationId() {return conversationId;}
    public void setConversationId(Long conversationId) {this.conversationId = conversationId;}

    @Column(name = "CO_NOM")
    public String getConversationNom() {return conversationNom;}
    public void setConversationNom(String conversationNom) {this.conversationNom = conversationNom;}

    @Column(name = "CO_DT_CREATION")
    public Date getDateCreationConv() {return dateCreationConv;}
    public void setDateCreationConv(Date dateCreationConv) {this.dateCreationConv = dateCreationConv;}

    @Column(name = "UT_ID")
    public Long getUtilisateurCreateurID() {return utilisateurCreateurID;}
    public void setUtilisateurCreateurID(Long utilisateurCreateurID) {this.utilisateurCreateurID = utilisateurCreateurID;}

    public List<Message> getLesMessages() {return lesMessages;}
    public void setLesMessages(List<Message> lesMessages) {this.lesMessages = lesMessages;}
}
