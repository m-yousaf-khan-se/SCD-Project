package MainClass.scdprojectupdated;

import controllers.ViewIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.DiagramModel;
import models.IModel;
import models.classdiagram.*;
import models.classdiagram.Class;
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
        DiagramModel model = new DiagramModel();
        Class clazz=new Class();
        Aggregation aggregation=new Aggregation();
        Association association=new Association();
        generalization generalizations=new generalization();
        Inherritance inherritance=new Inherritance();
        List<Class> classes = new ArrayList<>();


        /* @Saad is ki jaga dakh lena tum na kon kon sa model kis presenter ma use kerna hai
                                                wo wo model define ker k relavent Presetner ma pass ker dena*/

        while(true)
        {
            if (parentView != null) {
                ClassDiagramPresenter classDiagramPresenter = new ClassDiagramPresenter(model,clazz,aggregation,association,generalizations,inherritance,classes,parentView);

                // or imran tum bhi mera is presnter ko set ker lena jasa mena oper wala ko kia hai taak saad is per bhi kaam ker saka.
                UseCaseDiagramPresenter useCaseDiagramPresenter = new UseCaseDiagramPresenter();

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