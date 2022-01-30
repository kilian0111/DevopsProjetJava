package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.client.Client;
import main.java.client.gui.listcell.UserListCell;
import main.java.common.UserSafeData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreerConversationController implements Initializable,Icontrolleur {


    private Client client;
    private List<UserSafeData> lesUsers;

    @FXML
    private TextArea conversationName;

    @FXML
    private ListView<UserSafeData> lesUtilisateurs;

    @Override
    public main.java.client.Client getClient() {
        return client;
    }

    @Override
    public void setClient(main.java.client.Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lesUtilisateurs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void chargerData(List<UserSafeData> lesUsers){
        this.lesUsers =  lesUsers;

        this.lesUtilisateurs.setCellFactory(user -> new UserListCell());
        this.lesUtilisateurs.getItems().setAll(lesUsers);
    }

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

        // TODO : Créer la conv
    }

    public void closeNewConversation(ActionEvent actionEvent) {
        this.client.getMainGui().changeScene("application.fxml");
    }
}
