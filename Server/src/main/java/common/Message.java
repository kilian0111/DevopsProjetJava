package main.java.common;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_MESSAGE")
public class Message implements Serializable {

    private Long id;
    private String Content;
    private Date dateMessage;
    private Boolean visible;
    private UserSafeData utilisateurSender;
    private Long conversationId;

    @Serial
    private static final long serialVersionUID =  1350092881346723532L;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ME_ID",updatable = false, nullable = false)
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Column(name = "ME_CONTENT")
    public String getContent() {return Content;}
    public void setContent(String content) {Content = content;}

    @Column(name = "ME_DT")
    public Date getDateMessage() {return dateMessage;}
    public void setDateMessage(Date dateMessage) {this.dateMessage = dateMessage;}

    @Column(name = "ME_VISIBLE")
    public Boolean getVisible() {return visible;}
    public void setVisible(Boolean visible) {this.visible = visible;}

    @OneToOne
    @JoinColumn(name = "UT_ID", referencedColumnName = "UT_ID")
    public UserSafeData getUtilisateurSender() {return utilisateurSender;}
    public void setUtilisateurSender(UserSafeData utilisateurSender) {this.utilisateurSender = utilisateurSender;}

    @Column(name = "CO_ID")
    public Long getConversationId() {return conversationId;}
    public void setConversationId(Long conversationId) {this.conversationId = conversationId;}

}


