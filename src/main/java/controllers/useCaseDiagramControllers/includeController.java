package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class includeController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane includePane;

    @FXML
    void initialize() {
        assert includePane != null : "fx:id=\"includePane\" was not injected: check your FXML file 'include.fxml'.";

    }

}
