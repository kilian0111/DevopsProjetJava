package main.java.client;



import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.client.gui.controlleur.ApplicationController;
import main.java.client.gui.controlleur.GameController;
import main.java.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
                               Platform.runLater(() -> client.getMainGui().erreurPopUp("Validation", "Les modifications ont bien été prises en compte !", AlertType.INFORMATION));
                               Platform.runLater(() -> client.getMainGui().changeScene("application.fxml"));
                           // Inscription
                           }else if(objectReceive.getAction().equals(Action.INSCRIPTION)){
                               this.responseInscription(objectReceive);
                               // recois la liste des conversation et des messages
                           } else if(objectReceive.getAction().equals(Action.LIST_CONVERSATION) && objectReceive.getObject() instanceof List ){
                               this.loadApplication((List<UtilisateursConversations>) objectReceive.getObject());
                               // reçoie un message
                           }else if(objectReceive.getAction() == Action.MESSAGE && objectReceive.getObject() instanceof Message ){
                               this.addMessage((Message) objectReceive.getObject());
                               // à reçu une demande de jeux
                           }else if(objectReceive.getAction() == Action.DEMANDE_JEUX && objectReceive.getObject() instanceof GameChifoumi ){
                               this.demandeJeux((GameChifoumi) objectReceive.getObject());
                               // si a demander de jouer recois la reponse
                           }else if(objectReceive.getAction() == Action.LANCER_JEUX){
                               this.reponseDemandeJeux(objectReceive.getObject());
                           }else if(objectReceive.getAction() == Action.RESULTAT_JEUX &&  objectReceive.getObject() instanceof GameChifoumi){
                               this.envoyerResultat((GameChifoumi) objectReceive.getObject() );
                           }else if(objectReceive.getAction() == Action.LIST_USER &&  objectReceive.getObject() instanceof List){
                               this.createConv( (List<UserSafeData> ) objectReceive.getObject());
                           }else if(objectReceive.getAction() == Action.USER_CONNECTER &&  objectReceive.getObject() instanceof List){
                               this.allUserConnecter( (List<UserSafeData> ) objectReceive.getObject());
                           }else if(objectReceive.getAction() == Action.ADD_USER_CONNECTER &&  objectReceive.getObject() instanceof UserSafeData){
                               this.addUserConnecter( (UserSafeData) objectReceive.getObject());
                           }else if(objectReceive.getAction() == Action.REMOVE_USER_CONNECTER &&  objectReceive.getObject() instanceof UserSafeData){
                               this.removeUserConnecter( (UserSafeData) objectReceive.getObject());
                           }else if(objectReceive.getAction() == Action.SUPPRESSSION_USER_CONV &&  objectReceive.getObject() instanceof Conversations){
                               this.removeUserConv((Conversations) objectReceive.getObject());
                           }
                       }
                   }else{
                       isActive = false;
                   }
               }
           } catch (IOException | ClassNotFoundException e) {
               e.printStackTrace();
           }
           this.client.disconnectedServer();
           this.socket = null;
           Platform.runLater(() -> client.getMainGui().erreurPopUp("ATTENTION", "Erreur de connection au serveur", AlertType.ERROR));
           Platform.runLater(() ->this.client.getMainGui().changeScene("connection.fxml"));

       }

    }

    private void removeUserConv(Conversations conversations) {
       List<UtilisateursConversations> lesUtilisateurConv =  this.client.getLesConversations();
       for(UtilisateursConversations userConv : lesUtilisateurConv){
           if(userConv.getId().getConversations().getConversationId().equals(conversations.getConversationId())){
               List<UserSafeData> lesUsers = userConv.getId().getConversations().getLesUsers();
               lesUsers.remove(conversations.getLesUsers().get(0));
               userConv.getId().getConversations().setLesUsers(lesUsers);
               break;
           }
       }
       this.client.setLesConversations(lesUtilisateurConv);
        if(this.client.getApplicationController() != null){
            ApplicationController app = this.client.getApplicationController();
            Platform.runLater(() ->app.removeUserConv(conversations));
        }
    }

    private void removeUserConnecter(UserSafeData userSafeData) {
        List<UserSafeData> lesUsersCo = this.client.getLesUserConnecter();
        lesUsersCo.remove(userSafeData);
        this.client.setLesUserConnecter(lesUsersCo);
        if(this.client.getApplicationController() != null){
            ApplicationController app = this.client.getApplicationController();
            Platform.runLater(() ->app.removeUserCo(userSafeData));
        }
    }

    private void addUserConnecter(UserSafeData userSafeData) {
        List<UserSafeData> lesUsersCo = this.client.getLesUserConnecter();
        lesUsersCo.add(userSafeData);
        this.client.setLesUserConnecter(lesUsersCo);
        if(this.client.getApplicationController() != null){
            ApplicationController app = this.client.getApplicationController();
            Platform.runLater(() ->app.addNewUserCo(userSafeData));
        }
    }

    private void allUserConnecter(List<UserSafeData> lesUserCo) {
        this.client.setLesUserConnecter(lesUserCo);
        if(this.client.getApplicationController() != null){
            ApplicationController app = this.client.getApplicationController();
            Platform.runLater(app::addAllUserCo);
        }
    }

    private void createConv(List<UserSafeData> listUser) {
        this.client.setLesUser(listUser);
        Platform.runLater(()-> this.client.getMainGui().changeScene("creerConversation.fxml"));
    }

    private void envoyerResultat(GameChifoumi gameChifoumi) {
        GameController gameController = this.client.getLesGames().get(gameChifoumi.getId());
        if(gameController != null){
            Platform.runLater(()-> gameController.setGame(gameChifoumi));
            Platform.runLater(gameController::showResult);
        }

    }

    private void reponseDemandeJeux(Object object) {
        if(object instanceof String){
            Platform.runLater(() ->this.client.getMainGui().erreurPopUp("DEMANDE JEUX",(String) object,AlertType.INFORMATION));
        }else if(object instanceof GameChifoumi){
            this.client.setLaGame((GameChifoumi) object);
            Platform.runLater(()->this.client.getMainGui().showSecondNewStage("game.fxml"));
        }
    }

    private void demandeJeux(GameChifoumi gameChifoumi) {
        Platform.runLater(()->this.client.getMainGui().demandeJeux(gameChifoumi));
    }

    private void loadApplication(List<UtilisateursConversations> lesConvByUser) {
        Conversations convgen = new Conversations();
        convgen.setConversationId(0L);
        convgen.setLesMessages(new ArrayList<>());
        convgen.setConversationNom("Générale");
        UtilisateursConversationsId idgeneral = new UtilisateursConversationsId();
        idgeneral.setConversations(convgen);
        UtilisateursConversations generale = new UtilisateursConversations();
        generale.setId(idgeneral);

        List<UtilisateursConversations> lesConvs = new ArrayList<>();
        lesConvs.add(generale);
        lesConvs.addAll(lesConvByUser);

        this.client.setLesConversations(lesConvs);
        Platform.runLater(() -> client.getMainGui().changeScene("application.fxml"));
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

    public void addMessage(Message message){
        if(this.client != null && this.client.getApplicationController() != null){
            Platform.runLater(() ->this.client.getApplicationController().addMessageRecu(message));
        }
    }
}
