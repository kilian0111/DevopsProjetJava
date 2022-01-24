package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.java.client.Client;
import main.java.common.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Icontrolleur, Initializable {

    private Client client;

    //Tout ceux qui concerne l'Ami
    @FXML //fx:id="imgPierreAmi"
    private ImageView imgPierreAmi;
    @FXML //fx:id="imgFeuilleAmi"
    private ImageView imgFeuilleAmi;
    @FXML //fx:id="imgCiseauxAmi"
    private ImageView imgCiseauxAmi;
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
    private Button myPierreButton;
    @FXML //fx:id="imgFeuilleMoi"
    private ImageView imgFeuilleMoi;
    private Button myFeuilleButton;
    @FXML //fx:id="imgCiseauxMoi"
    private ImageView imgCiseauxMoi;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public Client getClient() {
        return null;
    }

    @Override
    public void setClient(Client client) {

    }

    @FXML
    public void JouerAction(ActionEvent e) throws IOException {

        User user = this.client.getUser();

        //Si le user choisit la pierre
        if(myPierreButton.isPressed())
        {
            imgPierreMoi.setVisible(false);
            imgJouerParMoi.setImage(url='@../../../../ressources/img/pierre.png');
        }
        //Si le user choisit la feuille
        if(myPierreButton.isPressed())
        {
            imgFeuilleAmi.setVisible(false);

        }
        //Si le user choisit le ciseaux
        if(myPierreButton.isPressed())
        {
            imgCiseauxMoi.setVisible(false);

        }

}
