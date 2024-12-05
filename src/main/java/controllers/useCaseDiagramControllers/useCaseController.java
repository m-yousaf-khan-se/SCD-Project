package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.ControllerClass;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public abstract class useCaseController extends ControllerClass {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane useCasePane;
    @FXML
    private TextField useCaseTextField;

    @FXML
    @Override
    public void initialize() {
        assert useCasePane != null : "fx:id=\"useCasePane\" was not injected: check your FXML file 'useCasePane.fxml'.";

        assert useCaseTextField != null : "fx:id=\"useCaseTextField\" was not injected: check your FXML file 'useCasePane.fxml'.";

        // Add an action listener for the TextField
        useCaseTextField.setOnAction(event -> {
            String inputText = useCaseTextField.getText();
            System.out.println("Entered UseCase Text: " + inputText);

            // Optional: Clear the text field after pressing Enter
           // useCaseTextField.clear();
        });

        //Use case textField is working properly (after typing any input just press enter then input will be accessed through above function)
    }

}

