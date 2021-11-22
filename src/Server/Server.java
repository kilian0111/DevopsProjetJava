package Server;

import Common.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private Integer port;
    private List<ConnectedClient> clients;

    public Server(Integer port) throws IOException {
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    public Integer getPort() {return port;}
    public void setPort(Integer port) {this.port = port;}

    public List<ConnectedClient> getClients() {return clients;}
    public void setClients(List<ConnectedClient> clients) {this.clients = clients;}

    public int getNumClients(){
        return this.clients.size();
    }

    public ConnectedClient addClient(ConnectedClient newClient) throws IOException {
        Message mess = new Message("Server",newClient.getId() + " vient de se connecter");
        for(ConnectedClient client : clients){
                client.sendMessage(mess);
        }
        this.clients.add(newClient);
        return newClient;
    }

    public int broadcastMessage(Message mess, int id) throws IOException {
        for(ConnectedClient client :clients){
            if(client.getId() != id){
                client.sendMessage(mess);
            }
        }
        return id;
    }

    public void disconnectedClient(ConnectedClient discClient) {
        try {
            discClient.closeClient();
        this.clients.remove(discClient);
        for(ConnectedClient client : clients){
            client.sendMessage(new Message("server","Le client "+ discClient.getId() + " nous a quitté"));
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
