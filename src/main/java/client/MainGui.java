package main.java.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.client.gui.controlleur.Icontrolleur;
import main.java.common.Utils;

import java.io.File;
import java.io.IOException;

public class MainGui extends Application {

    private Stage primaryStage;
    private Client client;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        try{
            this.client = new Client(1111, "127.0.0.1",this);
        } catch(Exception ignored){}

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(new File(Utils.getResourcesPath()+"fxml/connection.fxml").toURI().toURL());

            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            Icontrolleur controlleur = fxmlLoader.getController();
            controlleur.setClient(client);
            controlleur.setMainGui(this);
            stage.setTitle("Kijoki");
            stage.getIcons().add(new Image("file:"+Utils.getResourcesPath()+"img/favicon.png"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
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
            primaryStage.setScene(scene);
            Icontrolleur controlleur = fxmlLoader.getController();
            controlleur.setClient(client);
            controlleur.setMainGui(this);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}

