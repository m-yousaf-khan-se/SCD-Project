package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.ControllerClass;
import controllers.IController;
import controllers.ViewIController;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public  class useCaseController extends ViewIController implements IController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane useCasePane;
    @FXML
    private TextField useCaseTextField;
    @FXML
    private Group useCaseGroup;

    private String initialText = "";
    private String useCaseName = "";

    public String getUseCaseName() {
        return useCaseName;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }

    @FXML
    public void initialize() {
        assert useCasePane != null : "fx:id=\"useCasePane\" was not injected: check your FXML file 'useCasePane.fxml'.";

        assert useCaseTextField != null : "fx:id=\"useCaseTextField\" was not injected: check your FXML file 'useCasePane.fxml'.";

        //This is slightly different from Class focusListener
        attachFocusChangeListener(useCaseTextField);

        //Use case textField is working properly (after typing any input just press enter then input will be accessed through above function)
    }

    //attach this listener to textField.
    private void attachFocusChangeListener(TextField textField) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Field gained focus
                initialText = textField.getText();
            } else {
                // Field lost focus
                if (!initialText.equals(textField.getText())) {
                    if(textField.getPromptText().contains("usecase name"))
                    {
                        useCaseName = textField.getText();
                        addOrUpdateUseCaseName(initialText, useCaseName);
                    }

                    System.out.println("Text changed in: " + textField.getPromptText() + " (of the useCase: " + useCaseName + ")");
                    System.out.println("Old Value: " + initialText);
                    System.out.println("New Value: " + textField.getText());
                }
            }
        });
    }

    @Override
    public Double[] getCoordinates() {
        Double []useCaseCoordinates = new Double[2];
        useCaseCoordinates[0] = useCaseGroup.getLayoutX();
        useCaseCoordinates[1] = useCaseGroup.getLayoutY();
        return useCaseCoordinates;
    }

    @Override
    public String[] getClassesName() {
        throw new UnsupportedOperationException("Not implemented! as it has one actor only right now");
    }
    public void setCoordinates(Double x, Double y){
        useCaseGroup.setLayoutX(x);
        useCaseGroup.setLayoutY(y);
    }

}

