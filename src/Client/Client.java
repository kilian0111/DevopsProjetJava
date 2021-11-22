package Client;

import Common.Message;
import Interface.ClientPanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Locale;

public class Client {

    private int port;
    private String address;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ClientPanel view;


    public Client(int port, String address) throws IOException {
        this.port = port;
        this.address = address;
        this.socket = new Socket(this.address,this.port);
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        Thread threadSend = new Thread(new ClientSend(this.socket, this.out, this));
        Thread threadReceive = new Thread(new ClientReceive(this,this.socket));
        //threadSend.start();
        threadReceive.start();
    }

    public void disconnectedServer() throws IOException {
        if(this.out != null){
            this.out.close();
        }
        if(this.in != null){
            this.in.close();
        }
        if(this.socket != null){
            this.socket.close();
        }
        System.exit(0);
    }

    public Message messageReceived(Message mess){
        System.out.println("Sender : " + mess.getSender() + ", Content : " + mess.getContent());
        view.printNewMessage(mess);
        return mess;
    }

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

    public ClientPanel getView() {
        return view;
    }

    public void setView(ClientPanel view) {
        this.view = view;
    }

    public void sendMessage(Message m){
        try {
            if("exit".equals(m.getContent().toLowerCase())){
                this.disconnectedServer();
            }
            out.writeObject(m);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
