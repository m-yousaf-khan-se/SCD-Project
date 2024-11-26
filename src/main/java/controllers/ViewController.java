package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MainClass.scdprojectupdated.ApplicationMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;

public class ViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group groupCanvas;

    @FXML
    void aboutUMLEditor(ActionEvent event) {

    }

    @FXML
    void createAggregation(ActionEvent event) {

    }

    @FXML
    void createAssociation(ActionEvent event) {

    }

    @FXML
    void createClass(ActionEvent event) throws IOException {
        System.out.println("Creating empty class");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/UMLclass.fxml"));
        groupCanvas.getChildren().add(((Group)loader.load()));
    }

    @FXML
    void createComposition(ActionEvent event) {

    }

    @FXML
    void createGeneralization(ActionEvent event) {

    }

    @FXML
    void createNewProject(ActionEvent event) {

    }

    @FXML
    void exportAsPNG(ActionEvent event) {

    }

    @FXML
    void loadExistingProject(ActionEvent event) {

    }

    @FXML
    void saveProject(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
