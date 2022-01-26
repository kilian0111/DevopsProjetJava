package main.java.client.gui.controlleur;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.client.Client;
import main.java.client.MainGui;
import main.java.common.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable,Icontrolleur {

    private Client client;

    @FXML
    private TextFlow messagesList;

    @FXML
    private ListView lesConversations;



    public void sendMessage(ActionEvent event) {
        //addMessage(new Message(0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultricies vitae mi vitae imperdiet. Proin a fringilla justo. Sed eu lectus a felis varius iaculis vel faucibus risus. Fusce hendrerit fringilla elit ac porttitor. Cras iaculis, urna ut euismod mollis, lectus metus congue mi, at malesuada metus nunc at libero. Curabitur ac sem eget quam volutpat iaculis a vel leo. Nunc bibendum turpis lorem, eu blandit massa ultrices sed. Aenean vel quam lacus. Vivamus diam arcu, laoreet vitae diam quis, tempor egestas nulla"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.lesConversations.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

            }
        });


    }


    public void chargerData(){

        // conversation générale
        Conversations convgen = new Conversations();
        convgen.setLesMessages(new ArrayList<>());
        UtilisateursConversationsId idgeneral = new UtilisateursConversationsId();
        idgeneral.setConversations(convgen);
        UtilisateursConversations generale = new UtilisateursConversations();
        generale.setId(idgeneral);
        List<UtilisateursConversations> lesConv = new ArrayList<>();
        lesConv.add(generale);

        // toute les autres conv
        lesConv.addAll(this.client.getLesConversations());

        this.lesConversations.getItems().setAll(lesConv);
        System.out.println("test");
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
        /*String userName = message.getSender() + "";
        if (client.getUser().getId()  != null && client.getUser().getId() == message.getSender()) {
            userName = "Moi";
        }

        Text name = new Text(userName + "\n");
        name.getStyleClass().add("userName");
        Text userMessage = new Text(message.getContent() + "\n\n");

        messagesList.getChildren().add(name);
        messagesList.getChildren().add(userMessage);*/

    }

    public void onConversationClick(MouseEvent mouseEvent) {

    }
}
