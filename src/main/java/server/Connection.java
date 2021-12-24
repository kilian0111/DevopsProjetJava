package main.java.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {

    private Server server;
    private ServerSocket serverSocket;


    public Connection(Server server) throws IOException {
        this.server = server;
        this.serverSocket = new ServerSocket(server.getPort());
    }

    public Server getServer() {return server;}
    public void setServer(Server server) {this.server = server;}

    public ServerSocket getServerSocket() {return serverSocket;}
    public void setServerSocket(ServerSocket serverSocket) {this.serverSocket = serverSocket;}

    @Override
    public void run() {
        while(true){
            try {
                Socket sockNewClient = serverSocket.accept();
                ConnectedClient newClient = new ConnectedClient(sockNewClient,this.server);
                this.server.addClient(newClient);
                Thread threadNewClient = new Thread(newClient);
                threadNewClient.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
