package main.java.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_UTILISATEUR")
public class User implements Serializable {


    private Long id;
    private String pseudo;
    private String mail;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private Integer sexe;
    private String mdp;
    private String salt;
    private Boolean actif;

    @Serial
    private  static  final  long serialVersionUID =  1350092881346723535L;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
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

    @Column(name = "UT_MDP")
    public String getMdp() {return mdp;}
    public void setMdp(String mdp) {this.mdp = mdp;}

    @Column(name = "UT_SALT")
    public String getSalt() {return salt;}
    public void setSalt(String salt) {this.salt = salt;}

    @Column(name = "UT_ACTIF")
    public Boolean getActif() {return actif;}
    public void setActif(Boolean actif) {this.actif = actif;}
}
