package main.java.database;

import main.java.common.Utils;
import main.java.common.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DataBaseConnectionRequest {

    private final static DataBaseConnection dataBaseConnection = DataBaseConnection.getDataBaseConnection();

    public User seConnecter(String MailOuPseudo, String mdp){

       ArrayList<String> getdata = new ArrayList<String>();
        PreparedStatement stmt = null;
        try {
            String requeteSql = "select UT_ID,UT_PSEUDO,UT_MAIL,UT_PRENOM,UT_NOM,UT_DT_NAISSANCE,UT_SEXE,UT_MDP,UT_SALT,UT_ACTIF FROM T_UTILISATEUR WHERE (UT_PSEUDO = ? OR UT_MAIL = ?) AND UT_ACTIF = true ";

            PreparedStatement preparedStatement = dataBaseConnection.getConnection().prepareStatement(requeteSql);
            preparedStatement.setString(1, MailOuPseudo.toString());
            preparedStatement.setString(2, MailOuPseudo.toString());
            ResultSet resultat = preparedStatement.executeQuery();

            User userTryConnect = new User();
            resultat.next();

            userTryConnect.setId(resultat.getLong("UT_ID"));
            userTryConnect.setPseudo( resultat.getString("UT_PSEUDO"));
            userTryConnect.setMail( resultat.getString("UT_MAIL"));
            userTryConnect.setPrenom( resultat.getString("UT_PRENOM"));
            userTryConnect.setNom( resultat.getString("UT_NOM"));
            userTryConnect.setDateNaissance( resultat.getDate("UT_DT_NAISSANCE"));
            userTryConnect.setSexe( resultat.getInt("UT_SEXE"));
            userTryConnect.setMdp( resultat.getString("UT_MDP"));
            userTryConnect.setSalt( resultat.getString("UT_SALT"));
            userTryConnect.setActif( resultat.getBoolean("UT_ACTIF"));

            resultat.close();
            preparedStatement.close();
            if(Utils.convertMdpWithSalt(mdp, userTryConnect.getSalt()).equals(userTryConnect.getMdp())){
                return userTryConnect;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return new User();

    }


}
