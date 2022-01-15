package main.java.server;

import main.java.common.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainServer {

    public static void main(String[] args) {

        Properties props = new Properties();
        System.out.println(System.getProperty("user.dir"));
        try(FileInputStream conf = new FileInputStream(Utils.getResourcesPath()+"conf.properties")) {

            props.load(conf);
            Class.forName(props.getProperty("jdbc.driver.class"));
            Integer port = Integer.parseInt(props.getProperty("server.port"));
            Server server = new Server(port);
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }


    private static void printUsage() {
        System.out.println("java server.Server <port>");
        System.out.println("\t<port>: server's port");
    }
}
