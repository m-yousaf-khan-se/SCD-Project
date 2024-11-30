package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.Controller;
import controllers.ViewController;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class includeController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group includeGroup;

    @FXML
    private Line includeLine;

    @FXML
    private Circle startCircle;

    @FXML
    private Polygon arrowPolygon;

    @FXML
    private Text includeText; // The text node for "<<include>>"

    private Node attachedStartNode;
    private Node attachedEndNode;

    @FXML
    void initialize() {
        setupAssociationLineBindings();
        setupDragHandlers();
    }

    private void setupAssociationLineBindings() {
        // Bind the start of the line to the circle
        includeLine.startXProperty().bind(startCircle.layoutXProperty());
        includeLine.startYProperty().bind(startCircle.layoutYProperty());

        // Bind the end of the line to the polygon's position
        includeLine.endXProperty().bind(arrowPolygon.layoutXProperty());
        includeLine.endYProperty().bind(arrowPolygon.layoutYProperty());

        // Bind the text position to the midpoint of the line
        includeLine.startXProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        includeLine.startYProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        includeLine.endXProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        includeLine.endYProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());

        // Initial position for text
        updateTextPosition();
    }

    private void setupDragHandlers() {
        startCircle.setOnMouseDragged(event -> onNodeDragged(startCircle, event, true));
        arrowPolygon.setOnMouseDragged(event -> onNodeDragged(arrowPolygon, event, false));
    }

    private void onNodeDragged(Node node, MouseEvent event, boolean isStart) {
        double newX = includeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = includeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

        double deltaX = Math.abs(node.getLayoutX() - newX);
        double deltaY = Math.abs(node.getLayoutY() - newY);

        if (deltaX > 1 || deltaY > 1) {
            node.setLayoutX(newX);
            node.setLayoutY(newY);

            // Snap the node to the edge of the nearest node
            snapToNearestNodeEdge(node, isStart);
        }

        event.consume();
    }

    private void updateTextPosition() {
        // Calculate the midpoint of the line
        double midX = (includeLine.getStartX() + includeLine.getEndX()) / 2;
        double midY = (includeLine.getStartY() + includeLine.getEndY()) / 2;

        // Position the text slightly above the line midpoint
        includeText.setLayoutX(midX - includeText.getBoundsInLocal().getWidth() / 2);
        includeText.setLayoutY(midY - 10); // Offset the text above the line
    }

    private void snapToNearestNodeEdge(Node node, boolean isStart) {
        Node nearestNode = findNearestNode(node);

        if (nearestNode != null) {
            double threshold = 20; // Adjust this value as needed
            double distance = calculateDistanceToNode(node, nearestNode.getBoundsInParent());

            if (distance < threshold) {
                updateNodePositionToEdge(node, nearestNode); // Attach to edge
                updateAttachedNode(node, nearestNode, isStart); // Update attached node
            }
        }
    }

    private Node findNearestNode(Node node) {
        Node nearestNode = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Node canvasNode : ViewController.getCanvasChildren()) {
            if (!(canvasNode.getStyleClass().contains("usecase-actor") || canvasNode.getStyleClass().contains("uml-useCase"))) {
                continue; // Skip irrelevant nodes
            }

            Bounds bounds = canvasNode.getBoundsInParent();
            double distance = calculateDistanceToNode(node, bounds);

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNode = canvasNode;
            }
        }

        return nearestNode;
    }

    private double calculateDistanceToNode(Node node, Bounds bounds) {
        double nearestX = Math.max(bounds.getMinX(), Math.min(node.getLayoutX(), bounds.getMaxX()));
        double nearestY = Math.max(bounds.getMinY(), Math.min(node.getLayoutY(), bounds.getMaxY()));

        return Math.hypot(node.getLayoutX() - nearestX, node.getLayoutY() - nearestY);
    }

    private void updateNodePositionToEdge(Node node, Node targetNode) {
        Bounds bounds = targetNode.getBoundsInParent();
        double centerX = (bounds.getMinX() + bounds.getMaxX()) / 2;
        double centerY = (bounds.getMinY() + bounds.getMaxY()) / 2;

        double angle = Math.atan2(node.getLayoutY() - centerY, node.getLayoutX() - centerX);

        double radiusX = (bounds.getMaxX() - bounds.getMinX()) / 2;
        double radiusY = (bounds.getMaxY() - bounds.getMinY()) / 2;

        double edgeX = centerX + radiusX * Math.cos(angle);
        double edgeY = centerY + radiusY * Math.sin(angle);

        node.setLayoutX(edgeX);
        node.setLayoutY(edgeY);
    }

    private void updateAttachedNode(Node node, Node targetNode, boolean isStart) {
        if (isStart) {
            attachedStartNode = targetNode;
        } else {
            attachedEndNode = targetNode;
        }

        System.out.println((isStart ? "Start" : "End") + " node snapped to: " + targetNode.getId());
    }
}
