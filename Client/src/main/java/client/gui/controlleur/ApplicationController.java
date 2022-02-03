package main.java.client.gui.controlleur;

import javafx.application.Platform;
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
import main.java.client.gui.listcell.UserListCell;
import main.java.common.*;

import java.net.URL;
import java.util.*;

public class ApplicationController implements Initializable,Icontrolleur {


    /**
     * Client courant
     */
    private Client client;
    /**
     * Conversation séléctionnée par l'utilisateur
     */
    private UtilisateursConversations currentConv;

    /**
     * Liste des messages
     */
    @FXML
    private TextFlow messagesList;

    /**
     * Conversations de l'utilisateur
     */
    @FXML
    private ListView<UtilisateursConversations> lesConversations;

    /**
     * Zone d'envoi de message
     */
    @FXML
    private TextArea textAreaMessage;

    /**
     * Nom de l'utilisateur
     */
    @FXML
    private Label accountName;

    /**
     * Utilisateurs de la conversation courante
     */
    @FXML
    private ListView convUsers;

    /**
     * Utilisateurs connectés
     */
    @FXML
    private ListView<UserSafeData> connectedUsers;

    /**
     * Bouton de jeu
     */
    @FXML
    private Button playButton;

    @FXML
    private ScrollPane scrollMessage;

    /**
     * Nom de la conversation courante
     */
    @FXML
    private Label convName;

    /**
     * Envoi un message aux autres utilisateurs
     * @param event Raison de l'appel de la méthode
     */
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

    /**
     * Appelé lors de l'initialisation de la vue Application
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.lesConversations.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                currentConv = (UtilisateursConversations) t1;
                messagesList.getChildren().clear();
                    currentConv.getId().getConversations().getLesMessages().forEach(message -> { addMessage(message); });
                    scrollMessage.vvalueProperty().bind(messagesList.heightProperty());
                    convName.setText(currentConv.nomConv());
                    setPlayButtonState();

                    if (currentConv.getId().getConversations().getConversationId() == 0) {
                        convUsers.getItems().clear();
                        convUsers.setDisable(true);
                        return;
                    }

                convUsers.setDisable(false);
                convUsers.setCellFactory(user -> new UserListCell());
                convUsers.getItems().setAll(currentConv.getId().getConversations().getLesUsers());



            }
        });


    }

    /**
     * Récupère les données et les affiche
     */
    public void chargerData(){
        this.lesConversations.setCellFactory(uc -> new ConversationListCell());
        this.lesConversations.getItems().setAll(this.client.getLesConversations());
        this.lesConversations.getSelectionModel().select(0);
        this.currentConv = this.lesConversations.getSelectionModel().getSelectedItem();
        accountName.setText(this.client.getUser().getPseudo());
        this.setPlayButtonState();
        this.addAllUserCo();
    }

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
     * Définis l'état du bouton "Jouer" selon la conversation
     * Le bouton est accessible uniquement si ce n'est pas un groupe
     */
    private void setPlayButtonState() {
        if (currentConv.getId().getConversations().getLesUsers() != null &&  currentConv.getId().getConversations().getLesUsers().size() == 2) {
            this.playButton.setDisable(false);
        } else {
            this.playButton.setDisable(true);
        }
    }

    /**
     * Affiche un message à l'utilisateur
     * @param message
     */
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

    /**
     * Affiche un message reçu à l'utilisateur
     * @param message Message reçu
     */
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

    /**
     * Demande au serveur d'envoyer tous les utilisateurs existants
     * @param actionEvent Raison de l'appel de la méthode
     */
    public void addNewConvAction(ActionEvent actionEvent) {
        this.client.sendToServer(new ObjectSend(null , Action.LIST_USER));
    }

    /**
     * Deconnecte l'utilisateur courant
     * @param actionEvent Raison de l'appel de la méthode
     */
    public void deconexionAction(ActionEvent actionEvent) {
        this.client.setUser(new User());
        this.client.setLesConversations(null);
        this.client.setApplicationController(null);
        this.client.getMainGui().changeScene("connection.fxml");
    }

    /**
     * Appelé lorsque l'utilisateur décide de lancer une partie
     * @param actionEvent Raison de l'appel de la méthode
     */
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

    public void addAllUserCo(){
        this.connectedUsers.setCellFactory(user -> new UserListCell());
        this.connectedUsers.getItems().setAll(this.client.getLesUserConnecter());
    }

    /**
     * Met à jour la liste d'utilisateurs connectés
     */
    public void addNewUserCo(UserSafeData userSafeData) {
        this.connectedUsers.getItems().add(userSafeData);
    }
    public void removeUserCo(UserSafeData userSafeData) {
        this.connectedUsers.getItems().remove(userSafeData);
    }

    public void removeUserConv(Conversations conv) {
        if(this.currentConv.getId().getConversations().getConversationId().equals(conv.getConversationId())){
            this.convUsers.getItems().remove(conv.getLesUsers().get(0));
        }

    }
    /**
     * Affiche les paramètres
     * @param actionEvent Raison de l'appel de la méthode
     */
    public void settingAction(ActionEvent actionEvent) {
        this.client.getMainGui().changeScene("options.fxml");
    }

    public void quitterConvAction(ActionEvent actionEvent){
        //Si la conversation est la général
        if(!this.currentConv.getId().getConversations().getConversationId().equals(0L))
        {
            this.client.sendToServer(new ObjectSend(this.currentConv, Action.QUITTER_CONV));
            List<UtilisateursConversations> lesConvs = this.client.getLesConversations();
            lesConvs.remove(this.currentConv);
            this.client.setLesConversations(lesConvs);
            this.lesConversations.getItems().remove(this.currentConv);
            this.lesConversations.getSelectionModel().select(0);
        }
        else
        {
            this.client.getMainGui().erreurPopUp("Information","On ne peut pas quitter la conversation générale !", Alert.AlertType.INFORMATION);
        }
    }

}
