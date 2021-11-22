package gui;

import client.Client;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGui extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        ClientPanel clientPanel = new ClientPanel();
        Group root = new Group();
        root.getChildren().add(clientPanel);
        Scene scene = new Scene(root, 450, 500);
        stage.setTitle("Mon application");
        stage.setScene(scene);
        stage.show();
        try {

            String address = "127.0.0.1";
            int port = 1111;
            Client c = new Client(port, address);
            clientPanel.setClient(c);
            c.setView(clientPanel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(MainGui.class,args);
    }
}

