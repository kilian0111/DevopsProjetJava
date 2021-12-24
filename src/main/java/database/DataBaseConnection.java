package main.java.database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public  class DataBaseConnection {

    private static DataBaseConnection dataBaseConnection;
    private Connection connection;


    public DataBaseConnection() {

        Properties props = new Properties();

        try(FileInputStream conf = new FileInputStream("src/main/resources/conf.properties")){
            props.load(conf);
            Class.forName(props.getProperty("jdbc.driver.class"));

        } catch (Exception e){
            e.printStackTrace();
        }

        String url = props.getProperty("jdbc.url");
        String login = props.getProperty("jdbc.login");
        String password = props.getProperty("jdbc.password");

        try{
        this.connection = DriverManager.getConnection(url,login,password);
        }catch(Exception e){
            e.printStackTrace();
            this.connection = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DataBaseConnection getDataBaseConnection() {
        if(dataBaseConnection == null){
            dataBaseConnection = new DataBaseConnection();
        }
        return dataBaseConnection;
    }
}
