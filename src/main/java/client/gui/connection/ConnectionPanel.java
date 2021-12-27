package main.java.client.gui.connection;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.client.MainGui;
import main.java.common.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ConnectionPanel extends Parent {

    //création des différents attributs de ConnectionPanel
    private Button connectionBtn;
    private Button registerBtn;
    private TextField username;
    private PasswordField password;
    private Label usernameTxt;
    private Label passwordTxt;
    private Image logoIcon;

    public ConnectionPanel()
    {
        //initialisation des attributs
        this.connectionBtn = new Button("Connexion");
        this.registerBtn = new Button("Inscription");
        this.usernameTxt = new Label("Nom d'utilisateur : ");
        this.passwordTxt = new Label("Mot de passe : ");
        this.username = new TextField();
        this.password = new PasswordField();
        //instanciation du logo
        try {
            this.logoIcon = new Image(new FileInputStream(Utils.getResourcesPath()+"img/logo.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView logoView = new ImageView(logoIcon);

        //mise en place du logo
        logoView.setX(20);
        logoView.setY(20);
        logoView.setFitHeight(175);
        logoView.setFitWidth(400);
        logoView.setVisible(true);

        this.connectionBtn.setLayoutX(180);
        this.connectionBtn.setLayoutY(425);
        this.connectionBtn.setPrefWidth(100);
        this.connectionBtn.setPrefHeight(20);
        this.connectionBtn.setVisible(true);

        this.registerBtn.setLayoutX(180);
        this.registerBtn.setLayoutY(475);
        this.registerBtn.setPrefWidth(100);
        this.registerBtn.setPrefHeight(20);
        this.registerBtn.setVisible(true);

        this.usernameTxt.setPrefHeight(30);
        this.usernameTxt.setPrefWidth(150);
        this.usernameTxt.setLayoutX(130);
        this.usernameTxt.setLayoutY(200);
        this.usernameTxt.setVisible(true);

        this.passwordTxt.setPrefHeight(30);
        this.passwordTxt.setPrefWidth(100);
        this.passwordTxt.setLayoutX(130);
        this.passwordTxt.setLayoutY(280);
        this.passwordTxt.setVisible(true);

        this.password.setPrefWidth(200);
        this.password.setLayoutX(130);
        this.password.setLayoutY(310);
        this.password.setVisible(true);

        this.username.setPrefWidth(200);
        this.username.setLayoutX(130);
        this.username.setLayoutY(230);
        this.username.setVisible(true);

        this.getChildren().add(logoView);
        this.getChildren().add(registerBtn);
        this.getChildren().add(connectionBtn);
        this.getChildren().add(usernameTxt);
        this.getChildren().add(passwordTxt);
        this.getChildren().add(username);
        this.getChildren().add(password);

        this.connectionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        this.registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ConnectionPanel.this.setVisible(false);
                //MainGui.getRegisterPanel().setVisible(true);
            }
        });
    }
}
