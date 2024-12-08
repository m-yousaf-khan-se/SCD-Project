package MainClass.scdprojectupdated;

import controllers.ViewIController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DiagramModel;
import models.classdiagram.*;
import models.classdiagram.Class;
import models.usecase.Actor;
import models.usecase.Extend;
import models.usecase.Include;
import models.usecase.UseCase;
import presenter.classDiagramPresenters.ClassDiagramPresenter;
import presenter.useCaseDiagramPresenters.UseCaseDiagramPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMain extends Application {

    private static Scene mainScene;
    private static ViewIController parentView;

    @Override
    public void start(Stage stage) throws IOException {
        // Load welcomeView first

        FXMLLoader welcomeLoader = new FXMLLoader(ApplicationMain.class.getResource("Views/welcomeView.fxml"));
        Scene welcomeScene = new Scene(welcomeLoader.load());
        stage.setScene(welcomeScene);
        stage.setTitle("Welcome to UML Editor");
        stage.show();

        // Use PauseTransition to display welcomeView for 6 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(event -> {
            try {
                // Load actual view after the delay
                FXMLLoader actualLoader = new FXMLLoader(ApplicationMain.class.getResource("Views/view.fxml"));
                mainScene = new Scene(actualLoader.load());
                parentView = actualLoader.getController();

                if (parentView != null) {
                    System.out.println("Parent View is Initialized Successfully");
                }

                // Setting up MVP
                initializeMVP();
                parentView.setOwnerWindow(stage);

                // Set the actual view
                stage.setScene(mainScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pause.play();
    }

    private void initializeMVP() {
        DiagramModel model = new DiagramModel();
        models.classdiagram.Class clazz = new models.classdiagram.Class();
        models.classdiagram.Aggregation aggregation = new models.classdiagram.Aggregation();
        models.classdiagram.Association association = new models.classdiagram.Association();
        models.classdiagram.generalization generalizations = new models.classdiagram.generalization();
        models.classdiagram.Inherritance inherritance = new models.classdiagram.Inherritance();
        List<Class> classes = new ArrayList<>();

        // Use Case Diagram Presenter
        models.usecase.Actor actor = new Actor();
        models.usecase.UseCase useCase = new UseCase();
        models.usecase.Include include = new Include();
        models.usecase.Extend extend = new Extend();
        models.usecase.Association associations = new models.usecase.Association();
        List<Actor> actors = new ArrayList<>();
        List<UseCase> useCases = new ArrayList<>();

        if (parentView != null) {
            ClassDiagramPresenter classDiagramPresenter = new ClassDiagramPresenter(model, clazz, aggregation, association, generalizations, inherritance, classes, parentView);

            UseCaseDiagramPresenter useCaseDiagramPresenter = new UseCaseDiagramPresenter(model, actor, useCase, include, extend, associations, actors, useCases, parentView);

            if (classDiagramPresenter == null || useCaseDiagramPresenter == null) {
                System.out.println("One of the Presenters is not properly initialized!");
            } else {
                System.out.println("Presenters initialized Successfully!");
            }

            parentView.setPresenter(classDiagramPresenter, useCaseDiagramPresenter);
        } else {
            System.err.println("parentView is null. Ensure the FXML file is loaded correctly.");
        }
    }

    public static Scene getScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
