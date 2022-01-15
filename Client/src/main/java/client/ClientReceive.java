package main.java.client;



import javafx.application.Platform;
import javafx.scene.control.Alert.*;
import main.java.common.Action;
import main.java.common.ObjectSend;
import main.java.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientReceive implements Runnable {

    private Client client;
    private ObjectInputStream in;
    private Socket socket;


    public ClientReceive(Client client) {
        this.client = client;
        try{
            this.client.setSocket(new Socket(this.client.getAddress(), this.client.getPort()));
            this.socket = this.client.getSocket();
            this.client.setOut(new ObjectOutputStream(this.socket.getOutputStream()));
        }catch (Exception ignored){
        }

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

        }catch (Exception ignored){
            Platform.runLater(() ->this.client.getMainGui().erreurPopUp("ATTENTION", "Erreur de connection au serveur", AlertType.ERROR));
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

        while(this.client.connectionServer()){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.client.lanceThread();

    }

    public void changementMdp(ObjectSend objectReceive){

        String reponse = (String) objectReceive.getObject();
        Boolean retourPageConnaction = true;


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
                Platform.runLater(() -> client.getMainGui().changeScene("application.fxml"));
                this.client.setUser(user);
            }else{
                Platform.runLater(() -> client.getMainGui().erreurPopUp("Erreur","votre compte est désactivé", AlertType.INFORMATION));
            }

        } else {
            Platform.runLater(() -> client.getMainGui().erreurPopUp("Erreur","Identifiants ou mots de passe incorrecte", AlertType.ERROR));
        }
    }
}
