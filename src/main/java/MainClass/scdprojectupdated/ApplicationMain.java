package MainClass.scdprojectupdated;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {

    private static Scene mainScene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("Views/view.fxml"));
        mainScene = new Scene(fxmlLoader.load());
        stage.setTitle("UML Editor");
        stage.setScene(mainScene);
        stage.show();
    }

    public static Scene getScene()
    {
        return mainScene;
    }

    public static void main(String[] args) {
        launch();
    }
}