package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class useCaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane useCasePane;

    @FXML
    void initialize() {
        assert useCasePane != null : "fx:id=\"useCasePane\" was not injected: check your FXML file 'useCasePane.fxml'.";


    }

}