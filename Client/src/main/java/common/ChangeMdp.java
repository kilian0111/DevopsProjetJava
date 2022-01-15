package main.java.common;

import java.io.Serializable;
import java.util.Date;

public class ChangeMdp implements Serializable {

    private Long idUser;
    private String codeMail;
    private String newMdp;
    private Integer nbEssais;
    private Date dateDemande;

    public ChangeMdp(String codeMail, String newMdp) {
        this.codeMail = codeMail;
        this.newMdp = newMdp;
    }

    public ChangeMdp() {
        this.codeMail = codeMail;
        this.newMdp = newMdp;
    }


    public String getCodeMail() {
        return codeMail;
    }

    public void setCodeMail(String codeMail) {
        this.codeMail = codeMail;
    }

    public String getNewMdp() {
        return newMdp;
    }

    public void setNewMdp(String newMdp) {
        this.newMdp = newMdp;
    }

    public Integer getNbEssais() {
        return nbEssais;
    }

    public void setNbEssais(Integer nbEssais) {
        this.nbEssais = nbEssais;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
