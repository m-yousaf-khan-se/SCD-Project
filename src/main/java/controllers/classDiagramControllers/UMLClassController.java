package controllers.classDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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

    private ContextMenu getRightClickDeleteOption(TextField targetField) {
        ContextMenu rightClickOptions = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");

        // Pass the target field directly to the action listener
        deleteItem.setOnAction(event -> rightClickDeleteOptionListener(event, targetField));

        rightClickOptions.getItems().add(deleteItem);

        return rightClickOptions;
    }

    private void rightClickDeleteOptionListener(ActionEvent event, TextField targetField) {
        if (targetField == null) {
            System.out.println("Error: targetField is null.");
            return;
        }

        VBox parent = (VBox) targetField.getParent();
        if (parent != null) {
            parent.getChildren().remove(targetField);
            System.out.println("TextField deleted: " + targetField.getPromptText());
        } else {
            System.out.println("Error: Parent VBox is null.");
        }
    }

    // Create a ChangeListener to track text changes
    private ChangeListener<String> textChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            // The observable is the textProperty of the TextField, which is the source
            TextField sourceField = (TextField) ((javafx.beans.property.StringProperty) observable).getBean(); // Accessing the source TextField

            // Print text changes for debugging or handling purposes
            System.out.println("Text changed in: " + sourceField.getPromptText());
            System.out.println("Old Value: " + oldValue);
            System.out.println("New Value: " + newValue);
        }
    };

    @FXML
    void addNewFieldListener(ActionEvent event) {
        int newFieldIndex = classVBox.getChildren().indexOf(addFieldBtn);  // Get index of the button
        TextField newField = new TextField();

        newField.setAlignment(Pos.CENTER);
        newField.setFont(Font.font(10));
        newField.setPromptText("Enter Variable Name");

        // Add ChangeListener to dynamically created fields
        newField.textProperty().addListener(textChangeListener);

        // Pass the new field directly to the getRightClickDeleteOption method
        newField.setContextMenu(getRightClickDeleteOption(newField));

        classVBox.getChildren().add(newFieldIndex, newField);  // Add new field
        System.out.println("New empty field added to the class");
    }

    @FXML
    void addNewMethodListener(ActionEvent event) {
        int newFieldIndex = classVBox.getChildren().indexOf(addMethodBtn);  // Get index of the button
        TextField newField = new TextField();

        newField.setAlignment(Pos.CENTER);
        newField.setFont(Font.font(10));
        newField.setPromptText("Enter Method Name");

        // Add ChangeListener to dynamically created fields
        newField.textProperty().addListener(textChangeListener);

        // Pass the new field directly to the getRightClickDeleteOption method
        newField.setContextMenu(getRightClickDeleteOption(newField));

        classVBox.getChildren().add(newFieldIndex, newField);  // Add new method
        System.out.println("New empty method added to the class");
    }

    @FXML
    void initialize() {
        assert addFieldBtn != null : "fx:id=\"addFieldBtn\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert addMethodBtn != null : "fx:id=\"addMethodBtn\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert classNameField != null : "fx:id=\"classNameField\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert classVBox != null : "fx:id=\"classVBox\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert methodNameField != null : "fx:id=\"methodNameField\" was not injected: check your FXML file 'UMLclass.fxml'.";
        assert variableNameField != null : "fx:id=\"variableNameField\" was not injected: check your FXML file 'UMLclass.fxml'.";

        // Add ChangeListener to predefined fields
        variableNameField.textProperty().addListener(textChangeListener);
        methodNameField.textProperty().addListener(textChangeListener);

        // Ensure context menus for predefined fields are set correctly
        variableNameField.setContextMenu(getRightClickDeleteOption(variableNameField));
        methodNameField.setContextMenu(getRightClickDeleteOption(methodNameField));
    }
}
