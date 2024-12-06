package MainClass.scdprojectupdated;

import controllers.ViewIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.DiagramModel;
import models.classdiagram.*;
import models.classdiagram.Aggregation;
import models.classdiagram.Class;
import models.usecase.Actor;
import models.usecase.Extend;
import models.usecase.Include;
import models.usecase.UseCase;
import presenter.classDiagramPresenters.ClassDiagramPresenter;
import presenter.useCaseDiagramPresenters.UseCaseDiagramPresenter;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMain extends Application {

    private static Scene mainScene;
    private ViewIController parentView;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Views/view.fxml"));

        mainScene = new Scene(fxmlLoader.load());
        parentView = (ViewIController) fxmlLoader.getController();

        if(parentView != null)
            System.out.println("Parent View is Initialized Successfully");


        //Setting up MVP
        //------------------------------------------------------------------
        //For Class Diagram Prsenter
        DiagramModel model = new DiagramModel();
        Class clazz=new Class();
        Aggregation aggregation=new Aggregation();
        Association association=new Association();
        generalization generalizations=new generalization();
        Inherritance inherritance=new Inherritance();
        List<Class> classes = new ArrayList<>();

        //Use Case Diagram Presenter
        ViewIController view;
        models.usecase.Actor actor=new Actor();
        models.usecase.UseCase useCase=new UseCase();
        models.usecase.Include include=new Include();
        models.usecase.Extend extend=new Extend();
        models.usecase.Association associations=new models.usecase.Association();
        List<Actor> actors = new ArrayList<>();
        List<UseCase> useCases = new ArrayList<>();
        models.usecase.UseCaseDiagram useCaseDiagram;


        while(true)
        {
            if (parentView != null) {
                ClassDiagramPresenter classDiagramPresenter = new ClassDiagramPresenter(model,clazz,aggregation,association,generalizations,inherritance,classes,parentView);


                UseCaseDiagramPresenter useCaseDiagramPresenter = new UseCaseDiagramPresenter(model,actor,useCase,include,extend,associations,actors,useCases,parentView);

                if(classDiagramPresenter == null || useCaseDiagramPresenter == null)
                {
                    System.out.println("One of the Presenter in not properly initialized!");
                }
                else
                {
                    System.out.println("Presenters initialized Successfully!");
                }

                parentView.setPresenter(classDiagramPresenter, useCaseDiagramPresenter);

                break;
            } else {
                System.err.println("parentView is null. Ensure the FXML file is loaded correctly.");
            }
        }


        //-------------------------------------------------------------------



        stage.setTitle("UML Editor");
        stage.setScene(mainScene);
        stage.show();// iam imran
    }

    public static Scene getScene()
    {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}