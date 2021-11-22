package gui;

import client.Client;
import common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;



public class ClientPanel extends Parent {

    private Client client;
    private TextArea textToSend;
    private ScrollPane scrollReceivedText;
    private TextFlow receivedText;
    private Button sendBtn;
    private Button clearBtn;

    public ClientPanel() {
        this.textToSend = new TextArea();
        this.scrollReceivedText = new ScrollPane();
        this.receivedText = new TextFlow();
        this.sendBtn = new Button("Send");
        this.clearBtn = new Button("Clear");


        this.textToSend.setLayoutX(50);
        this.textToSend.setLayoutY(375);
        this.textToSend.setPrefWidth(250);
        this.textToSend.setPrefHeight(75);

        this.scrollReceivedText.setLayoutX(50);
        this.scrollReceivedText.setLayoutY(25);
        this.scrollReceivedText.setPrefWidth(350);
        this.scrollReceivedText.setPrefHeight(325);

        this.receivedText.setPrefWidth(350);
        this.receivedText.setPrefHeight(325);
        this.receivedText.setVisible(true);

        this.sendBtn.setLayoutX(330);
        this.sendBtn.setLayoutY(375);
        this.sendBtn.setPrefWidth(70);
        this.sendBtn.setPrefHeight(20);
        this.sendBtn.setVisible(true);

        this.clearBtn.setLayoutX(330);
        this.clearBtn.setLayoutY(425);
        this.clearBtn.setPrefWidth(70);
        this.clearBtn.setPrefHeight(20);
        this.clearBtn.setVisible(true);


        this.scrollReceivedText.setContent(this.receivedText);
        this.scrollReceivedText.vvalueProperty().bind(this.receivedText.heightProperty());


        this.clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textToSend.setText("");
            }
        });

        this.sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Message m = new Message("moi",textToSend.getText());
                printNewMessage(m);
                textToSend.setText("");
                client.sendMessage(m);


            }
        });

        this.getChildren().add(this.scrollReceivedText);
        this.getChildren().add(textToSend);
        this.getChildren().add(clearBtn);
        this.getChildren().add(sendBtn);
    }

    public void printNewMessage(Message mess) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label text = new Label("\n" + mess.getContent());
                text.setPrefWidth(receivedText.getPrefWidth() - 20);
                text.setAlignment(Pos.CENTER_LEFT);
                receivedText.getChildren().add(text);
            }
        });
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
