package main.java.client.gui.style;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.java.common.Message;
import main.java.common.UtilisateursConversations;

import java.util.List;

public class ConversationListCell extends ListCell<UtilisateursConversations> {

    private final GridPane gridPane = new GridPane();
    private final Label conversationName = new Label();
    private final Label lastMessage = new Label();
    private final AnchorPane content = new AnchorPane();

    public ConversationListCell() {
        conversationName.setStyle("-fx-font-weight: bold;");
        GridPane.setConstraints(conversationName, 1, 0);
        GridPane.setConstraints(lastMessage, 1, 1);
        gridPane.getChildren().setAll(conversationName, lastMessage);
        content.getChildren().add(gridPane);
    }

    @Override
    protected void updateItem(UtilisateursConversations item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (!empty && item != null) {
            List<Message> messages = item.getId().getConversations().getLesMessages();
            conversationName.setText(item.nomConv());
            lastMessage.setText(messages.get(messages.size()-1).getContent());
            setGraphic(content);
            setText(null);
        }
    }

}
