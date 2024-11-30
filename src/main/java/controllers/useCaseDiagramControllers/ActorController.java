package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import controllers.Controller;

public class ActorController implements Controller {


    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField actorNameTextField;

    @FXML
    private URL location;

    @FXML
    private Pane ActorPane;

    @FXML
    void initialize() {
        assert ActorPane != null : "fx:id=\"ActorPane\" was not injected: check your FXML file 'actor.fxml'.";

        // Add an action listener for the TextField
        actorNameTextField.setOnAction(event -> {
            String inputText = actorNameTextField.getText();
            System.out.println("Entered ActorName: " + inputText);


        });

    }

}
