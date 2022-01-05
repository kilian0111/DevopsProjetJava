package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import main.java.client.Client;
import main.java.client.MainGui;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable,Icontrolleur {

    private Client client;
    private MainGui mainGui;

    public void sendMessage(ActionEvent event) {
        System.out.println("ZKOFPIQPGKI");
    }

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

    @Override
    public MainGui getMainGui() {
        return mainGui;
    }

    @Override
    public void setMainGui(MainGui mainGui) {
        this.mainGui = mainGui;
    }
}
