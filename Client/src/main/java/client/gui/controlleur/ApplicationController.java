package main.java.client.gui.controlleur;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.client.Client;
import main.java.client.gui.ConversationListCell;
import main.java.common.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable,Icontrolleur {


    private Client client;

    private UtilisateursConversations currentConv;

    @FXML
    private TextFlow messagesList;

    @FXML
    private ListView<UtilisateursConversations> lesConversations;

    @FXML
    private TextArea textAreaMessage;

    @FXML
    private Label accountName;

    @FXML
    private Button playButton;

    public void sendMessage(ActionEvent event) {
        if(!this.textAreaMessage.getText().isBlank()){
            List<Message> lesMessages = this.currentConv.getId().getConversations().getLesMessages();
            UserSafeData currentUser = new UserSafeData();
            currentUser.setId(this.client.getUser().getId());
            currentUser.setPseudo(this.client.getUser().getPseudo());
            Message nouvMessage = new Message();
            nouvMessage.setDateMessage(new Date());
            nouvMessage.setContent(textAreaMessage.getText());
            nouvMessage.setVisible(true);
            nouvMessage.setConversationId(this.currentConv.getId().getConversations().getConversationId());
            nouvMessage.setUtilisateurSender(currentUser);
            this.addMessage(nouvMessage);
            this.client.addMessage(nouvMessage);

            this.textAreaMessage.setText("");
            this.textAreaMessage.setStyle("-fx-border-color: transparent");
            this.client.sendToServer(new ObjectSend(nouvMessage, Action.MESSAGE));
        } else{
            this.textAreaMessage.setStyle("-fx-border-color: red");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.lesConversations.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                currentConv = (UtilisateursConversations) t1;
                messagesList.getChildren().clear();
                currentConv.getId().getConversations().getLesMessages().forEach(message -> { addMessage(message); });

                setPlayButtonState(currentConv);
            }
        });
    }


    public void chargerData(){
        // conversation générale
        Conversations convgen = new Conversations();
        convgen.setConversationId(0L);
        convgen.setLesMessages(new ArrayList<>());
        convgen.setConversationNom("Générale");
        UtilisateursConversationsId idgeneral = new UtilisateursConversationsId();
        idgeneral.setConversations(convgen);
        UtilisateursConversations generale = new UtilisateursConversations();
        generale.setId(idgeneral);

        List<UtilisateursConversations> lesConv = new ArrayList<>();
        lesConv.add(generale);

        // toute les autres conv
        lesConv.addAll(this.client.getLesConversations());
        this.client.addConversation(generale);

        this.lesConversations.setCellFactory(uc -> new ConversationListCell());
        this.lesConversations.getItems().setAll(lesConv);
        this.lesConversations.getSelectionModel().select(0);;

        accountName.setText(this.client.getUser().getPseudo());
        setPlayButtonState(this.lesConversations.getSelectionModel().getSelectedItem());
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    private void setPlayButtonState(UtilisateursConversations currentConv) {
        Conversations conversations = currentConv.getId().getConversations();

        if (conversations.getLesUsers() != null && conversations.getLesUsers().size() == 2) {
            this.playButton.setDisable(false);
        } else {
            this.playButton.setDisable(true);
        }
    }

    private void addMessage(Message message) {
        List<Message> lesMessages = this.currentConv.getId().getConversations().getLesMessages();


        String userName = message.getUtilisateurSender().getPseudo();
        if (client.getUser().getId()  != null && client.getUser().getId().equals(message.getUtilisateurSender().getId())) {
            userName = "Moi";
        }

        Text name = new Text(userName + "\n");
        name.getStyleClass().add("userName");
        Text userMessage = new Text(message.getContent() + "\n\n");

        messagesList.getChildren().add(name);
        messagesList.getChildren().add(userMessage);
    }

}
