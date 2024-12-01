package controllers.classDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import controllers.ViewController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class AssociationController implements Controller {
    private static final String[] MULTIPLICITY_CHOICES = {"0..1", "1..1", "0..*", "1..*"};

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group associationGroup;

    @FXML
    private Line associationLine;

    @FXML
    private Circle corner1;

    @FXML
    private Circle corner2;

    @FXML
    private ChoiceBox<String> multiplicity1ChoiceBox;

    @FXML
    private ChoiceBox<String> multiplicity2ChoiceBox;

    private String className1;
    private String className2;

    private Node attachedNode1;
    private Node attachedNode2;

    private static final double SNAP_THRESHOLD = 20.0;

    @FXML
    void initialize() {
        setupMultiplicityChoiceBoxes();
        setupAssociationLineBindings();
        setupCornerDragHandlers();
    }

    private void setupMultiplicityChoiceBoxes() {
        multiplicity1ChoiceBox.getItems().addAll(MULTIPLICITY_CHOICES);
        multiplicity2ChoiceBox.getItems().addAll(MULTIPLICITY_CHOICES);

        multiplicity1ChoiceBox.setValue(MULTIPLICITY_CHOICES[0]);
        multiplicity2ChoiceBox.setValue(MULTIPLICITY_CHOICES[0]);

        multiplicity1ChoiceBox.setOnAction(this::onMultiplicity1Changed);
        multiplicity2ChoiceBox.setOnAction(this::onMultiplicity2Changed);
    }

    private void setupAssociationLineBindings() {
        associationLine.startXProperty().bind(
                Bindings.subtract(corner1.layoutXProperty(), 10)
        );
        associationLine.startYProperty().bind(
                Bindings.subtract(corner1.layoutYProperty(), 5)
        );
        associationLine.endXProperty().bind(
                Bindings.subtract(corner2.layoutXProperty(), 10)
        );
        associationLine.endYProperty().bind(
                Bindings.subtract(corner2.layoutYProperty(), 5)
        );

        multiplicity1ChoiceBox.layoutXProperty().bind(
                Bindings.add(corner1.layoutXProperty(), 10)
        );
        multiplicity1ChoiceBox.layoutYProperty().bind(
                Bindings.add(corner1.layoutYProperty(), 10)
        );
        multiplicity2ChoiceBox.layoutXProperty().bind(
                Bindings.subtract(corner2.layoutXProperty(), 50)
        );
        multiplicity2ChoiceBox.layoutYProperty().bind(
                Bindings.add(corner2.layoutYProperty(), 10)
        );
    }

    private void setupCornerDragHandlers() {
        corner1.setOnMouseDragged(this::onCornerDragged);
        corner2.setOnMouseDragged(this::onCornerDragged);
    }

    private void onCornerDragged(MouseEvent event) {
        Circle corner = (Circle) event.getSource();
        double newX = associationGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = associationGroup.sceneToLocal(event.getSceneY(), event.getSceneY()).getY();

        double deltaX = Math.abs(corner.getLayoutX() - newX);
        double deltaY = Math.abs(corner.getLayoutY() - newY);

        // Only update if the change is significant enough (threshold)
        if (deltaX > 1 || deltaY > 1) {
            corner.setLayoutX(newX);
            corner.setLayoutY(newY);

            snapToNearestClass(corner);
        }

        event.consume();
    }

    private void snapToNearestClass(Circle corner) {
        Node nearestNode = findNearestUMLClass(corner);

        if (nearestNode != null) {
            double SNAP_THRESHOLD = calculateDistanceToNode(corner, nearestNode.getBoundsInParent());

            if (SNAP_THRESHOLD < AssociationController.SNAP_THRESHOLD) { // Only snap if the SNAP_THRESHOLD is less than the threshold
                updateCornerPosition(corner, nearestNode);
                updateAttachedClass(corner, nearestNode);
            }
        }
    }


    private Node findNearestUMLClass(Circle corner) {
        Node nearestNode = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Node node : ViewController.getCanvasChildren()) {
            if (!node.getStyleClass().contains("uml-class")) continue;

            UMLClassController ctrl = (UMLClassController) ViewController.getController(node);
            String currentClassName = ctrl.getClassName();

            // Ensure the corner is not snapping to the already attached class
            if ((corner == corner1 && currentClassName.equals(className1)) ||
                    (corner == corner2 && currentClassName.equals(className2))) {
                continue;
            }

            Bounds bounds = node.getBoundsInParent();
            double distance = calculateDistanceToNode(corner, bounds);

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNode = node;
            }
        }

        return nearestNode;
    }

    private double calculateDistanceToNode(Circle corner, Bounds bounds) {
        double nearestX = Math.max(bounds.getMinX(), Math.min(corner.getLayoutX(), bounds.getMaxX()));
        double nearestY = Math.max(bounds.getMinY(), Math.min(corner.getLayoutY(), bounds.getMaxY()));

        return Math.hypot(corner.getLayoutX() - nearestX, corner.getLayoutY() - nearestY);
    }

    private void updateCornerPosition(Circle corner, Node node) {
        Bounds bounds = node.getBoundsInParent();
        double nearestX = Math.max(bounds.getMinX(), Math.min(corner.getLayoutX(), bounds.getMaxX()));
        double nearestY = Math.max(bounds.getMinY(), Math.min(corner.getLayoutY(), bounds.getMaxY()));

        corner.setLayoutX(nearestX);
        corner.setLayoutY(nearestY);
    }

    private void updateAttachedClass(Circle corner, Node node) {
        UMLClassController ctrl = (UMLClassController) ViewController.getController(node);

        if (corner == corner1) {
            className1 = ctrl.getClassName();
            attachedNode1 = node;
            bindCornerToNode(corner1, attachedNode1);
        } else if (corner == corner2) {
            className2 = ctrl.getClassName();
            attachedNode2 = node;
            bindCornerToNode(corner2, attachedNode2);
        }

        System.out.println("Snapped " + (corner == corner1 ? "Corner 1" : "Corner 2")
                + " to UML Class: " + ctrl.getClassName());
    }

    private void bindCornerToNode(Circle corner, Node node) {
        if (node == null) return;

        // Listen for changes in the node's bounds and update the corner's position
        node.layoutXProperty().addListener((observable, oldValue, newValue) -> {
            if (corner == corner1 && node == attachedNode1) {
                updateCornerPosition(corner, node);
            } else if (corner == corner2 && node == attachedNode2) {
                updateCornerPosition(corner, node);
            }
        });

        node.layoutYProperty().addListener((observable, oldValue, newValue) -> {
            if (corner == corner1 && node == attachedNode1) {
                updateCornerPosition(corner, node);
            } else if (corner == corner2 && node == attachedNode2) {
                updateCornerPosition(corner, node);
            }
        });
    }

    private void onMultiplicity1Changed(ActionEvent event) {
        String choice = multiplicity1ChoiceBox.getValue();
        System.out.println("Multiplicity 1: " + choice);
    }

    private void onMultiplicity2Changed(ActionEvent event) {
        String choice = multiplicity2ChoiceBox.getValue();
        System.out.println("Multiplicity 2: " + choice);
    }
}
