package controllers.classDiagramControllers;

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

public class ClassDiagramToolsIController implements IController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void createAggregationListener(ActionEvent event) throws IOException {
        System.out.println("Creating Aggregation");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/aggregation.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());
    }

    @FXML
    void createAssociationListener(ActionEvent event) throws IOException {
        System.out.println("Creating Association");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/association.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());
    }

    @FXML
    void createClassListener(ActionEvent event) throws IOException {
        //code of creating class
        System.out.println("Creating empty class");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/UMLclass.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());
    }

    @FXML
    void createCompositionListener(ActionEvent event) throws IOException {
        System.out.println("Creating Composition");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/composition.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());
    }

    @FXML
    void createGeneralizationListener(ActionEvent event) throws IOException {
        System.out.println("Creating Generalization");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/generalization.fxml"));
        Parent container = loader.load();
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeController(container, loader.getController());
    }

    @FXML
    void initialize() {

    }

}