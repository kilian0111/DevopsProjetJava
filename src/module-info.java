module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens main.java.client.gui.controlleur to javafx.fxml,javafx.graphics,java.base,java.persistence,org.hibernate.orm.core;
    exports main.java.client.gui.controlleur to javafx.graphics, javafx.fxml,java.base,java.persistence,org.hibernate.orm.core;
    opens main.java.client to javafx.fxml, javafx.graphics, java.sql,java.base,java.persistence,org.hibernate.orm.core;
    exports main.java.client to javafx.graphics, javafx.fxml,java.base,java.persistence,org.hibernate.orm.core;
    opens main.java.common.repository to javafx.fxml, javafx.graphics, java.sql,java.base,java.persistence,org.hibernate.orm.core;
    exports main.java.common.repository to javafx.graphics, javafx.fxml,java.base,java.persistence,org.hibernate.orm.core;
    exports main.java.common to  org.hibernate.orm.core;
    // JAVA VM OPTION
    //--module-path src/main/resources/lib/javafx-sdk-17.0.1/lib;out/production;src/main/resources/lib/hibernate/required --add-modules=javafx.controls

}