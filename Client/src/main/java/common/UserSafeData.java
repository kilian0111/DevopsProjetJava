package main.java.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


public class UserSafeData implements Serializable {

    private Long id;
    private String pseudo;
    private String mail;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private Integer sexe;
    private Boolean actif;

    @Serial
    private static final long serialVersionUID =  1350092881346723535L;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getPseudo() {return pseudo;}
    public void setPseudo(String pseudo) {this.pseudo = pseudo;}

    public String getMail() {return mail;}
    public void setMail(String mail) {this.mail = mail;}

    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public Date getDateNaissance() {return dateNaissance;}
    public void setDateNaissance(Date dateNaissance) {this.dateNaissance = dateNaissance;}

    public Integer getSexe() {return sexe;}
    public void setSexe(Integer sexe) {this.sexe = sexe;}

    public Boolean getActif() {return actif;}
    public void setActif(Boolean actif) {this.actif = actif;}
}
