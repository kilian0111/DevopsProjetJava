package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.client.Client;
import main.java.client.MainGui;
import main.java.common.User;
import main.java.database.DataBaseConnectionRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionControlleur implements Initializable,Icontrolleur {

    private DataBaseConnectionRequest sqlRequest = new DataBaseConnectionRequest();
    private Client client;
    private MainGui mainGui;

    @FXML
    private TextField identifiant;
    @FXML
    private PasswordField mdp;
    @FXML
    private Label labelErreur;

    @FXML
    public void connectionAction(ActionEvent e){
        if(!identifiant.getText().isBlank() && !mdp.getText().isBlank()){
            labelErreur.setVisible(false);

            User user = new User();
            user.setPseudo(identifiant.getText());
            user.setMdp(mdp.getText());
            this.client.seConnecter(user);
            User userConnecter =  this.client.getUser();
            if(userConnecter.getId() != null){
                System.out.println("oui");
            }

        }else{
            labelErreur.setText("Erreur ! Veuillez remplir tout les champs");
            labelErreur.setVisible(true);

        }
        identifiant.setStyle(identifiant.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        mdp.setStyle(mdp.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
    }

    @FXML
    public void inscriptionAction(ActionEvent e) throws IOException {
        mainGui.changeScene("inscription.fxml");
    }

    @FXML
    public void forgotPassword(ActionEvent e){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public MainGui getMainGui() {return mainGui;}

    public void setMainGui(MainGui mainGui) {this.mainGui = mainGui;}
}
