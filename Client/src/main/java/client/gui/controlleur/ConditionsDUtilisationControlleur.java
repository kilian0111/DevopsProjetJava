package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.client.Client;
import main.java.common.Utils;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ConditionsDUtilisationControlleur implements Initializable,Icontrolleur {

    /**
     * Client courant
     */
    private Client client;

    /**
     * Contient les conditions d'utilisations
     */
    @FXML
    public TextFlow txtConditionUtils;


    /**
     * Retourne l'instance du client connecté
     * @return Client instance du client connecté
     */
    @Override
    public Client getClient() {
        return client;
    }

    /**
     * Définis l'instance du client connecté
     * @param client
     */
    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Appelé lors de l'initialisation de la vue Application
     * Affiche les conditions à l'utilisateur
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File(Utils.getResourcesPath() + "file/conditionsUtilisations");

        String result = "";
        try {
            InputStream inputStream = new FileInputStream(file);
            result = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Text lesConditions = new Text(result);
        txtConditionUtils.getChildren().add(lesConditions);
    }

}
