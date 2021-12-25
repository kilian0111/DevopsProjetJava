package main.java.client.gui;




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/connection.fxml").toURI().toURL());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Kijoki");
        stage.getIcons().add(new Image("file:src/main/resources/img/favicon.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}