package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;

public class ViewController implements Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane MainViewRightBorderArea;

    @FXML
    private static Group groupCanvas;

    @FXML
    void aboutUMLEditorListener(ActionEvent event) {

    }

    @FXML
    void createNewClassDiagramListener(ActionEvent event) {

    }

    @FXML
    void createNewPackageDiagramListener(ActionEvent event) {

    }

    @FXML
    void createNewUseCaseDiagramListener(ActionEvent event) {

    }

    @FXML
    void exportAsPNGListener(ActionEvent event) {

    }

    @FXML
    void loadExistingProject(ActionEvent event) {

    }

    @FXML
    void saveProjectListener(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert MainViewRightBorderArea != null : "fx:id=\"MainViewRightBorderArea\" was not injected: check your FXML file 'view.fxml'.";
        assert groupCanvas != null : "fx:id=\"groupCanvas\" was not injected: check your FXML file 'view.fxml'.";
    }

    public static Group getGroupCanvas()
    {
        return groupCanvas;
    }
}