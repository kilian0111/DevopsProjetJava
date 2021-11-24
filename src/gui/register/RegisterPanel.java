package gui.register;

import gui.MainGui;
import gui.connection.ConnectionPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class RegisterPanel extends Parent {

    private Button confirmBtn;
    private Button cancelBtn;

    private TextField username;
    private Label usernameTxt;

    private PasswordField password;
    private Label passwordTxt;

    private PasswordField confirmPassword;
    private Label confirmPasswordTxt;

    private TextField firstName;
    private Label firstNameTxt;

    private TextField lastName;
    private Label lastNameTxt;

    private TextField email;
    private Label emailTxt;

    private DatePicker birthdate;
    private Label birthdateTxt;

    private Label genderTxt;
    private RadioButton maleBtn;
    private RadioButton femaleBtn;
    final ToggleGroup genderGroup = new ToggleGroup();

    public RegisterPanel()
    {

        this.usernameTxt = new Label("Nom d'utilisateur : ");
        this.username = new TextField();

        this.passwordTxt = new Label("Mot de passe : ");
        this.password = new PasswordField();

        this.confirmPasswordTxt = new Label("Confirmer votre mot de passe : ");
        this.confirmPassword = new PasswordField();

        this.emailTxt = new Label("Adresse électronique : ");
        this.email = new TextField();

        this.birthdateTxt = new Label("Date de naissance : ");
        this.birthdate = new DatePicker();

        this.genderTxt = new Label("Sexe : ");
        this.maleBtn = new RadioButton("Homme");
        maleBtn.setToggleGroup(genderGroup);
        this.femaleBtn = new RadioButton("Femme");
        femaleBtn.setToggleGroup(genderGroup);

        this.lastNameTxt = new Label ("Nom de naissance : ");
        this.lastName = new TextField();

        this.firstNameTxt = new Label ("Prénom :");
        this.firstName = new TextField();

        this.confirmBtn = new Button("Confirmer");
        this.cancelBtn = new Button("Annuler");

        this.confirmBtn.setLayoutX(80);
        this.confirmBtn.setLayoutY(425);
        this.confirmBtn.setPrefWidth(100);
        this.confirmBtn.setPrefHeight(20);
        this.confirmBtn.setVisible(true);

        this.cancelBtn.setLayoutX(200);
        this.cancelBtn.setLayoutY(425);
        this.cancelBtn.setPrefWidth(100);
        this.cancelBtn.setPrefHeight(20);
        this.cancelBtn.setVisible(true);

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

        this.getChildren().add(confirmBtn);
        this.getChildren().add(cancelBtn);
        this.getChildren().add(usernameTxt);
        this.getChildren().add(passwordTxt);
        this.getChildren().add(username);
        this.getChildren().add(password);

        this.confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        this.cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RegisterPanel.this.setVisible(false);
                MainGui.getConnectionPanel().setVisible(true);
            }
        });
    }
}
