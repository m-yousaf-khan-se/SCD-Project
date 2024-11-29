module main.scdprojectupdated {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens MainClass.scdprojectupdated to javafx.fxml;
    exports MainClass.scdprojectupdated;
    exports controllers;
    opens controllers to javafx.fxml;
    exports controllers.classDiagramControllers;
    opens controllers.classDiagramControllers to javafx.fxml;
    exports controllers.useCaseDiagramControllers;
    opens controllers.useCaseDiagramControllers to javafx.fxml;
}