package controllers.useCaseDiagramControllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.IController;
import controllers.ViewIController;
import controllers.classDiagramControllers.UMLClassIController;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class includeIController extends ViewIController implements IController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group includeGroup;

    @FXML
    private Line includeLine;

    @FXML
    private Line arrowLineLeft;

    @FXML
    private Line arrowLineRight;

    @FXML
    private Circle startCircle;

    @FXML
    private Circle endCircle;

    @FXML
    private Text includeText; // The text node for "<<include>>"

    private Node attachedNode1;
    private Node attachedNode2;

    String useCaseName1="";
    String useCaseName2="";

    @FXML
    void initialize() {
        setupAssociationLineBindings();
        setupDragHandlers();
    }

    private void setupAssociationLineBindings() {
        // Bind the start of the line to the circle
        includeLine.startXProperty().bind(startCircle.layoutXProperty());
        includeLine.startYProperty().bind(startCircle.layoutYProperty());

        // Update arrowhead lines when the main line changes
        includeLine.endXProperty().addListener((observable, oldValue, newValue) -> updateArrowLines());
        includeLine.endYProperty().addListener((observable, oldValue, newValue) -> updateArrowLines());
        includeLine.startXProperty().addListener((observable, oldValue, newValue) -> updateArrowLines());
        includeLine.startYProperty().addListener((observable, oldValue, newValue) -> updateArrowLines());
        includeLine.endXProperty().bind(endCircle.layoutXProperty());
        includeLine.endYProperty().bind(endCircle.layoutYProperty());

        // Initial position of the arrowhead
        updateArrowLines();

        // Bind the text position to the midpoint of the line
        includeLine.startXProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        includeLine.startYProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        includeLine.endXProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());
        includeLine.endYProperty().addListener((observable, oldValue, newValue) -> updateTextPosition());

        // Initial position for text
        updateTextPosition();
    }

    private void updateArrowLines() {
        // Calculate angle of the line
        double deltaX = includeLine.getEndX() - includeLine.getStartX();
        double deltaY = includeLine.getEndY() - includeLine.getStartY();
        double angle = Math.atan2(deltaY, deltaX);

        // Arrowhead line length
        double arrowLength = 15; // Adjust based on your preference
        double arrowAngle = Math.PI / 6; // 30 degrees for the arrowhead

        // Calculate points for left arrow line
        double leftX = includeLine.getEndX() - arrowLength * Math.cos(angle - arrowAngle);
        double leftY = includeLine.getEndY() - arrowLength * Math.sin(angle - arrowAngle);

        // Calculate points for right arrow line
        double rightX = includeLine.getEndX() - arrowLength * Math.cos(angle + arrowAngle);
        double rightY = includeLine.getEndY() - arrowLength * Math.sin(angle + arrowAngle);

        // Update arrowhead line positions
        arrowLineLeft.setStartX(includeLine.getEndX());
        arrowLineLeft.setStartY(includeLine.getEndY());
        arrowLineLeft.setEndX(leftX);
        arrowLineLeft.setEndY(leftY);

        arrowLineRight.setStartX(includeLine.getEndX());
        arrowLineRight.setStartY(includeLine.getEndY());
        arrowLineRight.setEndX(rightX);
        arrowLineRight.setEndY(rightY);
    }

    private void updateTextPosition() {
        // Calculate the midpoint of the line
        double midX = (includeLine.getStartX() + includeLine.getEndX()) / 2;
        double midY = (includeLine.getStartY() + includeLine.getEndY()) / 2;

        // Position the text slightly above the line midpoint
        includeText.setLayoutX(midX - includeText.getBoundsInLocal().getWidth() / 2);
        includeText.setLayoutY(midY - 10); // Offset the text above the line
    }

    private void setupDragHandlers() {
        startCircle.setOnMouseDragged(event -> onNodeDragged(startCircle, event));
        endCircle.setOnMouseDragged(event -> onNodeDragged(endCircle, event));
        includeLine.setOnMouseDragged(this::onLineDragged);
        arrowLineLeft.setOnMouseDragged(event -> onArrowDragged(event, true));
        arrowLineRight.setOnMouseDragged(event -> onArrowDragged(event, false));
    }

    private void onLineDragged(MouseEvent event) {
        double newX = includeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = includeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

        double deltaX = Math.abs(includeLine.getStartX() - newX);
        double deltaY = Math.abs(includeLine.getStartY() - newY);

        if (deltaX > 1 || deltaY > 1) {
            includeLine.setEndX(newX);
            includeLine.setEndY(newY);
            updateArrowLines(); // Update arrow position based on new line coordinates
            updateTextPosition(); // Update text position for the line
        }

        event.consume();
    }

    private void onArrowDragged(MouseEvent event, boolean isLeftArrow) {
        // Get the current drag position
        double newX = includeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = includeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

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
            includeLine.setStartX(newX);
            includeLine.setStartY(newY);
        } else {
            includeLine.setEndX(newX);
            includeLine.setEndY(newY);
        }

        // Update both arrows after moving the main line
        updateArrowLines();

        // Recalculate the text position based on the new line endpoint
        updateTextPosition();

        event.consume();
    }

    private void onNodeDragged(Node node, MouseEvent event) {
        double newX = includeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = includeGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

        double deltaX = Math.abs(node.getLayoutX() - newX);
        double deltaY = Math.abs(node.getLayoutY() - newY);

        if (deltaX > 1 || deltaY > 1) {
            node.setLayoutX(newX);
            node.setLayoutY(newY);

            // Snap the node to the edge of the nearest node
            snapToNearestNodeEdge(node);
        }

        event.consume();
    }

    private void snapToNearestNodeEdge(Node node) {
        Node nearestNode = findNearestUseCaseNode(node);

        if (nearestNode != null) {
            double threshold = 20; // Adjust this value as needed
            double distance = calculateDistanceToNode(node, nearestNode.getBoundsInParent());

            if (distance < threshold) {
                Circle correspondingCircle = (node == startCircle) ? startCircle : endCircle;
                updateNodePositionToEdge(correspondingCircle, nearestNode); // Attach to edge
                updateAttachedNode(correspondingCircle, nearestNode); // Update attached node
            }
        }
    }

    private Node findNearestUseCaseNode(Node node) {
        Node nearestNode = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Node canvasNode : ViewIController.getCanvasChildren()) {
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

    private void bindCornerToNode(Circle circle, Node node) {
        if (node == null) return;

        // Listen for changes in the node's layout and update the circle's position
        node.layoutXProperty().addListener((observable, oldValue, newValue) -> {
            if (circle == startCircle && node == attachedNode1) {
                updateNodePositionToEdge(circle, node);
            } else if (circle == endCircle && node == attachedNode2) {
                updateNodePositionToEdge(circle, node);
            }
        });

        node.layoutYProperty().addListener((observable, oldValue, newValue) -> {
            if (circle == startCircle && node == attachedNode1) {
                updateNodePositionToEdge(circle, node);
            } else if (circle == endCircle && node == attachedNode2) {
                updateNodePositionToEdge(circle, node);
            }
        });
    }




    private void updateAttachedNode(Circle circle, Node node) {
        useCaseController ctrl = (useCaseController) ViewIController.getUseCaseController(node);
        String newUseCaseName;
        if (circle == startCircle) {
            newUseCaseName = ctrl.getUseCaseName();
            updateIncludeLink(useCaseName1, newUseCaseName, useCaseName2, useCaseName2);

            useCaseName1 = newUseCaseName;
            attachedNode1 = node;
            bindCornerToNode(startCircle, attachedNode1);
        } else if (circle == endCircle) {
            newUseCaseName = ctrl.getUseCaseName();
            updateIncludeLink(useCaseName1, useCaseName1, useCaseName2, newUseCaseName);

            useCaseName2 = newUseCaseName;
            attachedNode2 = node;
            bindCornerToNode(endCircle, attachedNode2);
        }

        System.out.println("Snapped " + (circle == startCircle ? "Corner 1" : "Corner 2")
                + " to UseCase: " + ctrl.getUseCaseName());
    }


    @Override
    public Double[] getCoordinates() {
        Double []includeLinkCoordinates = new Double[2];
        includeLinkCoordinates[0] = includeGroup.getLayoutX();
        includeLinkCoordinates[1] = includeGroup.getLayoutY();
        return includeLinkCoordinates;
    }

    @Override
    public String[] getClassesName() {
        throw new UnsupportedOperationException("Not implemented! as it is only for Class Diagram. ");
    }
    public String[] getUseCaseNames(){

        return new String[]{useCaseName1, useCaseName2};
    }
}
