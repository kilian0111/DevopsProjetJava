package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.client.Client;
import main.java.common.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Icontrolleur, Initializable {



    private Client client;

    private GameChifoumi game;

    private Stage stage;


    //Ces rounds
    @FXML //fx:id="yourRound1"
    private ImageView yourRound1;
    @FXML //fx:id="yourRound2"
    private ImageView yourRound2;
    @FXML //fx:id="yourRound3"
    private ImageView yourRound3;

    //Tout ceux qui concerne Moi
    @FXML //fx:id="imgPierreMoi"
    private ImageView imgPierreMoi;
    @FXML
    private Button myPierreButton;
    @FXML //fx:id="imgFeuilleMoi"
    private ImageView imgFeuilleMoi;
    @FXML
    private Button myFeuilleButton;
    @FXML //fx:id="imgCiseauxMoi"
    private ImageView imgCiseauxMoi;
    @FXML
    private Button myCiseauxButton;
    //Mes rounds
    @FXML //fx:id="myRound1"
    private ImageView myRound1;
    @FXML //fx:id="myRound2"
    private ImageView myRound2;
    @FXML //fx:id="myRound3"
    private ImageView myRound3;

    //La table au milieu
    @FXML //fx:id="imgJouerParMoi"
    private ImageView imgJouerParMoi;
    @FXML //fx:id="imgJouerParAmi"
    private ImageView imgJouerParAmi;

    @FXML
    private Label scoreJ2;

    @FXML
    private Label scoreCurrentUser;

    private Image imageFeuille;
    private Image imageCisceaux;
    private Image imagePierre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            imageFeuille = new Image(String.valueOf(new File(Utils.getResourcesPath()+"img/feuille.png").toURI().toURL()));
            imageCisceaux = new Image(String.valueOf(new File(Utils.getResourcesPath()+"img/ciseaux.png").toURI().toURL()));
            imagePierre = new Image(String.valueOf(new File(Utils.getResourcesPath()+"img/ciseaux.png").toURI().toURL()));
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Client getClient() {
        return this.client;
    }
    @Override
    public void setClient(Client client) { this.client = client;}

    public GameChifoumi getGame() {return game;}
    public void setGame(GameChifoumi game) {this.game = game;}

    public Stage getStage() { return stage;}
    public void setStage(Stage stage) {  this.stage = stage;}

    public void showResult(){
        if(!this.game.getLesManches().isEmpty()){
            GameMancheChifoumi gameMancheChifoumi = this.game.getLesManches().get(this.game.getLesManches().size() -1);
            Image imgJ1 = this.selectImageByAction(gameMancheChifoumi.getChoixJ1());
            Image imgJ2 =  this.selectImageByAction(gameMancheChifoumi.getChoixJ2());
            if(this.client.getUser().getId().equals(this.game.getIdUtilisateurJ1().getId())){
                this.imgJouerParMoi.setImage(imgJ1);
                this.yourRound3.setImage(imgJ2);
                this.imgJouerParAmi.setImage(imgJ2);
                this.scoreCurrentUser.setText(this.game.getScoreJ1().toString());
                this.scoreJ2.setText(this.game.getScoreJ2().toString());
            }else{
                this.imgJouerParMoi.setImage(imgJ2);
                this.yourRound3.setImage(imgJ1);
                this.imgJouerParAmi.setImage(imgJ1);
                this.scoreCurrentUser.setText(this.game.getScoreJ2().toString());
                this.scoreJ2.setText(this.game.getScoreJ1().toString());
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.imgJouerParMoi.setImage(null);
            this.yourRound3.setImage(null);
            this.imgJouerParAmi.setImage(null);
            this.myPierreButton.setDisable(false);
            this.myFeuilleButton.setDisable(false);
            this.myCiseauxButton.setDisable(false);
        }


    }


    public void fermerStageAction(ActionEvent actionEvent) {
        this.client.removeGame(game.getId());
        this.stage.close();
    }

    //3
    public void cisceauxAction(ActionEvent actionEvent) {
        try{
            this.action(3);
            this.myRound1.setImage(imageCisceaux);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //1
    public void feuilleAction(ActionEvent actionEvent) {
        try {
            this.action(1);
            this.myRound1.setImage(imageFeuille);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //2
    public void pierreAction(ActionEvent actionEvent) {
        try {
            this.action(2);
            this.myRound1.setImage(imagePierre);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void action(Integer button){
        this.myPierreButton.setDisable(true);
        this.myFeuilleButton.setDisable(true);
        this.myCiseauxButton.setDisable(true);
        ChoixChifoumi choixChifoumi = new ChoixChifoumi(button,this.client.getUser().getId(),this.game.getId());
        this.client.sendToServer(new ObjectSend(choixChifoumi,Action.CHOIX_JEUX));
    }

    private Image selectImageByAction(Integer choix){
        try{
            if(choix == 1){
                return imageFeuille;
            }else if(choix == 2){
                return imagePierre;
            }else{
                return imageCisceaux;
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

}
