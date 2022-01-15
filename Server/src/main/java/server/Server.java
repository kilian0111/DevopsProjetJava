package main.java.server;



import main.java.repository.UserJpaRepository;

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
    }
}
