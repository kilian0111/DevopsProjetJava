package main.java.client.gui.controlleur;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import main.java.client.Client;
import main.java.client.MainGui;

import java.net.URL;
import java.util.ResourceBundle;

public class InscriptionControlleur implements Initializable,Icontrolleur{

   private Client client;

   @FXML //fx:id="genreCombo"
   private ComboBox<String> genreCombo;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreCombo.getItems().setAll("Homme","Femme","Non précisé");
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }


    public void showConditionsUtilisationAction(ActionEvent actionEvent) {
         this.client.getMainGui().showSecondNewStage("conditionsDUtilisation.fxml");
    }

    public void showPolitiqueConfiAction(ActionEvent actionEvent) {
        this.client.getMainGui().showSecondNewStage("politiquesConfidentialite.fxml");
    }

    public void retourPageConnexion(ActionEvent actionEvent) {
        this.client.getMainGui().changeScene("connection.fxml");
    }
}
