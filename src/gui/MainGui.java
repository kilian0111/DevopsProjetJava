package gui;

import client.Client;
import gui.connection.ConnectionPanel;
import gui.register.RegisterPanel;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.Connection;

import java.io.IOException;

public class MainGui extends Application{

    private static ConnectionPanel connectionPanel;
    private static RegisterPanel registerPanel;

    @Override
    public void start(Stage stage) throws IOException {
        connectionPanel = new ConnectionPanel();
        registerPanel = new RegisterPanel();
        Group root = new Group();
        root.getChildren().add(connectionPanel);
        root.getChildren().add(registerPanel);
        registerPanel.setVisible(false);
        Scene scene = new Scene(root, 450, 600);
        stage.setTitle("Kijoki");
        stage.getIcons().add(new Image("file:ressources/img/favicon.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static ConnectionPanel getConnectionPanel() {
        return connectionPanel;
    }

    public static RegisterPanel getRegisterPanel() {
        return registerPanel;
    }

    public static void main(String[] args) {
        Application.launch(MainGui.class,args);
    }
}

