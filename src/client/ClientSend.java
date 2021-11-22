package client;

import common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {

    private ObjectOutputStream out;
    private Socket socket;
    private Client client;

    public ClientSend(Socket socket, ObjectOutputStream out, Client client) throws IOException {
        this.socket = socket;
        this.out = out;
        this.client  = client;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void ClientSend(Socket socket, ObjectOutputStream out){

    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print("Votre message >> ");
            String m = sc.nextLine();
            Message mess = new Message("client",m);
            try {
                if("exit".equals(m.toLowerCase())){
                    client.disconnectedServer();
                }
                    out.writeObject(mess);
                    out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
