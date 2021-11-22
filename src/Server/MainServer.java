package Server;

import java.io.IOException;

public class MainServer {

    public static void main(String[] args) {
        try {

                Integer port = 1111;
                Server server = new Server(port);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    private static void printUsage() {
        System.out.println("java server.Server <port>");
        System.out.println("\t<port>: server's port");
    }
}
