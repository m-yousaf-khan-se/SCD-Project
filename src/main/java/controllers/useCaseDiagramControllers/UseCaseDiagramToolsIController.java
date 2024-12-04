package controllers.useCaseDiagramControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import MainClass.scdprojectupdated.ApplicationMain;
import controllers.IController;
import controllers.ViewIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class UseCaseDiagramToolsIController implements IController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void createActorListener(ActionEvent event) throws IOException {
        System.out.println("Creating Actor");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/actor.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());
    }

    @FXML
    void createExtendListener(ActionEvent event) throws IOException {
        System.out.println("Creating Extend Link");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/extend.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());
    }

    @FXML
    void createAssociationListener(ActionEvent event) throws IOException {

        System.out.println("Creating Association Link");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/association.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());
    }

    @FXML
    void createIncludeListener(ActionEvent event) throws IOException {
        System.out.println("Creating Include Link");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/include.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());

    }


    @FXML
    void createUseCaseLIstener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/useCasePane.fxml"));
        ViewIController.getPaneCanvas().getChildren().add(loader.load());

    }

    @FXML
    void initialize() {

    }

}
