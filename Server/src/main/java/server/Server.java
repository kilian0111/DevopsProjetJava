package main.java.server;



import main.java.common.Action;
import main.java.common.Message;
import main.java.common.ObjectSend;
import main.java.common.UserSafeData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private Integer port;
    private List<ConnectedClient> lesClients;

    public Server(Integer port) throws IOException {
        this.port = port;
        this.lesClients = new ArrayList<>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    public Integer getPort() {return port;}
    public void setPort(Integer port) {this.port = port;}

    public List<ConnectedClient> getLesClients() {return lesClients;}
    public void addClient(ConnectedClient Client) {this.lesClients.add(Client);}

    public void disconnectedClient(ConnectedClient client)  {
        try{
            client.closeClient();
        }catch (Exception e){
            e.printStackTrace();
        }
        this.lesClients.remove(client);
        if(client.getUser() != null && client.getUser().getPseudo() != null){
            this.sendToAll(client.getUser().getPseudo() + " Vient de se déconnecter (Quelle Indignité) ");
        }

    }

    public void sendToAll(String content){
        UserSafeData serveur = new UserSafeData();
        serveur.setPseudo("Bot");
        serveur.setId(0L);
        Message message = new Message();
        message.setVisible(true);
        message.setConversationId(0L);
        message.setContent(content);
        message.setUtilisateurSender(serveur);

        for(ConnectedClient connectedClient : this.lesClients){
            connectedClient.sendToClient(new ObjectSend(message,Action.MESSAGE));
        }

    }




}
