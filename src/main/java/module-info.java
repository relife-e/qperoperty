module com.mycompany.qproperty {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.qproperty to javafx.fxml;
    opens com.mycompany.qproperty.controller to javafx.fxml;
    exports com.mycompany.qproperty;
    exports com.mycompany.qproperty.controller;
    exports com.mycompany.qproperty.model;   
}
