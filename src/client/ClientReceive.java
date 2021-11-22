package client;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive implements Runnable {

    private Client client;
    private ObjectInputStream in;
    private Socket socket;


    public ClientReceive(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    public void clientReceive(Client client, Socket socket){

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
            this.in = new ObjectInputStream(this.socket.getInputStream());
            boolean isActive =true;
            while(isActive){
                Message mess = (Message) in.readObject();
                if(mess != null){
                        this.client.messageReceived(mess);
                    }else{
                        isActive = false;
                        client.disconnectedServer();
                    }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
