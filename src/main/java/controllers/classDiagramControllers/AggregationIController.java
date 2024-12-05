package controllers.classDiagramControllers;

import controllers.IController;
import controllers.ViewIController;
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

public abstract class AggregationIController implements IController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Polygon aggregationArrow;

    @FXML
    private Line aggregationLine;

    @FXML
    private Group aggregationGroup;

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
        assert aggregationLine != null : "fx:id=\"aggregationLine\" was not injected: check your FXML file 'aggregation.fxml'.";
        assert aggregationGroup != null : "fx:id=\"aggregationGroup\" was not injected: check your FXML file 'aggregation.fxml'.";
        assert corner1 != null : "fx:id=\"corner1\" was not injected: check your FXML file 'aggregation.fxml'.";
        assert corner2 != null : "fx:id=\"corner2\" was not injected: check your FXML file 'aggregation.fxml'.";

        setupAggregationLineBindings();
        setupCornerDragHandlers();
    }

    private void setupAggregationLineBindings() {
        // Bind aggregation line's start and end points to the corners
        aggregationLine.startXProperty().bind(
                Bindings.subtract(corner1.layoutXProperty(), 10)
        );
        aggregationLine.startYProperty().bind(
                Bindings.subtract(corner1.layoutYProperty(), 10)
        );

        aggregationLine.endXProperty().bind(
                Bindings.subtract(aggregationArrow.layoutXProperty(), 10)
        );
        aggregationLine.endYProperty().bind(
                Bindings.add(aggregationArrow.layoutYProperty(), 2)
        );


        aggregationArrow.layoutXProperty().bind(
                Bindings.subtract(corner2.layoutXProperty(), 37)
        );
        aggregationArrow.layoutYProperty().bind(
                Bindings.subtract(corner2.layoutYProperty(), 11)
        );

        //alternate2
//        // Add listeners to update the arrowhead rotation
//        aggregationLine.startXProperty().addListener((observable, oldValue, newValue) -> rotateArrowhead(corner2));
//        aggregationLine.startYProperty().addListener((observable, oldValue, newValue) -> rotateArrowhead(corner2));
//        aggregationLine.endXProperty().addListener((observable, oldValue, newValue) -> rotateArrowhead(corner1));
//        aggregationLine.endYProperty().addListener((observable, oldValue, newValue) -> rotateArrowhead(corner1));
    }

    private void setupCornerDragHandlers() {
        corner1.setOnMouseDragged(this::onCornerDragged);
        corner2.setOnMouseDragged(this::onCornerDragged);
    }

    private void onCornerDragged(MouseEvent event) {
        Circle corner = (Circle) event.getSource();
        double newX = aggregationGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = aggregationGroup.sceneToLocal(event.getSceneY(), event.getSceneY()).getY();

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

        for (Node node : ViewIController.getCanvasChildren()) {
            if (!node.getStyleClass().contains("uml-class")) continue;

            UMLClassIController ctrl = (UMLClassIController) ViewIController.getClassController(node);
            String currentClassName = ctrl.getUMLClassName();

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
        UMLClassIController ctrl = (UMLClassIController) ViewIController.getClassController(node);

        if (corner == corner1) {
            className1 = ctrl.getUMLClassName();
            attachedNode1 = node;
            bindCornerToNode(corner1, attachedNode1);
        } else if (corner == corner2) {
            className2 = ctrl.getUMLClassName();
            attachedNode2 = node;
            bindCornerToNode(corner2, attachedNode2);
        }

        System.out.println("Snapped " + (corner == corner1 ? "Corner 1" : "Corner 2")
                + " to UML Class: " + ctrl.getUMLClassName());
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

    @Override
    public Double[] getCoordinates() {
        Double []classCoordinates = new Double[2];
        classCoordinates[0] = aggregationGroup.getLayoutX();
        classCoordinates[1] = aggregationGroup.getLayoutY();
        return classCoordinates;
    }

    @Override
    public String[] getClassesName(){
        return new String[]{className1, className2};
    }

//    private void rotateArrowhead(Node pivot) {
//        double startX = aggregationLine.getStartX();
//        double startY = aggregationLine.getStartY();
//        double endX = aggregationLine.getEndX();
//        double endY = aggregationLine.getEndY();
//
//        // Calculate the angle in degrees
//        double angle = Math.toDegrees(Math.atan2(endY - startY, endX - startX));
//
//        // Set the rotation angle
//        aggregationArrow.setRotate(angle);
//
//        // Adjust the pivot point for rotation by translating the group
//        aggregationArrow.setTranslateX(pivot.getLayoutX() / (aggregationArrow.getBoundsInParent().getWidth()+2));
//        aggregationArrow.setTranslateY(pivot.getLayoutY() / (aggregationArrow.getBoundsInParent().getHeight()+2));
//    }



}
