package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MainClass.scdprojectupdated.ApplicationMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Group groupCanvas;

    private static ViewController instance; //using sigleton pattern

    @FXML
    void aboutUMLEditorListener(ActionEvent event) {

    }

    @FXML
    void createNewClassDiagramListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/ClassDiagramTools.fxml"));
        MainViewRightBorderArea.setContent(loader.load());
    }

    @FXML
    void createNewUseCaseDiagramListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/UseCaseDiagramsTools.fxml"));
        MainViewRightBorderArea.setContent(loader.load());
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
        //Actually this code is loaded from the scene builder sample Controller. And I don't why I get these two line below.
        assert MainViewRightBorderArea != null : "fx:id=\"MainViewRightBorderArea\" was not injected: check your FXML file 'view.fxml'.";
        assert groupCanvas != null : "fx:id=\"groupCanvas\" was not injected: check your FXML file 'view.fxml'.";

        instance = this;
    }

    public static Group getGroupCanvas() {
        if (instance == null) {
            throw new IllegalStateException("ViewController has not been initialized yet.");
        }
        return instance.groupCanvas;
    }


}