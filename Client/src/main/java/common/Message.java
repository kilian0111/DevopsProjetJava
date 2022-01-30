package main.java.common;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * Contient les informations d'un message
 * Envoyeur, contenu
 */
public class Message implements Serializable {

    private Long id;
    private String Content;
    private Date dateMessage;
    private Boolean visible;
    private UserSafeData utilisateurSender;
    private Long conversationId;

    @Serial
    private static final long serialVersionUID =  1350092881346723532L;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getContent() {return Content;}
    public void setContent(String content) {Content = content;}

    public Date getDateMessage() {return dateMessage;}
    public void setDateMessage(Date dateMessage) {this.dateMessage = dateMessage;}

    public Boolean getVisible() {return visible;}
    public void setVisible(Boolean visible) {this.visible = visible;}

    public UserSafeData getUtilisateurSender() {return utilisateurSender;}
    public void setUtilisateurSender(UserSafeData utilisateurSender) {this.utilisateurSender = utilisateurSender;}

    public Long getConversationId() {return conversationId;}
    public void setConversationId(Long conversationId) {this.conversationId = conversationId;}

}
