package controllers.classDiagramControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MainClass.scdprojectupdated.ApplicationMain;
import controllers.Controller;
import controllers.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class ClassDiagramToolsController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void createAggregationListener(ActionEvent event) {

    }

    @FXML
    void createAssociationListener(ActionEvent event) {

    }

    @FXML
    void createClassListener(ActionEvent event) throws IOException {
        //code of creating class
        System.out.println("Creating empty class");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/UMLclass.fxml"));
        ViewController.getGroupCanvas().getChildren().add((loader.load()));
    }

    @FXML
    void createCompositionListener(ActionEvent event) {

    }

    @FXML
    void createGeneralizationListener(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}