module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires java.sql;


    opens main.java.client.gui.controlleur to javafx.fxml,javafx.graphics,java.base;
    exports main.java.client.gui.controlleur to javafx.graphics, javafx.fxml,java.base;
    opens main.java.client to javafx.fxml, javafx.graphics, java.sql,java.base;
    exports main.java.client to javafx.graphics, javafx.fxml,java.base;




    // JAVA VM OPTION client
    //--module-path src/main/ressources/lib/javafx-sdk-17.0.1/lib;out/production --add-modules=javafx.controls

}