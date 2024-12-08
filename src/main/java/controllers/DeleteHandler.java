package controllers;

import controllers.classDiagramControllers.*;
import controllers.useCaseDiagramControllers.ActorController;
import controllers.useCaseDiagramControllers.extendIController;
import controllers.useCaseDiagramControllers.includeIController;
import controllers.useCaseDiagramControllers.useCaseController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import models.classdiagram.Aggregation;
import models.classdiagram.Association;

import java.util.List;

public class DeleteHandler extends ViewIController {

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

        if(selectedNode != null)
        {
            if(selectedNode instanceof Group)
            {
                ((Group)selectedNode).getChildren().forEach(n -> {
                    n.setStyle(""); // Highlighting the selected node
                });
            }
            else
            {
                selectedNode.setStyle("");
            }
        }

        if(node instanceof Group)
        {
            ((Group)node).getChildren().forEach(n -> {
                n.setStyle("-fx-border-color: blue; -fx-border-width: 2px;"); // Highlighting the selected node
            });
        }
        else
        {
            node.setStyle("-fx-border-color: blue; -fx-border-width: 2px;"); // Highlighting the selected node
        }

        selectedNode = node; // Set clicked node as the selected node
        System.out.println("Selected node: " + node.getId());
        event.consume(); // Prevent further propagation of the event
    }

    // Handles key press events
    private static void handleKeyPressed(KeyEvent event, Pane canvas) {
        if (event.getCode() == KeyCode.DELETE && selectedNode != null) {
            System.out.println("Deleting node: " + selectedNode.getId());
            canvas.getChildren().remove(selectedNode); // Remove the node from the canvas
            IController controller = ViewIController.canvasClassNodes.get(selectedNode);
            if(controller instanceof AggregationIController)
            {
                String []classesNames = controller.getClassesName();
                removeAggregation(classesNames[0], classesNames[1]);
            }
            else if(controller instanceof AssociationIController)
            {
                String []classesNames = controller.getClassesName();
                removeAssociation(classesNames[0], classesNames[1]);
            }
            else if(controller instanceof CompositionIController)
            {
                String []classesNames = controller.getClassesName();
                removeComposition(classesNames[0], classesNames[1]);
            }
            else if(controller instanceof GeneralizationIController)
            {
                String []classesNames = controller.getClassesName();
                removeGeneralization(classesNames[0], classesNames[1]);
            }
            else if(controller instanceof UMLClassIController)
            {
                removeClass(((UMLClassIController) controller).getUMLClassName());
            }

            // of Use Case Diagram

            else if(controller instanceof ActorController)
            {
                removeActor(((ActorController) controller).getActorName());
            }
            else if(controller instanceof extendIController)
            {
                String []useCaseNames = ((extendIController) controller).getUseCaseNames();
                removeExtendLink(useCaseNames[0], useCaseNames[1]);
            }
            else if(controller instanceof includeIController)
            {
                String []useCaseNames = ((includeIController) controller).getUseCaseNames();
                removeIncludeLink(useCaseNames[0], useCaseNames[1]);
            }
            else if(controller instanceof useCaseController)
            {
                removeUseCase(((useCaseController) controller).getUseCaseName());
            }
            else
            {
                System.err.println("You are deleting an undefined object. Therefore unable to delete!");
            }

            ViewIController.removeNode(selectedNode); // Remove from node-controller mapping
            selectedNode = null; // Reset selection
        }
    }
}
