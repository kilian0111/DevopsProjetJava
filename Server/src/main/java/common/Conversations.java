package main.java.common;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Comporte toutes les informations liées à une conversation
 * Utilisateurs, Messages, nom, date de création
 */
@Entity
@Table(name = "T_CONVERSATION")
public class Conversations implements Serializable {

    private Long conversationId;
    private String conversationNom;
    private Date dateCreationConv;
    private Long utilisateurCreateurID;
    private List<Message> lesMessages;
    private List<UserSafeData> lesUsers;

    @Serial
    private static final long serialVersionUID =  1350092881346723531L;

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

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="CO_ID", referencedColumnName="CO_ID")
    @OrderBy("ME_ID")
    public List<Message> getLesMessages() {return lesMessages;}
    public void setLesMessages(List<Message> lesMessages) {this.lesMessages = lesMessages;}

    @JoinTable(name = "T_UTILISATEUR_CONVERSATION",
            joinColumns = @JoinColumn(name = "CO_ID"),
            inverseJoinColumns = @JoinColumn(name = "UT_ID"))
    @MapKeyJoinColumn(name = "CO_ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    public List<UserSafeData> getLesUsers() {return lesUsers;}
    public void setLesUsers(List<UserSafeData> lesUsers) {this.lesUsers = lesUsers;}

    public void addMessage(Message message){
        this.lesMessages.add(message);
    }

    public void removeMessage(Message message){
        this.lesMessages.remove(message);
    }
}
