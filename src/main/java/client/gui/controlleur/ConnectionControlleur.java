package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.database.DataBaseConnectionRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionControlleur implements Initializable {

    private DataBaseConnectionRequest sqlRequest = new DataBaseConnectionRequest();

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
           /* if(sqlRequest.seConnecter(identifiant.getText(),mdp.getText())){

            }*/

        }else{
            labelErreur.setText("Erreur ! Veuillez remplir tout les champs");
            labelErreur.setVisible(true);

        }
        identifiant.setStyle(identifiant.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        mdp.setStyle(mdp.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");


    }

    @FXML
    public void inscriptionAction(ActionEvent e){

    }

    @FXML
    public void forgotPassword(ActionEvent e){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
    }
}
