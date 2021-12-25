package main.java.user;

import java.util.Date;

public class User {

    private long id;
    private String pseudo;
    private String mail;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private int sexe;
    private String mdp;
    private String salt;
    private boolean actif;

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

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

    public int getSexe() {return sexe;}
    public void setSexe(int sexe) {this.sexe = sexe;}

    public String getMdp() {return mdp;}
    public void setMdp(String mdp) {this.mdp = mdp;}

    public String getSalt() {return salt;}
    public void setSalt(String salt) {this.salt = salt;}

    public boolean isActif() {return actif;}
    public void setActif(boolean actif) {this.actif = actif;}
}
