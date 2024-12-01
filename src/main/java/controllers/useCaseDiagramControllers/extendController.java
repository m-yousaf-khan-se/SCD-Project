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
import javafx.scene.text.Text;

public class extendController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group excludeGroup;

    @FXML
    private Line excludeLine;

    @FXML
    private Line arrowLineLeft;

    @FXML
    private Line arrowLineRight;

    @FXML
    private Circle startCircle;

    @FXML
    private Circle endCircle;

    @FXML
    private Text excludeText; // The text node for "<<include>>"

    private Node attachedStartNode;
    private Node attachedEndNode;

    @FXML
    void initialize() {
        setupAssociationLineBindings();
        setupDragHandlers();
    }

    private void setupAssociationLineBindings() {
        // Bind the start of the line to the circle
        excludeLine.startXProperty().bind(startCircle.layoutXProperty());
        excludeLine.startYProperty().bind(startCircle.layoutYProperty());

        // Update arrowhead lines when the main line changes
        excludeLine.endXProperty().addListener((observable, oldValue, newValue) -> updateArrowLines());
        excludeLine.endYProperty().addListener((observable, oldValue, newValue) -> updateArrowLines());
        excludeLine.startXProperty().addListener((observable, oldValue, newValue) -> updateArrowLines());
        excludeLine.startYProperty().addListener((observable, oldValue, newValue) -> updateArrowLines());
        excludeLine.endXProperty().bind(endCircle.layoutXProperty());
        excludeLine.endYProperty().bind(endCircle.layoutYProperty());

        // Initial position of the arrowhead
        updateArrowLines();

        // Bind the text position to the midpoint of the line
        excludeLine.startXProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        excludeLine.startYProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        excludeLine.endXProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        excludeLine.endYProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());

        // Initial position for text
        updateTextPosition();
    }

    private void updateArrowLines() {
        // Calculate angle of the line
        double deltaX = excludeLine.getEndX() - excludeLine.getStartX();
        double deltaY = excludeLine.getEndY() - excludeLine.getStartY();
        double angle = Math.atan2(deltaY, deltaX);

        // Arrowhead line length
        double arrowLength = 15; // Adjust based on your preference
        double arrowAngle = Math.PI / 6; // 30 degrees for the arrowhead

        // Calculate points for left arrow line
        double leftX = excludeLine.getEndX() - arrowLength * Math.cos(angle - arrowAngle);
        double leftY = excludeLine.getEndY() - arrowLength * Math.sin(angle - arrowAngle);

        // Calculate points for right arrow line
        double rightX = excludeLine.getEndX() - arrowLength * Math.cos(angle + arrowAngle);
        double rightY = excludeLine.getEndY() - arrowLength * Math.sin(angle + arrowAngle);

        // Update arrowhead line positions
        arrowLineLeft.setStartX(excludeLine.getEndX());
        arrowLineLeft.setStartY(excludeLine.getEndY());
        arrowLineLeft.setEndX(leftX);
        arrowLineLeft.setEndY(leftY);

        arrowLineRight.setStartX(excludeLine.getEndX());
        arrowLineRight.setStartY(excludeLine.getEndY());
        arrowLineRight.setEndX(rightX);
        arrowLineRight.setEndY(rightY);
    }

    private void updateTextPosition() {
        // Calculate the midpoint of the line
        double midX = (excludeLine.getStartX() + excludeLine.getEndX()) / 2;
        double midY = (excludeLine.getStartY() + excludeLine.getEndY()) / 2;

        // Position the text slightly above the line midpoint
        excludeText.setLayoutX(midX - excludeText.getBoundsInLocal().getWidth() / 2);
        excludeText.setLayoutY(midY - 10); // Offset the text above the line
    }

    private void setupDragHandlers() {
        startCircle.setOnMouseDragged(event -> onNodeDragged(startCircle, event, true));
        endCircle.setOnMouseDragged(event -> onNodeDragged(endCircle, event, false));
        excludeLine.setOnMouseDragged(this::onLineDragged);
        arrowLineLeft.setOnMouseDragged(event -> onArrowDragged(event, true));
        arrowLineRight.setOnMouseDragged(event -> onArrowDragged(event, false));
    }

    private void onLineDragged(MouseEvent event) {
        double newX = excludeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = excludeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

        double deltaX = Math.abs(excludeLine.getStartX() - newX);
        double deltaY = Math.abs(excludeLine.getStartY() - newY);

        if (deltaX > 1 || deltaY > 1) {
            excludeLine.setEndX(newX);
            excludeLine.setEndY(newY);
            updateArrowLines(); // Update arrow position based on new line coordinates
            updateTextPosition(); // Update text position for the line
        }

        event.consume();
    }

    private void onArrowDragged(MouseEvent event, boolean isLeftArrow) {
        // Get the current drag position
        double newX = excludeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = excludeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

        // Update the appropriate arrow position based on the dragging direction
        if (isLeftArrow) {
            // Set the new endpoint of the left arrow
            arrowLineLeft.setEndX(newX);
            arrowLineLeft.setEndY(newY);
        } else {
            // Set the new endpoint of the right arrow
            arrowLineRight.setEndX(newX);
            arrowLineRight.setEndY(newY);
        }

        // Update the line’s endpoint to the new arrow position
        if (isLeftArrow) {
            excludeLine.setStartX(newX);
            excludeLine.setStartY(newY);
        } else {
            excludeLine.setEndX(newX);
            excludeLine.setEndY(newY);
        }

        // Update both arrows after moving the main line
        updateArrowLines();

        // Recalculate the text position based on the new line endpoint
        updateTextPosition();

        event.consume();
    }

    private void onNodeDragged(Node node, MouseEvent event, boolean isStart) {
        double newX = excludeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = excludeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

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

    private void snapToNearestNodeEdge(Node node, boolean isStart) {
        Node nearestNode = findNearestUseCaseNode(node);

        if (nearestNode != null) {
            double threshold = 20; // Adjust this value as needed
            double distance = calculateDistanceToNode(node, nearestNode.getBoundsInParent());

            if (distance < threshold) {
                updateNodePositionToEdge(node, nearestNode); // Attach to edge
                updateAttachedNode(node, nearestNode, isStart); // Update attached node
            }
        }
    }

    private Node findNearestUseCaseNode(Node node) {
        Node nearestNode = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Node canvasNode : ViewController.getCanvasChildren()) {
            if (!(canvasNode.getStyleClass().contains("uml-useCase"))) {
                continue; // Skip non-usecase nodes
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

        double deltaX = node.getLayoutX() - nearestX;
        double deltaY = node.getLayoutY() - nearestY;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private void updateNodePositionToEdge(Node node, Node nearestNode) {
        Bounds bounds = nearestNode.getBoundsInParent();

        double nodeX = node.getLayoutX();
        double nodeY = node.getLayoutY();

        // Calculate distances to each edge
        double distanceToLeft = Math.abs(nodeX - bounds.getMinX());
        double distanceToRight = Math.abs(nodeX - bounds.getMaxX());
        double distanceToTop = Math.abs(nodeY - bounds.getMinY());
        double distanceToBottom = Math.abs(nodeY - bounds.getMaxY());

        // Find the edge with the minimum distance
        double minDistance = Math.min(Math.min(distanceToLeft, distanceToRight), Math.min(distanceToTop, distanceToBottom));

        if (minDistance == distanceToLeft) {
            // Snap to left edge
            node.setLayoutX(bounds.getMinX());
            node.setLayoutY(clamp(nodeY, bounds.getMinY(), bounds.getMaxY()));
        } else if (minDistance == distanceToRight) {
            // Snap to right edge
            node.setLayoutX(bounds.getMaxX());
            node.setLayoutY(clamp(nodeY, bounds.getMinY(), bounds.getMaxY()));
        } else if (minDistance == distanceToTop) {
            // Snap to top edge
            node.setLayoutX(clamp(nodeX, bounds.getMinX(), bounds.getMaxX()));
            node.setLayoutY(bounds.getMinY());
        } else if (minDistance == distanceToBottom) {
            // Snap to bottom edge
            node.setLayoutX(clamp(nodeX, bounds.getMinX(), bounds.getMaxX()));
            node.setLayoutY(bounds.getMaxY());
        }
    }

    /**
     * Clamps a value between a minimum and a maximum.
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }


    private void updateAttachedNode(Node node, Node nearestNode, boolean isStart) {
        if (isStart) {
            attachedStartNode = nearestNode;
        } else {
            attachedEndNode = nearestNode;
        }
        System.out.println((node.getStyleClass().contains("usecase-actor") ? "Actor" : "Use Case") + " snapped to node: " + node.getId());
    }
}
