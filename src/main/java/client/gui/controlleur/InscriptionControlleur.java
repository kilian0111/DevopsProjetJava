package main.java.client.gui.controlleur;

import javafx.fxml.Initializable;
import main.java.client.Client;
import main.java.client.MainGui;

import java.net.URL;
import java.util.ResourceBundle;

public class InscriptionControlleur implements Initializable,Icontrolleur{

   private Client client;
   private MainGui mainGui;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public MainGui getMainGui() {
        return this.mainGui;
    }

    @Override
    public void setMainGui(MainGui mainGui) {
        this.mainGui=mainGui;
    }
}
