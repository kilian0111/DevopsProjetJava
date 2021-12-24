package main.java.client.gui;



import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.client.gui.connection.ConnectionPanel;
import main.java.client.gui.register.RegisterPanel;

import java.io.IOException;

public class MainGui extends Application {

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
        stage.getIcons().add(new Image("file:src/main/resources/img/favicon.png"));
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

