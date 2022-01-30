package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.client.Client;
import main.java.client.gui.listcell.UserListCell;
import main.java.common.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CreerConversationController implements Initializable,Icontrolleur {

    /**
     * Client courant
     */
    private Client client;
    /**
     * Tous les utilisateurs existants
     */
    private List<UserSafeData> lesUsers;

    /**
     * Input du nom de la conversation
     */
    @FXML
    private TextArea conversationName;

    /**
     * Selection des utilisateurs
     */
    @FXML
    private ListView<UserSafeData> lesUtilisateurs;
    /**
     * Retourne l'instance du client connecté
     * @return Client instance du client connecté
     */
    @Override
    public main.java.client.Client getClient() {
        return client;
    }
    /**
     * Définis l'instance du client connecté
     * @param client
     */
    @Override
    public void setClient(main.java.client.Client client) {
        this.client = client;
    }

    /**
     * Appelé lors de l'initialisation de la Vue
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lesUtilisateurs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Charge tous les utilisateurs et les affiche
     * @param lesUsers
     */
    public void chargerData(List<UserSafeData> lesUsers){
        this.lesUsers =  lesUsers;

        this.lesUtilisateurs.setCellFactory(user -> new UserListCell());
        this.lesUtilisateurs.getItems().setAll(lesUsers);
    }

    /**
     * Appelé lors de l'appui sur le bouton de création de conversation
     * @param actionEvent
     */
    public void addNewConvAction(ActionEvent actionEvent) {
        String name = this.conversationName.getText();

        if (name.isBlank() || name.isEmpty()) {
            this.client.getMainGui().erreurPopUp("Nom de conversation invalide", "Merci de rentrer un nom de conversation valide.", Alert.AlertType.ERROR);
            return;
        }

        List<UserSafeData> users = this.lesUtilisateurs.getSelectionModel().getSelectedItems();

        if (users.size() == 0) {
            this.client.getMainGui().erreurPopUp("Aucun utilisateur sélectionné", "Merci de sélectionner au moins un utilisateur.", Alert.AlertType.ERROR);
            return;
        }
        UserSafeData currentUser = new UserSafeData();
        currentUser.setId(this.client.getUser().getId());
        currentUser.setPseudo(this.client.getUser().getPseudo());


        Conversations conversations = new Conversations();
        conversations.setConversationNom(name);
        conversations.setDateCreationConv(new Date());
        conversations.setUtilisateurCreateurID(this.client.getUser().getId());
        this.client.sendToServer(new ObjectSend(conversations, Action.CREATE_CONV));
        List<UtilisateursConversations> lesUtilisateursConversations  = new ArrayList<>();

        UtilisateursConversations userConvCurrentUser = new UtilisateursConversations();
        UtilisateursConversationsId userConvIdCurrentUser = new UtilisateursConversationsId();
        userConvIdCurrentUser.setConversations(conversations);
        userConvIdCurrentUser.setUtilisateur(currentUser);
        userConvCurrentUser.setId(userConvIdCurrentUser);
        lesUtilisateursConversations.add(userConvCurrentUser);

        for(UserSafeData userSafeData : users){
            UtilisateursConversations userConv = new UtilisateursConversations();
            UtilisateursConversationsId userConvId = new UtilisateursConversationsId();
            userConvId.setConversations(conversations);
            userConvId.setUtilisateur(userSafeData);
            userConv.setId(userConvId);
            lesUtilisateursConversations.add(userConv);
        }

        this.client.sendToServer(new ObjectSend(lesUtilisateursConversations, Action.AJOUT_USER_CONV));


    }
    /**
     * Appelé lors de l'appui sur le bouton d'annulation
     * @param actionEvent
     */
    public void retourApplicationsAction(ActionEvent actionEvent) {
        this.client.getMainGui().changeScene("application.fxml");
    }
}
