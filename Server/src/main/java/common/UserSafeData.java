package main.java.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_UTILISATEUR")
public class UserSafeData implements Serializable {

    private Long id;
    private String pseudo;
    private String mail;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private Integer sexe;

    @Serial
    private static final long serialVersionUID =  1350092881346723535L;

    @Id
    @Column(name = "UT_ID",updatable = false, nullable = false)
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Column(name = "UT_PSEUDO")
    public String getPseudo() {return pseudo;}
    public void setPseudo(String pseudo) {this.pseudo = pseudo;}

    @Column(name = "UT_MAIL")
    public String getMail() {return mail;}
    public void setMail(String mail) {this.mail = mail;}

    @Column(name = "UT_PRENOM")
    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}

    @Column(name = "UT_NOM")
    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    @Column(name = "UT_DT_NAISSANCE")
    public Date getDateNaissance() {return dateNaissance;}
    public void setDateNaissance(Date dateNaissance) {this.dateNaissance = dateNaissance;}

    @Column(name = "UT_SEXE")
    public Integer getSexe() {return sexe;}
    public void setSexe(Integer sexe) {this.sexe = sexe;}



}
