package main.java.client;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.client.gui.controlleur.ApplicationController;
import main.java.client.gui.controlleur.CreerConversationController;
import main.java.client.gui.controlleur.GameController;
import main.java.client.gui.controlleur.Icontrolleur;
import main.java.common.Action;
import main.java.common.GameChifoumi;
import main.java.common.ObjectSend;
import main.java.common.Utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;
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
            if(controlleur instanceof ApplicationController applicationController){
                this.client.setApplicationController(applicationController);
                applicationController.chargerData();
            }else if(controlleur instanceof GameController gameController){
                gameController.setGame(this.client.getLaGame());
                this.client.addGame(this.client.getLaGame().getId(),gameController);
            }else if(controlleur instanceof CreerConversationController creerConversationController){
                creerConversationController.chargerData(this.client.getLesUser());
            }
            primaryStage.setScene(scene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Alert erreurPopUp(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setResizable(false);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:"+Utils.getResourcesPath()+"img/favicon.png"));
        alert.showAndWait();
        return alert;
    }

    public void showSecondNewStage(String fxml){
            Stage stage = new Stage();
            this.initStage(stage, fxml);
    }

    public void  demandeJeux(GameChifoumi gameChifoumi){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("DEMANDE JEUX");
        alert.setHeaderText(null);
        alert.setContentText(gameChifoumi.getIdUtilisateurJ1().getPseudo() + " Veut jouer au chifoumi Accepter ?");
        alert.setResizable(false);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:"+Utils.getResourcesPath()+"img/favicon.png"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            gameChifoumi.setAccepter(true);
            this.client.sendToServer(new ObjectSend(gameChifoumi, Action.REPONSEJ2_JEUX));
            this.client.setLaGame(gameChifoumi);
            Platform.runLater(()->this.client.getMainGui().showSecondNewStage("game.fxml"));
        }else{
            gameChifoumi.setAccepter(false);
            this.client.sendToServer(new ObjectSend(gameChifoumi, Action.REPONSEJ2_JEUX));
        }
    }



    private void initStage(Stage stage ,String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(Utils.getResourcesPath() + "fxml/" + fxml).toURI().toURL());
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            Icontrolleur controlleur = fxmlLoader.getController();
            controlleur.setClient(client);
            if(controlleur instanceof ApplicationController applicationController){
                this.client.setApplicationController(applicationController);
                applicationController.chargerData();
            }else if(controlleur instanceof GameController gameController){
                gameController.setGame(this.client.getLaGame());
                gameController.setStage(stage);
                this.client.addGame(this.client.getLaGame().getId(),gameController);

            }
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

