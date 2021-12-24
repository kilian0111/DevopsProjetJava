module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires java.sql;

    opens main.java.client.gui.controlleur to javafx.fxml,javafx.graphics;
    opens main.java.client.gui to javafx.fxml, javafx.graphics, java.sql;
    exports main.java.client.gui to javafx.graphics, javafx.fxml;
    exports main.java.client.gui.controlleur to javafx.graphics, javafx.fxml;


}