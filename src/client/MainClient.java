package client;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) {
        try {

                String address = args[0];
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

