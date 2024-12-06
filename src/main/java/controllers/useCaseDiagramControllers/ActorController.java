package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.ControllerClass;
import controllers.IController;
import controllers.ViewIController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public abstract class ActorController extends ViewIController implements IController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group ActorGroup;

    @FXML
    private Circle head;

    @FXML
    private Line body;

    @FXML
    private Line arms; // Single line for both arms

    @FXML
    private Line leftLeg;

    @FXML
    private Line rightLeg;

    @FXML
    private TextField actorNameTextField;
    private String initialText = "";
    private String actorName = "";

    public String getActorName() {
        return actorName;
    }

    @FXML
    public void initialize() {
        setupActorBindings();
        setupDragHandlers();
    }


    public void setupActorBindings() {
        // Body
        body.startXProperty().bind(head.centerXProperty());
        body.startYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius())); // Start just below the head
        body.endXProperty().bind(head.centerXProperty());
        body.endYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius() + 40)); // Extend 40 units downward

        // Arms (single horizontal line)
        arms.startXProperty().bind(Bindings.subtract(head.centerXProperty(), 20)); // Extend to the left
        arms.startYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius() + 10)); // Slightly below the top of the body
        arms.endXProperty().bind(Bindings.add(head.centerXProperty(), 20)); // Extend to the right
        arms.endYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius() + 10)); // Same height for horizontal line

        // Left Leg
        leftLeg.startXProperty().bind(head.centerXProperty());
        leftLeg.startYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius() + 40)); // Start at the bottom of the body
        leftLeg.endXProperty().bind(Bindings.subtract(head.centerXProperty(), 15)); // Extend diagonally to the left
        leftLeg.endYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius() + 60)); // Extend downward

        // Right Leg
        rightLeg.startXProperty().bind(head.centerXProperty());
        rightLeg.startYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius() + 40)); // Start at the bottom of the body
        rightLeg.endXProperty().bind(Bindings.add(head.centerXProperty(), 15)); // Extend diagonally to the right
        rightLeg.endYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius() + 60)); // Extend downward

        // Position the text field below the actor's head
        actorNameTextField.layoutXProperty().bind(Bindings.subtract(head.centerXProperty(), actorNameTextField.widthProperty().divide(2))); // Center align
        actorNameTextField.layoutYProperty().bind(Bindings.add(head.centerYProperty(), head.getRadius() + 70)); // Place below the legs
    }




    public void setupDragHandlers() {
        // Enable dragging the actor (dragging the head moves the entire actor)
        head.setOnMouseDragged(this::onActorDragged);
    }


    public void onActorDragged(MouseEvent event) {
        // Convert scene coordinates to local coordinates for dragging
        double newX = ActorGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = ActorGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

        // Update head's position
        head.setCenterX(newX);
        head.setCenterY(newY);

        event.consume();
    }

    private void attachFocusChangeListener(TextField textField) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Field gained focus
                initialText = textField.getText();
            } else {
                // Field lost focus
                if (!initialText.equals(textField.getText())) {
                    if(textField.getPromptText().contains("actor name"))
                    {
                        actorName = textField.getText();
                        addOrUpdateActorName(initialText, actorName);
                    }

                    System.out.println("Text changed in: " + textField.getPromptText() + " (of the actor: " + actorName + ")");
                    System.out.println("Old Value: " + initialText);
                    System.out.println("New Value: " + textField.getText());
                }
            }
        });
    }
}
