package main.java.client;


import javafx.application.Platform;
import javafx.scene.control.Alert;
import main.java.common.Message;
import main.java.common.ObjectSend;
import main.java.common.User;
import main.java.common.UtilisateursConversations;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class Client {

    private int port;
    private String address;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;
    private MainGui mainGui;
    private List<UtilisateursConversations> lesConversations;


    public Client(int port, String address, MainGui mainGui) throws IOException {
        this.port = port;
        this.address = address;
        this.user = new User();
        this.mainGui = mainGui;
        Thread threadConnection = new Thread(new ClientReceive(this));
        threadConnection.start();
    }


    public void sendToServer(ObjectSend objectSend){
        try {
            out.writeObject(objectSend);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectedServer(){
        try {
            if(this.out != null){
                this.out.close();
            }
            if(this.in != null ){
                this.in.close();
            }
            if(this.socket != null){
                this.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public User getUser() {return user; }
    public void setUser(User user) {this.user = user;}

    public MainGui getMainGui() {return mainGui;}
    public void setMainGui(MainGui mainGui) {this.mainGui = mainGui;}

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public Boolean estConnecter(){
        return this.socket != null;
    }

    public List<UtilisateursConversations> getLesConversations() {return lesConversations;}

    public void setLesConversations(List<UtilisateursConversations> lesConversations) {this.lesConversations = lesConversations;}

    public void addConversation(UtilisateursConversations utilisateursConversations){
        this.lesConversations.add(utilisateursConversations);
    }

    public void removEonversation(UtilisateursConversations utilisateursConversations){
        this.lesConversations.remove(utilisateursConversations);
    }

    public void addMessage(Message message){
        for(UtilisateursConversations conv : lesConversations){
            if(conv.getId().getConversations().getConversationId().equals(message.getConversationId())){
                conv.getId().getConversations().addMessage(message);
                break;
            }
        }
    }

    public void removeMessage(Message message){
        for(UtilisateursConversations conv : lesConversations){
            if(conv.getId().getConversations().getConversationId().equals(message.getConversationId())){
                conv.getId().getConversations().removeMessage(message);
                break;
            }
        }
    }

}
