package client;

import database.DataBaseConnection;

import java.io.IOException;
import java.sql.Connection;

public class MainClient {

    public static void main(String[] args) {
        try {
            DataBaseConnection db = new DataBaseConnection();
            Connection co = db.getConnection();
            String address = "127.0.0.1";
                int port = 1111;
                Client c = new Client(port, address);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void printUsage() {
        System.out.println("java client.Client <address> <port>");
        System.out.println("\t<address>: server's ip address");
        System.out.println("\t<port>: server's port");
    }
}

