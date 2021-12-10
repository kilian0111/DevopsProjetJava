package database;

import common.Message;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBaseConnectionRequest {

    private final static DataBaseConnection dataBaseConnection = DataBaseConnection.getDataBaseConnection();

    public void getMessagePrivee(){
      /*  ArrayList<String> getdata = new ArrayList<String>();
        PreparedStatement stmt = null;
        try {
            String requeteSql = "select mobile, password from tbl_1 join tbl_2 on tbl_1.fk_id=2.Pk_ID where mobile=? and password=?";

            PreparedStatement preparedStatement = dataBaseConnection.getConnection().prepareStatement(requeteSql);

            preparedStatement.setString(1, "1");
            preparedStatement.setString(1, "2");

            ResultSet rs = preparedStatement.executeQuery(login);

            Statement stmts = (Statement) conn.createStatement();

            if (rs.next()) {
                System.out.println("Db inside RS");
                ResultSet data = stmts.executeQuery(data);

                while (data.next()) {  looping through the resultset

                    getdata.add(data.getString("name"));
                    getdata.add(data.getString("place"));
                    getdata.add(data.getString("age"));
                    getdata.add(data.getString("job"));
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return new Message();*/

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
