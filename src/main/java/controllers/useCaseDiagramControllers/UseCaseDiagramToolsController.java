package controllers.useCaseDiagramControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MainClass.scdprojectupdated.ApplicationMain;
import controllers.Controller;
import controllers.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class UseCaseDiagramToolsController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void createActorListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/actor.fxml"));
        ViewController.getGroupCanvas().getChildren().add(loader.load());
    }

    @FXML
    void createExtendListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/extend.fxml"));
        ViewController.getGroupCanvas().getChildren().add(loader.load());
    }

    @FXML
    void createAssociationListener(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/association.fxml"));
        ViewController.getGroupCanvas().getChildren().add(loader.load());
    }

    @FXML
    void createIncludeListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/include.fxml"));
        ViewController.getGroupCanvas().getChildren().add(loader.load());
    }

    @FXML
    void createMultiplicityListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/multiplicity.fxml"));
        ViewController.getGroupCanvas().getChildren().add(loader.load());
    }

    @FXML
    void createUseCaseLIstener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/useCasePane.fxml"));
        ViewController.getGroupCanvas().getChildren().add(loader.load());

    }

    @FXML
    void initialize() {

    }

}
