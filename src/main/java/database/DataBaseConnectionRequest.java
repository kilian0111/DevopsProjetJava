package main.java.database;

import main.java.common.Message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBaseConnectionRequest {

    private final static DataBaseConnection dataBaseConnection = DataBaseConnection.getDataBaseConnection();

    public void seConnecter(String MailOuPseudo,String mdp){

       ArrayList<String> getdata = new ArrayList<String>();
        PreparedStatement stmt = null;
        try {
            String requeteSql = "select UT_ID,UT_PSEUDO,UT_MAIL,UT_PRENOM,UT_NOM,UT_DT_NAISSANCE,UT_SEXE,UT_MDP,UT_SALT,UT_ACTIF from T_UTILISATEUR where UT_PSEUDO = ?1 OR UT_MAIL = ?2";

            PreparedStatement preparedStatement = dataBaseConnection.getConnection().prepareStatement(requeteSql);
            preparedStatement.setString(1, MailOuPseudo);
            preparedStatement.setString(2, MailOuPseudo);
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.close();


        } catch (Exception e) {
            System.out.println(e);
        }


    }




    public String getHashSHA512(String StringToHash, String salt){
        /*String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(StringToHash.getBytes(StandardCharsets.UTF_8));
            generatedPassword = Hex.encodeHexString(bytes);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }*/
        return new String();//generatedPassword;
    }


}
