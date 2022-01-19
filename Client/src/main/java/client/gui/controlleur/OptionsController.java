package main.java.client.gui.controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.client.Client;
import main.java.common.Action;
import main.java.common.ObjectSend;
import main.java.common.User;
import main.java.common.Utils;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Icontrolleur, Initializable {

    private Client client;

    @FXML //fx:id="email"
    private TextField email;
    @FXML //fx:id="confirmEmail"
    private TextField confirmEmail;

    @FXML //fx:id="mdp"
    private PasswordField mdp;
    @FXML //fx:id="confirmMdp"
    private PasswordField confirmMdp;

    @FXML //fx:id="pseudo"
    private TextField pseudo;
    @FXML //fx:id="confirmPseudo"
    private TextField confirmPseudo;

    @FXML //fx:id="labelErreur"
    private Label labelErreur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public Client getClient() {
        return null;
    }

    @Override
    public void setClient(Client client) {

    }

    /**
     *
     * @param e
     * @throws IOException
     */
    @FXML
    public void EnvoyerAction(ActionEvent e) throws IOException {

        User user = this.client.getUser();
        Boolean modif = false;

        //On controle si des champs sont remplis
        if(!email.getText().isBlank() || !confirmEmail.getText().isBlank() || !mdp.getText().isBlank() || !confirmMdp.getText().isBlank() || !pseudo.getText().isBlank() || !confirmPseudo.getText().isBlank())
        {
            //On controle si les champs email remplis sont correct
            if (!email.getText().isBlank() && !confirmEmail.getText().isBlank())
            {
                if (Utils.isEmailAdress(email.getText())) {
                    if (email.getText().equals(confirmEmail.getText())) {
                        //Rentrons le nouveau email dans la bdd
                        user.setMail(email.getText());
                        modif = true;
                    } else {
                        labelErreur.setText("Erreur ! L'adresse e-mail rentrée et sa confirmation ne correspondent pas !");
                        email.setStyle(email.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
                        confirmEmail.setStyle(confirmEmail.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
                    }
                } else {
                    labelErreur.setText("Erreur ! L'adresse e-mail rentrée est incorrecte !");
                    email.setStyle(email.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
                    confirmEmail.setStyle(confirmEmail.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
                }

                //On controle dans un premier temps si les champs mdp remplis sont correct
                if (!mdp.getText().isBlank() && !confirmMdp.getText().isBlank()) {

                    if (mdp.getText().equals(confirmMdp.getText())) {
                        //Rentrons le nouveau mdp dans la bdd

                        user.setMdp(mdp.getText());
                        modif = true;
                    } else {
                        labelErreur.setText("Erreur ! Le mot de passe rentré et sa confirmation ne correspondent pas !");
                        mdp.setStyle(mdp.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
                        confirmMdp.setStyle(confirmMdp.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
                    }

                    //On controle dans un premier temps si les champs nom d'affichage remplis sont correct
                    if (!pseudo.getText().isBlank() && !confirmPseudo.getText().isBlank())
                    {

                        if (pseudo.getText().equals(confirmPseudo.getText())) {
                            //Rentrons le nouveau nom d'affichage dans la bdd

                            user.setPseudo(pseudo.getText());
                            modif = true;
                        } else {
                            labelErreur.setText("Erreur ! Le pseudo rentré et sa confirmation ne correspondent pas !");
                            pseudo.setStyle(pseudo.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
                            confirmPseudo.setStyle(confirmPseudo.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: transparent");
                        }
                    }
                    else {
                        labelErreur.setText("Erreur ! Veuillez remplir tous les champs nom d'affichage !");
                    }
                }
                else {
                    labelErreur.setText("Erreur ! Veuillez remplir tous les champs mot de passe !");
                }
            }
            else {
                labelErreur.setText("Erreur ! Veuillez remplir tous les champs e-mail !");
            }
        }
        else {
            labelErreur.setText("Erreur ! Veuillez remplir les champs !");
        }

        if(modif)
        {
            this.client.sendToServer(new ObjectSend(user, Action.MODIF_USER));
        }
        //On attend que le serveur répond et nous dis que les changements ont bien été faits
    }

    @FXML //Le bouton annuler ramène à l'application
    public void annulerAction(ActionEvent e) throws IOException {
        this.client.getMainGui().changeScene("application.fxml");
    }
}
