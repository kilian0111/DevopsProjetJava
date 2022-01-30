package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.java.client.Client;
import main.java.common.UserSafeData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreerConversationController implements Initializable,Icontrolleur {


    private Client Client;
    private List<UserSafeData> lesUsers;

    @FXML
    private ListView lesConversations;
    @Override
    public main.java.client.Client getClient() {
        return Client;
    }

    @Override
    public void setClient(main.java.client.Client client) {
        Client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void chargerData(List<UserSafeData> lesUsers){
        this.lesUsers =  lesUsers;


    }

    public void addNewConvAction(ActionEvent actionEvent) {
    }
}
