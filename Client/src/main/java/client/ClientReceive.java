package main.java.client;



import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import main.java.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientReceive implements Runnable {

    private Client client;
    private ObjectInputStream in;
    private Socket socket;


    public ClientReceive(Client client) {
        this.client = client;
    }



    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            this.client.setSocket(new Socket(this.client.getAddress(),this.client.getPort()));
            this.socket = this.client.getSocket();
            this.client.setOut(new ObjectOutputStream(this.socket.getOutputStream()));
        } catch (IOException ignored) {
            Platform.runLater(() -> client.getMainGui().erreurPopUp("ATTENTION", "Erreur de connection au serveur", AlertType.ERROR));
        }

       while(true){
           //permet la reconnection au server
           while(this.socket == null){
               try{
                   this.client.setSocket(new Socket(this.client.getAddress(),this.client.getPort()));
                   this.socket = this.client.getSocket();
                   this.client.setOut(new ObjectOutputStream(this.socket.getOutputStream()));
                   Platform.runLater(() -> this.client.getMainGui().erreurPopUp("INFORMATION", "Vous êtes de nouveau connecté(e)", Alert.AlertType.INFORMATION));
               } catch (Exception e) {
                   try {
                       Thread.sleep(10000);
                   } catch (InterruptedException ex) {
                       ex.printStackTrace();
                   }
               }
           }
           if(this.socket != null){
               try{
                   this.in = new ObjectInputStream(this.socket.getInputStream());
                   boolean isActive =true;
                   while(isActive){
                       Object object =  in.readObject();
                       if(object != null ){
                           if(object instanceof ObjectSend objectReceive) {

                               //connection
                               if (objectReceive.getObject() instanceof User && objectReceive.getAction().equals(Action.CONNECTION)) {
                                   this.connection((User) objectReceive.getObject());
                                   //mdp oubliee
                               }else if(objectReceive.getAction().equals(Action.REPONSE_MDP_OUBLIEE)){
                                   this.demandeMdpOublier(objectReceive);
                                   // reponse changement de mdp
                               } else if(objectReceive.getAction().equals(Action.REPONSE_CHANGEMENT_MDP)){
                                   this.changementMdp(objectReceive);
                                   // Modification User
                               } else if(objectReceive.getAction().equals(Action.REPONSE_MODIFUSER) && objectReceive.getObject() instanceof String){
                                   Platform.runLater(() -> client.getMainGui().erreurPopUp("Validation", "Les modifications ont bien été prises en compte !", AlertType.CONFIRMATION));
                                   Platform.runLater(() -> client.getMainGui().changeScene("application.fxml"));
                               // Inscription
                               }else if(objectReceive.getAction().equals(Action.INSCRIPTION)){
                                   this.responseInscription(objectReceive);
                               } else if(objectReceive.getAction().equals(Action.LIST_CONVERSATION) && objectReceive.getObject() instanceof List ){
                                   this.client.setLesConversations((List<UtilisateursConversations>) objectReceive.getObject());
                                   Platform.runLater(() -> client.getMainGui().changeScene("application.fxml"));
                               }

                           }
                       }else{
                           isActive = false;
                       }
                   }
               } catch (IOException | ClassNotFoundException e) {
                   e.printStackTrace();
               }
           }
           this.client.disconnectedServer();
           Platform.runLater(() -> client.getMainGui().erreurPopUp("ATTENTION", "Erreur de connection au serveur", AlertType.ERROR));
           this.socket = null;

       }

    }

    public void responseInscription(ObjectSend objectReceive){
        if(objectReceive.getObject() instanceof String){
            Platform.runLater(() -> client.getMainGui().erreurPopUp("ATTENTION", (String) objectReceive.getObject(), AlertType.ERROR));
        }else if(objectReceive.getObject() instanceof User){
            this.client.setUser((User) objectReceive.getObject());
        }
    }

    public void changementMdp(ObjectSend objectReceive){

        String reponse = (String) objectReceive.getObject();
        boolean retourPageConnaction = true;


        if(objectReceive.getObject() instanceof String){

            if("ok".equals(reponse)){
                Platform.runLater(() ->this.client.getMainGui().erreurPopUp("INFORMATION","Votre mots de passe a bien été changé",AlertType.INFORMATION));
            }else if("tooLong".equals(reponse)){
                Platform.runLater(() ->this.client.getMainGui().erreurPopUp("ERREUR","La durée de vie du code reçu par mail est expiré Veuillez en redemander un",AlertType.ERROR));
            }else if("badCode".equals(reponse)){
                retourPageConnaction = false;
                Platform.runLater(() ->this.client.getMainGui().erreurPopUp("ERREUR","Code saisie Invalide veuillez réessayer",AlertType.ERROR));
            }else if("tooManyTry".equals(reponse)){
                Platform.runLater(() ->this.client.getMainGui().erreurPopUp("ERREUR","Vous avez fait trop d'essais pour ce code veuillez en redemander un",AlertType.ERROR));
            }else if("codeNotExists".equals(reponse)){
                Platform.runLater(() ->this.client.getMainGui().erreurPopUp("ERREUR","pas de code enregistrer veuillez en redemander un",AlertType.ERROR));
            }
        }else{
            Platform.runLater(() ->this.client.getMainGui().erreurPopUp("ERREUR","Erreur Interne",AlertType.ERROR));
        }

        if(retourPageConnaction){
            Platform.runLater(() ->this.client.getMainGui().changeScene("connection.fxml"));
        }


    }

    public void demandeMdpOublier(ObjectSend objectReceive){
        if(objectReceive.getObject() instanceof String){
            if("ok".equals((String) objectReceive.getObject())){
                Platform.runLater(() ->this.client.getMainGui().changeScene("mdpOublier.fxml"));
            }else{
                Platform.runLater(() ->this.client.getMainGui().erreurPopUp("INFORMATION","Impossible de rénitialiser le mdp votre compte est désactivé",AlertType.INFORMATION));
            }
        }else{
            Platform.runLater(() ->this.client.getMainGui().erreurPopUp("ERREUR","le Mail que vous avez saisie n'est pas enregistré",AlertType.ERROR));
        }
    }

    public void connection(User user){
        if(user.getId() != null){
            if(user.getActif()){
                this.client.setUser(user);
            }else{
                Platform.runLater(() -> client.getMainGui().erreurPopUp("Erreur","votre compte est désactivé", AlertType.INFORMATION));
            }

        } else {
            Platform.runLater(() -> client.getMainGui().erreurPopUp("Erreur","Identifiants ou mots de passe incorrecte", AlertType.ERROR));
        }
    }
}
