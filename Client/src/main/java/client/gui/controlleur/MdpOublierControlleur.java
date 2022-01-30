package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.client.Client;
import main.java.common.Action;
import main.java.common.ChangeMdp;
import main.java.common.ObjectSend;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MdpOublierControlleur implements Initializable,Icontrolleur {

    private Client client;
    @FXML
    private TextField codeTxt;
    @FXML
    private PasswordField mdpTxt;
    @FXML
    private PasswordField mdpTxt2;
    @FXML
    private Label labelErreur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Retour à la page de connexion
     * @param actionEvent
     */
    public void retourConnectionAction(ActionEvent actionEvent) {
        this.client.getMainGui().changeScene("connection.fxml");
    }

    /**
     * L'utilisateur confirme son changement
     * @param actionEvent
     */
    public void validerChangementMdpAction(ActionEvent actionEvent) {
        if(!this.codeTxt.getText().isBlank() && !this.mdpTxt.getText().isBlank() && !this.mdpTxt2.getText().isBlank()){
            if(this.mdpTxt.getText().length() >= 4){
                if(this.mdpTxt.getText().equals(this.mdpTxt2.getText())){
                    ChangeMdp dataForChange = new ChangeMdp(this.codeTxt.getText(),this.mdpTxt.getText());
                    this.client.sendToServer(new ObjectSend(dataForChange, Action.CHANGEMENT_MDP));
                    this.labelErreur.setVisible(false);
                }else{
                    this.labelErreur.setText("les mots de passe ne correspondent pas");
                    this.labelErreur.setVisible(true);
                }
            }else{
                this.labelErreur.setText("Le mot de passe est trop court");
                this.labelErreur.setVisible(true);
            }
        }else{
            this.labelErreur.setText("Veuillez remplir tout les champs");
            this.labelErreur.setVisible(true);
        }
        codeTxt.setStyle(codeTxt.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        mdpTxt.setStyle(mdpTxt.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        mdpTxt2.setStyle(mdpTxt2.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
    }
}
