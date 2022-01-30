package main.java.client.gui.controlleur;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.client.Client;
import main.java.client.gui.listcell.ConversationListCell;
import main.java.common.*;

import java.net.URL;
import java.util.*;

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

    @FXML
    private ScrollPane scrollMessage;

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
                scrollMessage.vvalueProperty().bind(messagesList.heightProperty());
                setPlayButtonState();
            }
        });
    }

    public void chargerData(){
        this.lesConversations.setCellFactory(uc -> new ConversationListCell());
        this.lesConversations.getItems().setAll(this.client.getLesConversations());
        this.lesConversations.getSelectionModel().select(0);
        this.currentConv = this.lesConversations.getSelectionModel().getSelectedItem();
        accountName.setText(this.client.getUser().getPseudo());
        setPlayButtonState();
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    private void setPlayButtonState() {
        if (currentConv.getId().getConversations().getLesUsers() != null &&  currentConv.getId().getConversations().getLesUsers().size() == 2) {
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
        scrollMessage.vvalueProperty().bind(messagesList.heightProperty());
    }


    public void addMessageRecu(Message message) {
        for(UtilisateursConversations userConv : this.client.getLesConversations()){
            if(userConv.getId().getConversations().getConversationId().equals(message.getConversationId())){
                List<Message> lesMessages = userConv.getId().getConversations().getLesMessages();
                lesMessages.add(message);
                userConv.getId().getConversations().setLesMessages(lesMessages);
            }
        }

        Text name = new Text(message.getUtilisateurSender().getPseudo() + "\n");
        name.getStyleClass().add("userName");
        Text userMessage = new Text(message.getContent() + "\n\n");
        if(this.currentConv.getId().getConversations().getConversationId().equals(message.getConversationId())){
            messagesList.getChildren().add(name);
            messagesList.getChildren().add(userMessage);
            scrollMessage.vvalueProperty().bind(messagesList.heightProperty());
        }
    }

    public void addNewConvAction(ActionEvent actionEvent) {
        this.client.sendToServer(new ObjectSend(null , Action.LIST_USER));
    }

    public void deconexionAction(ActionEvent actionEvent) {
        this.client.setUser(new User());
        this.client.setLesConversations(null);
        this.client.setApplicationController(null);
        this.client.getMainGui().changeScene("connection.fxml");
    }

    public void playAction(ActionEvent actionEvent) {
        if(this.currentConv.getId().getConversations().getLesUsers() != null && this.currentConv.getId().getConversations().getLesUsers().size() == 2 ){
            UserSafeData userConnected = new UserSafeData();
            UserSafeData userJ2 = new UserSafeData();
            for(UserSafeData userSafeData : this.currentConv.getId().getConversations().getLesUsers() ){
                if(userSafeData.getId().equals(this.client.getUser().getId())){
                    userConnected = userSafeData;
                }else{
                    userJ2 = userSafeData;
                }
            }
            GameChifoumi game = new GameChifoumi(this.currentConv.getId().getConversations().getConversationId(),userConnected,userJ2);
            this.client.sendToServer(new ObjectSend(game,Action.LANCER_JEUX));
        }
    }

    public void settingAction(ActionEvent actionEvent) {
        this.client.getMainGui().changeScene("options.fxml");
    }

}
