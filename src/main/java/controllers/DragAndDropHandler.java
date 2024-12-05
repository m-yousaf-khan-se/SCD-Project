package controllers;

import controllers.classDiagramControllers.UMLClassIController;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import java.util.List;

public class DragAndDropHandler extends ControllerClass {

    // Static method to add drag-and-drop functionality to a single node
    public static void add(Node node) {
        node.setOnMousePressed(DragAndDropHandler::handleMousePressed);
        node.setOnMouseDragged(DragAndDropHandler::handleMouseDragged);
        node.setOnMouseReleased(DragAndDropHandler::handleMouseReleased);
    }

    // Static method to add drag-and-drop functionality to multiple nodes
    public static void addAll(List<Node> nodes) {
        for (Node node : nodes) {
            add(node); // Add drag-and-drop functionality to each node
        }
    }

    // Private static variables to track the node being dragged and offset values
    private static Node draggedNode;
    private static double offsetX, offsetY;

    // Private static method for mouse pressed event
    private static void handleMousePressed(MouseEvent event) {
        draggedNode = (Node) event.getSource();  // the node that is being dragged
        offsetX = event.getSceneX() - draggedNode.getLayoutX();
        offsetY = event.getSceneY() - draggedNode.getLayoutY();
    }

    // Private static method for mouse dragged event
    private static void handleMouseDragged(MouseEvent event) {
        if (draggedNode != null) {
            // Update node position based on mouse position
            draggedNode.setLayoutX(event.getSceneX() - offsetX);
            draggedNode.setLayoutY(event.getSceneY() - offsetY);

            if(draggedNode.getStyleClass().contains("uml-class"))
            {
                System.out.println("uml-class: " + ((UMLClassIController) ViewIController.getClassController(draggedNode)).getUMLClassName());
            }
        }
    }

    // Private static method for mouse released event
    private static void handleMouseReleased(MouseEvent event) {
        // Handle any drop-related logic (e.g., snapping, validation)
        draggedNode = null; // Clear the dragged node after the drop
    }
}
