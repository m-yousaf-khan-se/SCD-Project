package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import controllers.Controller;

public class ActorController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane ActorPane;

    @FXML
    void initialize() {
        assert ActorPane != null : "fx:id=\"ActorPane\" was not injected: check your FXML file 'actor.fxml'.";

    }

}
