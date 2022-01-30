package main.java.client.gui.listcell;

import javafx.scene.control.ListCell;
import main.java.common.UserSafeData;

public class UserListCell extends ListCell<UserSafeData> {

    @Override
    protected void updateItem(UserSafeData item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (!empty && item != null) {
            final String text = String.format("%s", item.getPseudo());
            setText(text);
        }
    }

}
