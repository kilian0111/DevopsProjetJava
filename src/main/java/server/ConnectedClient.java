package main.java.server;



import main.java.common.Message;
import main.java.common.User;
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
    private DataBaseConnectionRequest dataBaseConnectionRequest;
    private User user;

    public ConnectedClient(Socket socket, Server server) throws IOException {
        dataBaseConnectionRequest = new DataBaseConnectionRequest();
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
                   if(object instanceof User){
                       User user = connexionClient((User) object);
                       this.user = user;
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

    public void closeClient() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();

    }

    public User connexionClient(User user) throws IOException {
        User userReturn = this.dataBaseConnectionRequest.seConnecter(user.getPseudo(), user.getMdp());
        this.out.writeObject(userReturn);
        this.out.flush();
        return user;
    }

    public Message sendMessage(Message mess) throws IOException {
        this.out.writeObject(mess);
        this.out.flush();
        return mess;
    }



}
