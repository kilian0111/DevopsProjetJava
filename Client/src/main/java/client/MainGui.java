package main.java.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.client.gui.controlleur.ApplicationController;
import main.java.client.gui.controlleur.Icontrolleur;
import main.java.common.Utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class MainGui extends Application {

    private Stage primaryStage;
    private Client client;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        Properties props = new Properties();
        try(FileInputStream conf = new FileInputStream(Utils.getResourcesPath()+"conf.properties")) {
            props.load(conf);
            int port = Integer.parseInt(props.getProperty("server.port"));
            String ip = props.getProperty("server.ip");

            this.client = new Client(port, ip,this);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.initStage(this.primaryStage,"connection.fxml");
    }

    public static void main(String[] args)  {
        Application.launch(MainGui.class,args);
    }

    public void changeScene(String fxml) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(
                    new File(Utils.getResourcesPath()+"fxml/"+fxml).toURI().toURL()
            );
            Scene scene = new Scene(fxmlLoader.load());
            Icontrolleur controlleur = fxmlLoader.getController();
            controlleur.setClient(client);
            if(controlleur instanceof ApplicationController){
               ApplicationController app =  (ApplicationController) controlleur;
               this.client.setApplicationController(app);
               app.chargerData();
            }
            primaryStage.setScene(scene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void erreurPopUp(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setResizable(false);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:"+Utils.getResourcesPath()+"img/favicon.png"));
        alert.showAndWait();
    }

    public void showSecondNewStage(String fxml){
            Stage stage = new Stage();
            this.initStage(stage, fxml);
    }

    private void initStage(Stage stage ,String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(Utils.getResourcesPath() + "fxml/" + fxml).toURI().toURL());
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            Icontrolleur controlleur = fxmlLoader.getController();
            controlleur.setClient(client);
            stage.setTitle("Kijoki");
            stage.getIcons().add(new Image("file:" + Utils.getResourcesPath() + "img/favicon.png"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }  catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        //arrete les threads
        System.exit(0);
    }

}

