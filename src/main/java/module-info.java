module main.scdprojectupdated {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing; // This allows access to javafx.embed.swing



    requires com.fasterxml.jackson.databind;
    opens models to com.fasterxml.jackson.databind; // Open the entire `models` package for Jackson
    opens models.classdiagram to com.fasterxml.jackson.databind; // Open the `models.classdiagram` package
    opens models.usecase to com.fasterxml.jackson.databind; // Open the models.usecase package
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;


    opens MainClass.scdprojectupdated to javafx.fxml;
    exports MainClass.scdprojectupdated;
    exports controllers;
    opens controllers to javafx.fxml;
    exports controllers.classDiagramControllers;
    opens controllers.classDiagramControllers to javafx.fxml;
    exports controllers.useCaseDiagramControllers;
    opens controllers.useCaseDiagramControllers to javafx.fxml;
}