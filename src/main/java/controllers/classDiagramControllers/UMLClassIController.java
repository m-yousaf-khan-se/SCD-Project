package controllers.classDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.IController;
import controllers.ViewIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UMLClassIController extends ViewIController implements IController, IClassComponentController {

    @FXML
    public Group umlClassGroup;

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

    private String initialText = "";
    private String className = "";


    public String getUMLClassName()
    {
        return className;
    }
    private ContextMenu createContextMenu(TextField textField) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> handleDeleteField(textField));
        contextMenu.getItems().add(deleteItem);
        return contextMenu;
    }

    private void handleDeleteField(TextField textField) {
        if (textField != null && textField.getParent() instanceof VBox) {
            VBox parent = (VBox) textField.getParent();
            parent.getChildren().remove(textField);
            if(textField.getPromptText().contains("variable"))
                removeFieldFromClass(className, textField.getText());
            else if(textField.getPromptText().contains("method"))
                removeMethodFromClass(className, textField.getText());
            System.out.println("TextField deleted: " + textField.getPromptText() + " from the class : " + className);
        }
    }

    private void attachFocusChangeListener(TextField textField) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Field gained focus
                initialText = textField.getText();
            } else {
                // Field lost focus
                if (!initialText.equals(textField.getText())) {
                    if(textField.getPromptText().contains("class name"))
                    {
                        className = textField.getText();
                        addOrUpdateClassName(initialText, className);
                    }
                    else if(textField.getPromptText().contains("method name"))
                    {
                        addOrUpdateMethodToClass(className, initialText, textField.getText());
                    }
                    else if(textField.getPromptText().contains("variable name"))
                    {
                        addOrUpdateFieldToClass(className, initialText, textField.getText());
                    }
                    System.out.println("Text changed in: " + textField.getPromptText() + " (of the class: " + className + ")");
                    System.out.println("Old Value: " + initialText);
                    System.out.println("New Value: " + textField.getText());
                }
            }
        });
    }

    private void setupTextField(TextField textField, String prompt) {
        textField.setAlignment(Pos.CENTER);
        textField.setFont(Font.font(10));
        textField.setPromptText(prompt);
        attachFocusChangeListener(textField);
        textField.setContextMenu(createContextMenu(textField));
    }

    @FXML
    void addNewFieldListener(ActionEvent event) {
        TextField newField = new TextField();
        setupTextField(newField, "(+/-/#):variable name:returnType");
        classVBox.getChildren().add(classVBox.getChildren().indexOf(addFieldBtn), newField);
        System.out.println("New empty field added to the class");
    }

    @FXML
    void addNewMethodListener(ActionEvent event) {
        TextField newField = new TextField();
        setupTextField(newField, "(+/#/-):method name:returnType");
        classVBox.getChildren().add(classVBox.getChildren().indexOf(addMethodBtn), newField);
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

        // Attach listeners to predefined fields
        setupTextField(variableNameField, "(+/-/#):variable name:returnType");
        setupTextField(methodNameField, "(+/#/-):method name:returnType");

        // Automatically attach to all TextFields in VBox (useful for FXML-defined TextFields)
        classVBox.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .forEach(this::attachFocusChangeListener);

    }

    @Override
    public Double[] getCoordinates() {
        Double []classCoordinates = new Double[2];
        classCoordinates[0] = umlClassGroup.getLayoutX();
        classCoordinates[1] = umlClassGroup.getLayoutY();
        return classCoordinates;
    }

    @Override
    public String[] getClassesName() {
        throw new UnsupportedOperationException("Not implemented! as Class Diagram contains name of only one class. Call getUMLClassName() ");
    }

    @Override
    public void setClassName1(String className1) {
        this.className = className1;
    }

    @Override
    public void setClassName2(String className2) {
        this.className = className2;
    }

    @Override
    public void setAttachedNode1(Node attachedNode1) {
        throw new UnsupportedOperationException("These methods are only valid for relationships");
    }

    @Override
    public void setAttachedNode2(Node attachedNode2) {
        throw new UnsupportedOperationException("These methods are only valid for relationships");
    }

    public void addNewMethod(String methodDetail) {

        if(methodNameField.getText().isEmpty())
        {
            methodNameField.setText(methodDetail);
            return;
        }

        // Create a new TextField for the method
        TextField newMethod = new TextField(methodDetail);

        // Setup the TextField with a prompt and other properties
        setupTextField(newMethod, "(+/#/-):method name:returnType");

        // Add the TextField to the VBox before the "Add Method" button
        classVBox.getChildren().add(classVBox.getChildren().indexOf(addMethodBtn), newMethod);

        System.out.println("New method added: " + methodDetail);
    }

    public void addNewField(String fieldDetail) {
        if(classNameField.getText().isEmpty())
        {
            classNameField.setText(fieldDetail);
            return;
        }

        // Create a new TextField for the field
        TextField newField = new TextField(fieldDetail);

        // Setup the TextField with a prompt and other properties
        setupTextField(newField, "(+/-/#):variable name:returnType");

        // Add the TextField to the VBox before the "Add Field" button
        classVBox.getChildren().add(classVBox.getChildren().indexOf(addFieldBtn), newField);

        System.out.println("New field added: " + fieldDetail);
    }

    public void setCoordinates(Double x, Double y)
    {
        umlClassGroup.setLayoutX(x);
        umlClassGroup.setLayoutY(y);
    }

}
