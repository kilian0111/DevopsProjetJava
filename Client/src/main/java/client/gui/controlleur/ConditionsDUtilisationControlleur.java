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


    private Client client;

    @FXML
    public TextFlow txtConditionUtils;



    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

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
