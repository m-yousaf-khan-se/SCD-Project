package controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.List;

public class DeleteHandler extends ControllerClass {

    private static Node selectedNode; // Stores the currently selected node

    // Add delete functionality to a single node
    public static void add(Node node, Pane canvas, Scene scene) {
        node.setOnMouseClicked(event -> handleMouseClicked(event, node)); // Set node selection
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> handleKeyPressed(event, canvas)); // Listen for delete key
    }

    // Add delete functionality to multiple nodes
    public static void addAll(Pane canvas, Scene scene, List<Node> nodes) {
        for (Node node : nodes) {
            add(node, canvas, scene);
        }
    }

    // Handles node selection
    private static void handleMouseClicked(MouseEvent event, Node node) {
        selectedNode = node; // Set clicked node as the selected node
        System.out.println("Selected node: " + node.getId());
        event.consume(); // Prevent further propagation of the event
    }

    // Handles key press events
    private static void handleKeyPressed(KeyEvent event, Pane canvas) {
        if (event.getCode() == KeyCode.DELETE && selectedNode != null) {
            System.out.println("Deleting node: " + selectedNode.getId());
            canvas.getChildren().remove(selectedNode); // Remove the node from the canvas
            ViewIController.removeNode(selectedNode); // Remove from node-controller mapping
            selectedNode = null; // Reset selection
        }
    }
}