package main.java.server;



import main.java.common.Action;
import main.java.common.Message;
import main.java.common.ObjectSend;
import main.java.common.User;
import main.java.common.repository.UserJpaRepository;
import main.java.database.DataBaseConnectionRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {
    private static int idCounter;
    private int id;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Server server;

    private User user;

    public ConnectedClient(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.id = idCounter;
        idCounter++;
        System.out.println("Nouvelle connexion, id = " + id);
    }

    public static int getIdCounter() {return idCounter;}
    public static void setIdCounter(int idCounter) {ConnectedClient.idCounter = idCounter;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public Socket getSocket() {return socket;}
    public void setSocket(Socket socket) {this.socket = socket;}

    public ObjectOutputStream getOut() {return out;}
    public void setOut(ObjectOutputStream out) {this.out = out;}

    public ObjectInputStream getIn() {return in;}
    public void setIn(ObjectInputStream in) {this.in = in;}

    public Server getServer() {return server;}
    public void setServer(Server server) {this.server = server;}

    @Override
    public void run() {
       try {
            this.in = new ObjectInputStream(this.socket.getInputStream());
            boolean isActive = true;
            while(isActive){
                Object object =  this.in.readObject();
                if(object != null ){
                   if(object instanceof ObjectSend){
                       ObjectSend objectSend = (ObjectSend) object;
                       if(objectSend.getAction() == Action.CONNECTION && objectSend.getObject() instanceof User){
                           this.connexionClient((User) objectSend.getObject());
                       } else if(objectSend.getAction() == Action.INSCRIPTION && objectSend.getObject() instanceof User ){
                           this.inscription((User) objectSend.getObject());
                       }
                   }
                }else{
                    server.disconnectedClient(this);
                    isActive = false;
                }
            }
        } catch (IOException e) {
            server.disconnectedClient(this);
        } catch(Exception e ){
            e.printStackTrace();
        }
    }

    private User inscription(User user) throws IOException {
        //User userReturn = this.dataBaseConnectionRequest.inscription(user);
        this.out.writeObject(null);
        this.out.flush();
        return null;
    }

    public void closeClient() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();

    }

    public void connexionClient(User user) throws IOException {
        ObjectSend object = new ObjectSend(UserJpaRepository.seConnecter(user.getPseudo(), user.getMdp()), Action.CONNECTION);
        this.sendToClient(object);
    }

    public void sendToClient(ObjectSend object) throws IOException {
        this.out.writeObject(object);
        this.out.flush();
    }

    public Message sendMessage(Message mess) throws IOException {
        this.out.writeObject(mess);
        this.out.flush();
        return mess;
    }



}
