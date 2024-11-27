package controllers.classDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UMLClassController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addFieldBtn;

    @FXML
    private Button addMethodBtn;

    @FXML
    private TextField classNameField;

    @FXML
    private VBox classVBox;

    @FXML
    private TextField methodNameField;

    @FXML
    private TextField variableNameField;

    @FXML
    void addNewFieldListener(ActionEvent event) {

    }

    @FXML
    void addNewMethodListener(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert addFieldBtn != null : "fx:id=\"addFieldBtn\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert addMethodBtn != null : "fx:id=\"addMethodBtn\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert classNameField != null : "fx:id=\"classNameField\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert classVBox != null : "fx:id=\"classVBox\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert methodNameField != null : "fx:id=\"methodNameField\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert variableNameField != null : "fx:id=\"variableNameField\" was not injected: check your FXML file 'UMLclass.fxml'.";

    }

}

//    @FXML
//    void addNewField(ActionEvent event) {
//        int newFieldindex = classVBox.getChildren().indexOf(addFieldBtn);
//        classVBox.getChildren().add()
//    }


//______________________change listener for fields:
//// Create multiple TextFields
//TextField textField1 = new TextField();
//TextField textField2 = new TextField();
//TextField textField3 = new TextField();
//
//// Create a ChangeListener for the text change event
//ChangeListener<String> textChangeListener = (observable, oldValue, newValue) -> {
//    TextField sourceField = (TextField) observable.getBean();  // Get the source of the change
//    System.out.println("Text changed in: " + sourceField.getId());
//    System.out.println("Old Value: " + oldValue);
//    System.out.println("New Value: " + newValue);
//};
//
//// Add the listener to multiple TextFields
//        textField1.textProperty().addListener(textChangeListener);
//        textField2.textProperty().addListener(textChangeListener);
//        textField3.textProperty().addListener(textChangeListener);
//
//// Set unique IDs to each TextField for identification
//        textField1.setId("TextField1");
//        textField2.setId("TextField2");
//        textField3.setId("TextField3");
//
//// Create a layout and add the TextFields to it
//javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(10);
//        layout.getChildren().addAll(textField1, textField2, textField3);