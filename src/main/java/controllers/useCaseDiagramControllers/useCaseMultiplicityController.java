package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class useCaseMultiplicityController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TextField multiplicityTextField;

    @FXML
    void initialize() {
       // assert multiplicityPane != null : "fx:id=\"multiplicityPane\" was not injected: check your FXML file 'multiplicity.fxml'.";

        // Add an action listener for the TextField
        multiplicityTextField.setOnAction(event -> {
            String inputText = multiplicityTextField.getText();
            System.out.println("Entered Multiplicity: " + inputText);


        });

    }

}
