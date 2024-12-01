package controllers.classDiagramControllers;

import controllers.Controller;
import controllers.ViewController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralizationController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Polygon generalizationArrow;

    @FXML
    private Line generalizationLine;

    @FXML
    private Group generalizationGroup;

    @FXML
    private Circle corner1;

    @FXML
    private Circle corner2;

    private String className1;
    private String className2;

    private Node attachedNode1;
    private Node attachedNode2;

    private static final double SNAP_THRESHOLD = 20.0;

    @FXML
    void initialize() {
        assert generalizationLine != null : "fx:id=\"generalizationLine\" was not injected: check your FXML file 'generalization.fxml'.";
        assert generalizationGroup != null : "fx:id=\"generalizationGroup\" was not injected: check your FXML file 'generalization.fxml'.";
        assert corner1 != null : "fx:id=\"corner1\" was not injected: check your FXML file 'generalization.fxml'.";
        assert corner2 != null : "fx:id=\"corner2\" was not injected: check your FXML file 'generalization.fxml'.";

        setupgeneralizationLineBindings();
        setupCornerDragHandlers();
    }

    private void setupgeneralizationLineBindings() {
        // Bind generalization line's start and end points to the corners
        generalizationLine.startXProperty().bind(
                Bindings.subtract(corner1.layoutXProperty(), 10)
        );
        generalizationLine.startYProperty().bind(
                Bindings.subtract(corner1.layoutYProperty(), 10)
        );

        generalizationLine.endXProperty().bind(
                Bindings.subtract(generalizationArrow.layoutXProperty(), 10)
        );
        generalizationLine.endYProperty().bind(
                Bindings.add(generalizationArrow.layoutYProperty(), 2)
        );


        generalizationArrow.layoutXProperty().bind(
                Bindings.subtract(corner2.layoutXProperty(), 37)
        );
        generalizationArrow.layoutYProperty().bind(
                Bindings.subtract(corner2.layoutYProperty(), 11)
        );

        //alternate2
//        // Add listeners to update the arrowhead rotation
//        generalizationLine.startXProperty().addListener((observable, oldValue, newValue) -> rotateArrowhead(corner2));
//        generalizationLine.startYProperty().addListener((observable, oldValue, newValue) -> rotateArrowhead(corner2));
//        generalizationLine.endXProperty().addListener((observable, oldValue, newValue) -> rotateArrowhead(corner1));
//        generalizationLine.endYProperty().addListener((observable, oldValue, newValue) -> rotateArrowhead(corner1));
    }

    private void setupCornerDragHandlers() {
        corner1.setOnMouseDragged(this::onCornerDragged);
        corner2.setOnMouseDragged(this::onCornerDragged);
    }

    private void onCornerDragged(MouseEvent event) {
        Circle corner = (Circle) event.getSource();
        double newX = generalizationGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = generalizationGroup.sceneToLocal(event.getSceneY(), event.getSceneY()).getY();

        double deltaX = Math.abs(corner.getLayoutX() - newX);
        double deltaY = Math.abs(corner.getLayoutY() - newY);

        // Only update if the change is significant enough (threshold)
        if (deltaX > 1 || deltaY > 1) {
            corner.setLayoutX(newX);
            corner.setLayoutY(newY);

            snapToNearestClass(corner);

            //alternate1
//            // Call rotateArrowhead with the active corner as the pivot point
//            if (corner == corner1) {
//                rotateArrowhead(corner2);
//            } else if (corner == corner2) {
//                rotateArrowhead(corner1);
//            }
        }

        event.consume();
    }


    private void snapToNearestClass(Circle corner) {
        Node nearestNode = findNearestUMLClass(corner);

        if (nearestNode != null) {
            double distance = calculateDistanceToNode(corner, nearestNode.getBoundsInParent());

            if (distance < SNAP_THRESHOLD) { // Only snap if the distance is less than the threshold
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

//    private void rotateArrowhead(Node pivot) {
//        double startX = generalizationLine.getStartX();
//        double startY = generalizationLine.getStartY();
//        double endX = generalizationLine.getEndX();
//        double endY = generalizationLine.getEndY();
//
//        // Calculate the angle in degrees
//        double angle = Math.toDegrees(Math.atan2(endY - startY, endX - startX));
//
//        // Set the rotation angle
//        generalizationArrow.setRotate(angle);
//
//        // Adjust the pivot point for rotation by translating the group
//        generalizationArrow.setTranslateX(pivot.getLayoutX() / (generalizationArrow.getBoundsInParent().getWidth()+2));
//        generalizationArrow.setTranslateY(pivot.getLayoutY() / (generalizationArrow.getBoundsInParent().getHeight()+2));
//    }



}
