package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.client.Client;
import main.java.client.MainGui;
import main.java.common.Message;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable,Icontrolleur {

    private Client client;


    @FXML
    private TextFlow messagesList;

    public void sendMessage(ActionEvent event) {
        addMessage(new Message(0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultricies vitae mi vitae imperdiet. Proin a fringilla justo. Sed eu lectus a felis varius iaculis vel faucibus risus. Fusce hendrerit fringilla elit ac porttitor. Cras iaculis, urna ut euismod mollis, lectus metus congue mi, at malesuada metus nunc at libero. Curabitur ac sem eget quam volutpat iaculis a vel leo. Nunc bibendum turpis lorem, eu blandit massa ultrices sed. Aenean vel quam lacus. Vivamus diam arcu, laoreet vitae diam quis, tempor egestas nulla"));
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

    private void addMessage(Message message) {
        String userName = message.getSender() + "";
        if (client.getUser().getId()  != null && client.getUser().getId() == message.getSender()) {
            userName = "Moi";
        }

        Text name = new Text(userName + "\n");
        name.getStyleClass().add("userName");
        Text userMessage = new Text(message.getContent() + "\n\n");

        messagesList.getChildren().add(name);
        messagesList.getChildren().add(userMessage);

    }
}
