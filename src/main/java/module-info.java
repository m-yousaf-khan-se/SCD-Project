module main.scdprojectupdated {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens MainClass.scdprojectupdated to javafx.fxml;
    exports MainClass.scdprojectupdated;
    exports controllers;
    opens controllers to javafx.fxml;
}