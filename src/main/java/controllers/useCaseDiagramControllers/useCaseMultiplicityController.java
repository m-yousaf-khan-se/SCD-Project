package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class useCaseMultiplicityController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text multiplicityPane;

    @FXML
    void initialize() {
        assert multiplicityPane != null : "fx:id=\"multiplicityPane\" was not injected: check your FXML file 'multiplicity.fxml'.";

    }

}
