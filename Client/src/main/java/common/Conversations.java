package main.java.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Conversations implements Serializable {

    private Long conversationId;
    private String conversationNom;
    private Date dateCreationConv;
    private Long utilisateurCreateurID;
    private List<Message> lesMessages;
    private List<UserSafeData> lesUsers;

    @Serial
    private static final long serialVersionUID =  1350092881346723531L;

    public Long getConversationId() {return conversationId;}
    public void setConversationId(Long conversationId) {this.conversationId = conversationId;}

    public String getConversationNom() {return conversationNom;}
    public void setConversationNom(String conversationNom) {this.conversationNom = conversationNom;}

    public Date getDateCreationConv() {return dateCreationConv;}
    public void setDateCreationConv(Date dateCreationConv) {this.dateCreationConv = dateCreationConv;}

    public Long getUtilisateurCreateurID() {return utilisateurCreateurID;}
    public void setUtilisateurCreateurID(Long utilisateurCreateurID) {this.utilisateurCreateurID = utilisateurCreateurID;}

    public List<Message> getLesMessages() {return lesMessages;}
    public void setLesMessages(List<Message> lesMessages) {this.lesMessages = lesMessages;}

    public List<UserSafeData> getLesUsers() {return lesUsers;}
    public void setLesUsers(List<UserSafeData> lesUsers) {this.lesUsers = lesUsers;}

    public void addMessage(Message message){
        this.lesMessages.add(message);
    }

    public void removeMessage(Message message){
        this.lesMessages.remove(message);
    }
}
