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

public  class useCaseAssociationIController extends ViewIController implements IController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group useCaseAssociationGroup;

    @FXML
    private Line associationLine;

    @FXML
    private Circle actorCircle;

    @FXML
    private Circle useCaseCircle;

    private Node attachedActorNode;
    private Node attachedUseCaseNode;

    String actorName="";
    String useCaseName="";

    @FXML
    void initialize() {
        setupAssociationLineBindings();
        setupCircleDragHandlers();
    }

    private void setupAssociationLineBindings() {
        associationLine.startXProperty().bind(actorCircle.layoutXProperty());
        associationLine.startYProperty().bind(actorCircle.layoutYProperty());

        associationLine.endXProperty().bind(useCaseCircle.layoutXProperty());
        associationLine.endYProperty().bind(useCaseCircle.layoutYProperty());
    }

    private void setupCircleDragHandlers() {
        actorCircle.setOnMouseDragged(event -> onCircleDragged(actorCircle, event));
        useCaseCircle.setOnMouseDragged(event -> onCircleDragged(useCaseCircle, event));
    }

    private void onCircleDragged(Circle circle, MouseEvent event) {
        double newX = useCaseAssociationGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
        double newY = useCaseAssociationGroup.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();

        double deltaX = Math.abs(circle.getLayoutX() - newX);
        double deltaY = Math.abs(circle.getLayoutY() - newY);

        if (deltaX > 1 || deltaY > 1) {
            circle.setLayoutX(newX);
            circle.setLayoutY(newY);

            // Snap the circle to the edge of the nearest node
            snapToNearestNodeEdge(circle);
        }

        event.consume();
    }

    private void snapToNearestNodeEdge(Circle circle) {
        Node nearestNode = findNearestNode(circle);

        if (nearestNode != null) {
            double threshold = 20; // Adjust this value as needed
            double distance = calculateDistanceToNode(circle, nearestNode.getBoundsInParent());

            if (distance < threshold) {
                updateCirclePositionToEdge(circle, nearestNode); // Attach to edge
                updateAttachedNode(circle, nearestNode);         // Update attached node
            }
        }
    }

    private Node findNearestNode(Circle circle) {
        Node nearestNode = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Node node : ViewIController.getCanvasChildren()) {
            if (!(node.getStyleClass().contains("usecase-actor") || node.getStyleClass().contains("uml-useCase"))) {
                continue; // Skip irrelevant nodes
            }

            Bounds bounds = node.getBoundsInParent();
            double distance = calculateDistanceToNode(circle, bounds);

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNode = node;
            }
        }

        return nearestNode;
    }

    private double calculateDistanceToNode(Circle circle, Bounds bounds) {
        double nearestX = Math.max(bounds.getMinX(), Math.min(circle.getLayoutX(), bounds.getMaxX()));
        double nearestY = Math.max(bounds.getMinY(), Math.min(circle.getLayoutY(), bounds.getMaxY()));

        return Math.hypot(circle.getLayoutX() - nearestX, circle.getLayoutY() - nearestY);
    }

    private void updateCirclePositionToEdge(Circle circle, Node node) {
        Bounds bounds = node.getBoundsInParent();
        double centerX = (bounds.getMinX() + bounds.getMaxX()) / 2;
        double centerY = (bounds.getMinY() + bounds.getMaxY()) / 2;

        double angle = Math.atan2(circle.getLayoutY() - centerY, circle.getLayoutX() - centerX);

        double radiusX = (bounds.getMaxX() - bounds.getMinX()) / 2;
        double radiusY = (bounds.getMaxY() - bounds.getMinY()) / 2;

        double edgeX = centerX + radiusX * Math.cos(angle);
        double edgeY = centerY + radiusY * Math.sin(angle);

        circle.setLayoutX(edgeX);
        circle.setLayoutY(edgeY);
    }
    private void bindCornerToNode(Circle circle, Node node) {
        if (node == null) return;

        // Listen for changes in the node's layout and update the circle's position
        node.layoutXProperty().addListener((observable, oldValue, newValue) -> {
            if (circle == actorCircle && node == attachedActorNode) {
                updateCirclePositionToEdge(circle, node);
            } else if (circle == useCaseCircle && node == attachedUseCaseNode) {
                updateCirclePositionToEdge(circle, node);
            }
        });

        node.layoutYProperty().addListener((observable, oldValue, newValue) -> {
            if (circle == actorCircle && node == attachedActorNode) {
                updateCirclePositionToEdge(circle, node);
            } else if (circle == useCaseCircle && node == attachedUseCaseNode) {
                updateCirclePositionToEdge(circle, node);
            }
        });
    }


    private void updateAttachedNode(Circle circle, Node node) {
        if (node == null) {
            System.err.println("Node is null.");
            return;
        }

        String newNodeName = null;
        IController ctrl = ViewIController.getUseCaseController(node);

        if (ctrl == null) {
            System.err.println("No controller found for the given node.");
            return;
        }

        if (ctrl instanceof ActorController) {
            newNodeName = ((ActorController) ctrl).getActorName();
            updateAssociation(actorName, newNodeName, useCaseName, useCaseName);
            actorName = newNodeName;
            attachedActorNode = node;
            bindCornerToNode(actorCircle, attachedActorNode);
        } else if (ctrl instanceof useCaseController) {
            newNodeName = ((useCaseController) ctrl).getUseCaseName();
            updateAssociation(actorName, actorName, useCaseName, newNodeName);
            useCaseName = newNodeName;
            attachedUseCaseNode = node;
            bindCornerToNode(useCaseCircle, attachedUseCaseNode);
        } else {
            System.err.println("Unexpected controller type for node: " + node.getClass().getSimpleName());
            return;
        }

        System.out.println("Snapped " + (circle == actorCircle ? "Actor Circle" : "Use Case Circle")
                + " to " + (ctrl instanceof ActorController ? "Actor: " : "Use Case: ") + newNodeName);
    }


    @Override
    public Double[] getCoordinates() {
        Double []useCaseCoordinates = new Double[2];
        useCaseCoordinates[0] = useCaseAssociationGroup.getLayoutX();
        useCaseCoordinates[1] = useCaseAssociationGroup.getLayoutY();
        return useCaseCoordinates;
    }

    @Override
    public String[] getClassesName() {
        throw new UnsupportedOperationException("Not implemented! as it is only for Class Diagram. ");
    }
    public String[] getActorAndUseCaseNames(){


        return new String[]{actorName, useCaseName};
    }
}
