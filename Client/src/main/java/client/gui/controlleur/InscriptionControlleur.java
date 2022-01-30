package main.java.client.gui.controlleur;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.client.Client;
import main.java.client.MainGui;
import main.java.common.Action;
import main.java.common.ObjectSend;
import main.java.common.User;
import main.java.common.Utils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InscriptionControlleur implements Initializable,Icontrolleur{



    private Client client;
    @FXML //fx:id="genreCombo"
    private ComboBox<String> genreCombo;
    @FXML
    private TextField pseudo;
    @FXML
    private TextField email;
    @FXML
    private TextField prenom;
    @FXML
    private TextField nom;
    @FXML
    private DatePicker dateNaissance;
    @FXML
    private PasswordField motDePasse;
    @FXML
    private PasswordField motDePasseConfirm;
    @FXML
    private CheckBox conditionInscription;
    @FXML
    private Label erreurLabel;

    /**
     * Appelé lors de l'initialisation de la vue Inscription
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreCombo.getItems().setAll("Non précisé","Homme","Femme");
        genreCombo.setValue("Non précisé");
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Affiche les conditions d'utilisation
     * @param actionEvent
     */
    public void showConditionsUtilisationAction(ActionEvent actionEvent) {
         this.client.getMainGui().showSecondNewStage("conditionsDUtilisation.fxml");
    }

    /**
     * Affiche la politique de confidentialité
     * @param actionEvent
     */
    public void showPolitiqueConfiAction(ActionEvent actionEvent) {
        this.client.getMainGui().showSecondNewStage("politiquesConfidentialite.fxml");
    }

    /**
     * Affichage la page de connexion
     * @param actionEvent
     */
    public void retourPageConnexion(ActionEvent actionEvent) {
        this.client.getMainGui().changeScene("connection.fxml");
    }

    /**
     * Utilisateur clique sur Inscription
     * @param actionEvent
     */
    public void inscriptionAction(ActionEvent actionEvent) {

        String message = "";
        try{
            if(this.dateNaissance.getValue() != null && !pseudo.getText().isBlank() && !email.getText().isBlank()
                    && !prenom.getText().isBlank() && !nom.getText().isBlank() && !motDePasse.getText().isBlank()
                    && !motDePasse.getText().isBlank() && !motDePasseConfirm.getText().isBlank()){
                if(conditionInscription.isSelected()){
                    if(Utils.isEmailAdress(this.email.getText())){
                        if(motDePasse.getText().equals(motDePasseConfirm.getText())){
                            LocalDate date = LocalDate.now();
                            if(Period.between(this.dateNaissance.getValue(), date).getYears() >= 13){
                                String salt =Utils.generateChaine();
                                User user = new User();
                                user.setSexe(genreCombo.getSelectionModel().getSelectedIndex());
                                user.setMail(email.getText());
                                user.setPseudo(pseudo.getText());
                                user.setMdp(Utils.convertMdpWithSalt(motDePasse.getText(), salt));
                                user.setSalt(salt);
                                user.setDateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse(this.dateNaissance.getValue().toString()));
                                user.setNom(nom.getText());
                                user.setPrenom(prenom.getText());
                                user.setActif(true);
                                if(this.client.estConnecter()){
                                    this.client.sendToServer(new ObjectSend(user, Action.INSCRIPTION));
                                }else{
                                    message = "erreur interne impossible de se connecter au serveur";
                                }
                            }else{
                                message = "Vous devez avoir 13 ans pour vous isncrire";
                            }
                        }else{
                            message = "les mots de passes tapés ne coressponde pas";
                        }
                    }else{
                        message = "Veuillez saisir une adresse email correcte";
                    }

                }else{
                    message = "Veuillez accepter les Conditions d'utilisation";
                }
            }else{
                message = "Veuillez Remplir tout les champs";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        this.erreurLabel.setText(message);
        this.erreurLabel.setVisible(true);

        pseudo.setStyle(pseudo.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        email.setStyle(email.getText().isBlank() || !Utils.isEmailAdress(email.getText())? "-fx-border-color: red" : "-fx-border-color: transparent");
        prenom.setStyle(prenom.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        nom.setStyle(nom.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        motDePasse.setStyle(motDePasse.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        motDePasseConfirm.setStyle(motDePasseConfirm.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
        dateNaissance.setStyle(dateNaissance.getValue() == null ? "-fx-border-color: red" : "-fx-border-color: transparent");

    }
}
