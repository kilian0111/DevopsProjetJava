package gui.connection;

import client.Client;
import common.Message;
import gui.MainGui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class ConnectionPanel extends Parent {

    //création des différents attributs de ConnectionPanel
    private Button connectionBtn;
    private Button registerBtn;
    private TextField username;
    private PasswordField password;
    private Label usernameTxt;
    private Label passwordTxt;
    private WebView logo;

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
        try
        {
            this.logo = new WebView();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //mise en place du logo
        logo.setLayoutX(10);
        logo.setLayoutY(500);
        logo.setPrefHeight(200);
        logo.setPrefWidth(200);
        logo.setVisible(true);

        this.connectionBtn.setLayoutX(180);
        this.connectionBtn.setLayoutY(375);
        this.connectionBtn.setPrefWidth(100);
        this.connectionBtn.setPrefHeight(20);
        this.connectionBtn.setVisible(true);

        this.registerBtn.setLayoutX(180);
        this.registerBtn.setLayoutY(425);
        this.registerBtn.setPrefWidth(100);
        this.registerBtn.setPrefHeight(20);
        this.registerBtn.setVisible(true);

        this.usernameTxt.setPrefHeight(30);
        this.usernameTxt.setPrefWidth(150);
        this.usernameTxt.setLayoutX(130);
        this.usernameTxt.setLayoutY(100);
        this.usernameTxt.setVisible(true);

        this.passwordTxt.setPrefHeight(30);
        this.passwordTxt.setPrefWidth(100);
        this.passwordTxt.setLayoutX(130);
        this.passwordTxt.setLayoutY(200);
        this.passwordTxt.setVisible(true);

        this.password.setPrefWidth(200);
        this.password.setLayoutX(130);
        this.password.setLayoutY(230);
        this.password.setVisible(true);

        this.username.setPrefWidth(200);
        this.username.setLayoutX(130);
        this.username.setLayoutY(130);
        this.username.setVisible(true);

        this.getChildren().add(logo);
        this.logo.getEngine().load("ressource/img/logo.svg");

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
                MainGui.getRegisterPanel().setVisible(true);
            }
        });
    }
}
