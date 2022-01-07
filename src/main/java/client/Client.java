package main.java.client;


import main.java.client.gui.application.ClientPanel;
import main.java.common.Action;
import main.java.common.ObjectSend;
import main.java.common.User;
import main.java.server.ConnectedClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private int port;
    private String address;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;
    private MainGui mainGui;


    public Client(int port, String address, MainGui mainGui) throws IOException {
        this.port = port;
        this.address = address;
        this.socket = new Socket(this.address,this.port);
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.user = new User();
        this.mainGui = mainGui;
    }

    public void seConnecter(User user){
            try {
                out.writeObject(new ObjectSend(user,Action.CONNECTION));
                out.flush();
                Thread threadConnection = new Thread(new ClientReceive(this,this.socket));
                threadConnection.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void disconnectedServer(){
        try {
            this.out.close();
            this.in.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public User getUser() {return user; }
    public void setUser(User user) {this.user = user;}

    public MainGui getMainGui() {return mainGui;}
    public void setMainGui(MainGui mainGui) {this.mainGui = mainGui;}
}
