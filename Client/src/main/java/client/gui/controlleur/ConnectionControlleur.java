package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.client.Client;
import main.java.client.MainGui;
import main.java.common.Action;
import main.java.common.ObjectSend;
import main.java.common.User;
import main.java.common.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionControlleur implements Initializable,Icontrolleur {


    private Client client;

    @FXML
    private TextField identifiant;
    @FXML
    private PasswordField mdp;
    @FXML
    private Label labelErreur;

    @FXML
    public void connectionAction(ActionEvent e) throws IOException {
        if(!identifiant.getText().isBlank() && !mdp.getText().isBlank()){
            labelErreur.setVisible(false);

            User user = new User();
            user.setPseudo(identifiant.getText());
            user.setMdp(mdp.getText());
            this.client.sendToServer(new ObjectSend(user, Action.CONNECTION));

        }else{
            labelErreur.setText("Erreur ! Veuillez remplir tous les champs");
            labelErreur.setVisible(true);

        }
        identifiant.setStyle(identifiant.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        mdp.setStyle(mdp.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
    }

    @FXML
    public void inscriptionAction(ActionEvent e) throws IOException {
        this.client.getMainGui().changeScene("inscription.fxml");
    }

    @FXML
    public void forgotPassword(ActionEvent e){
        if(!identifiant.getText().isBlank() && Utils.isEmailAdress(identifiant.getText())){
            labelErreur.setVisible(false);

            this.client.sendToServer(new ObjectSend(identifiant.getText(), Action.MDP_OUBLIEE));
        }else{
            labelErreur.setText("Erreur ! Veuillez remplir un e-mail");
            labelErreur.setVisible(true);
        }
        identifiant.setStyle(identifiant.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
