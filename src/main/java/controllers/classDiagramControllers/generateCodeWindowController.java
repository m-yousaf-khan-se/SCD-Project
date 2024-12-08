package controllers.classDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class generateCodeWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea textAreaForGeneratedJavaCode;

    @FXML
    void initialize() {
        assert textAreaForGeneratedJavaCode != null : "fx:id=\"textAreaForGeneratedJavaCode\" was not injected: check your FXML file 'TextAreaForGeneratedJavaCode.fxml'.";

    }

    public void setTextArea(String generatedJavaCode){
        this.textAreaForGeneratedJavaCode.setText(generatedJavaCode);
    }

}
