package main.java.client;



import javafx.application.Platform;
import main.java.common.Action;
import main.java.common.Message;
import main.java.common.ObjectSend;
import main.java.common.User;

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
                Object object =  in.readObject();
                if(object instanceof ObjectSend objectReceive){

                    if(objectReceive.getObject() instanceof User && objectReceive.getAction().equals(Action.CONNECTION)){
                        this.client.setUser((User) objectReceive.getObject());
                        if(this.client.getUser().getId() != null){

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    client.getMainGui().changeScene("application.fxml");
                                }
                            });

                        }
                    }


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
