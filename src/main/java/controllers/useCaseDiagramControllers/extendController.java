package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class extendController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane extendPane;

    @FXML
    void initialize() {
        assert extendPane != null : "fx:id=\"extendPane\" was not injected: check your FXML file 'extend.fxml'.";

    }

}
