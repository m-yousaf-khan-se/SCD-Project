package com.boota.javaproject;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;


public class CanvasController {

    private Point initialPoint;
    private Point finalPoint;

    @FXML
    private Pane canvasPane;

    private Canvas canvas;
    private GraphicsContext gc;
    private ArrayList<Class> classes = new ArrayList<>();
    private ArrayList<Association> associations = new ArrayList<>();

    private String activeTool = null;
    private Map<String, BiConsumer<Double, Double>> drawActions = new HashMap<>();
    private Map<Node, Object> elementMap = new HashMap<>();

    private Node sourceNode = null;
    private Node targetNode = null;


    @FXML
    public void initialize() {
        canvas = new Canvas(canvasPane.getWidth(), canvasPane.getHeight());
        gc = canvas.getGraphicsContext2D();
        canvasPane.getChildren().add(canvas);

        drawActions.put("Class", this::drawClass);
        //drawActions.put("Comment", this::drawComment);
        //drawActions.put("Association", this::drawAssociation);


        canvasPane.setOnMouseMoved(this::trackMouseCoordinates);
        canvasPane.setOnMouseClicked(this::handleCanvasClick);
    }

    //----------------------------------

    //----------------------------------


    public void handleClassButtonClick() {activeTool = "Class";}

    public void handleAssociationButtonClick() {activeTool = "Association";}

    public void handleCommentButtonClick() {activeTool = "Comment";}

    private void trackMouseCoordinates(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
    }


    private void handleCanvasClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (event.getClickCount() == 2) {
            handleDoubleClick(x, y);
        }
        else if (activeTool != null) {
//            BiConsumer<Double, Double> drawAction = drawActions.get(activeTool);
//            if (drawAction != null) {
//                drawAction.accept(x, y);
//            }
            if (activeTool.equals("Association")) {
                // Handle association line drawing
                handleAssociationClick(x, y);
            } else {
                BiConsumer<Double, Double> drawAction = drawActions.get(activeTool);
                if (drawAction != null) {
                    drawAction.accept(x, y);
                }
            }
        }
    }

    private void handleAssociationClick(double x, double y) {
        // Find the node closest to the mouse click position
        for (Map.Entry<Node, Object> entry : elementMap.entrySet()) {
            Node node = entry.getKey();
            if (isWithinBounds(node, x, y)) {
                if (sourceNode == null) {
                    // First click - set source node
                    sourceNode = node;
                } else if (targetNode == null) {
                    // Second click - set target node
                    targetNode = node;
                    drawAssociationLine();
                }
                break;
            }
        }
    }

    private void drawAssociationLine() {
        if (sourceNode != null && targetNode != null) {
            // Get coordinates of both source and target nodes
            double sourceX = sourceNode.getLayoutX() + sourceNode.getBoundsInParent().getWidth() / 2;
            double sourceY = sourceNode.getLayoutY() + sourceNode.getBoundsInParent().getHeight() / 2;
            double targetX = targetNode.getLayoutX() + targetNode.getBoundsInParent().getWidth() / 2;
            double targetY = targetNode.getLayoutY() + targetNode.getBoundsInParent().getHeight() / 2;

            // Draw the association line using GraphicsContext
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(sourceX, sourceY, targetX, targetY);

            // Reset source and target nodes for the next association
            sourceNode = null;
            targetNode = null;
        }
    }

    private void handleDoubleClick(double x, double y) {
        for (Map.Entry<Node, Object> entry : elementMap.entrySet()) {
            Node node = entry.getKey();
            if (isWithinBounds(node, x, y)) {
                Object element = entry.getValue();
                showElementOptions(element);
                return;
            }
        }
    }


    private void drawClass(double x, double y) {
        activeTool = null;

        Point initialPoint = new Point(x, y);
        Class myClass = new Class(initialPoint);

        double initialWidth = 120;

        VBox classBox = new VBox();
        classBox.setLayoutX(x);
        classBox.setLayoutY(y);
        classBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 5; -fx-background-color: #e0e0e0;");

        Label classNameLabel = new Label(myClass.getClassName());
        classNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        VBox classNameBox = new VBox(classNameLabel);
        classNameBox.setMinWidth(initialWidth);
        classNameBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");

        VBox attributesBox = new VBox();
        attributesBox.setMinWidth(initialWidth);
        attributesBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");

        ArrayList<Attribute> attributes = myClass.getAttributes();
        for (Attribute attribute : attributes) {
            Label attributeLabel = new Label(attribute.toString());
            attributesBox.getChildren().add(attributeLabel);
        }

        VBox functionsBox = new VBox();
        functionsBox.setMinWidth(initialWidth);
        functionsBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");

        ArrayList<Function> functions = myClass.getFunctions();
        for (Function function : functions) {
            Label functionLabel = new Label(function.toString());
            functionsBox.getChildren().add(functionLabel);
        }

        double maxWidth = Math.max(initialWidth, Math.max(getMaxLabelWidth(classNameBox), Math.max(getMaxLabelWidth(attributesBox), getMaxLabelWidth(functionsBox))));
        classNameBox.setMinWidth(maxWidth);
        attributesBox.setMinWidth(maxWidth);
        functionsBox.setMinWidth(maxWidth);

        classBox.getChildren().addAll(classNameBox, attributesBox, functionsBox);
        canvasPane.getChildren().add(classBox);
        classes.add(myClass);
        elementMap.put(classBox, myClass); // Track element for double-click detection
    }


    private boolean isWithinBounds(Node node, double x, double y) {
        return x >= node.getLayoutX() && x <= node.getLayoutX() + node.getBoundsInParent().getWidth() &&
                y >= node.getLayoutY() && y <= node.getLayoutY() + node.getBoundsInParent().getHeight();
    }

    private void showElementOptions(Object element) {
        Stage optionsStage = new Stage();
        VBox optionBox = new VBox();

        // Apply CSS class to optionBox
        optionBox.getStyleClass().add("option-box");

        if (element instanceof Class) {
            Class clazz = (Class) element;
            optionBox.setPadding(new Insets(10));

            Button dragButton = new Button("Drag");
            Button editButton = new Button("Edit");
            Button deleteButton = new Button("Delete");

            // Apply CSS class to buttons
            dragButton.getStyleClass().add("option-button");
            editButton.getStyleClass().add("option-button");
            deleteButton.getStyleClass().add("option-button");

            editButton.setOnAction(event -> {
                showClassDetails(clazz);
                optionsStage.close();
            });
            deleteButton.setOnAction(event -> {
                classes.remove(clazz);
                elementMap.remove(clazz);
                redrawcanvas();
                optionsStage.close();
            });
            dragButton.setOnAction(event -> {
                optionsStage.close();
            });

            optionBox.getChildren().addAll(dragButton, editButton, deleteButton);

            Scene scene = new Scene(optionBox);
            scene.getStylesheets().add(getClass().getResource("/com/boota/javaproject/styles.css").toExternalForm());

            optionsStage.setScene(scene);
            optionsStage.initStyle(StageStyle.TRANSPARENT); // Make the stage background transparent
            optionsStage.show();
        }
    }


    private void showClassDetails(Object element) {
        Stage detailStage = new Stage();
        VBox detailBox = new VBox(10);

        if (element instanceof Class) {
            Class clazz = (Class) element;
            detailBox.setPadding(new Insets(10));

            // Class Name Editing
            detailBox.getChildren().add(new Label("Class Name:"));
            TextField classNameField = new TextField(clazz.getClassName());
            detailBox.getChildren().add(classNameField);

            // Attributes Section
            detailBox.getChildren().add(new Label("Attributes:"));
            VBox attributesBox = new VBox(5);
            updateAttributesBox(clazz, attributesBox);
            Button addAttributeButton = new Button("Add Attribute");
            addAttributeButton.setOnAction(e -> {
                Attribute newAttr = new Attribute("", "String"); // Default attribute
                clazz.addAttribute(newAttr);
                updateAttributesBox(clazz, attributesBox);
            });
            detailBox.getChildren().addAll(attributesBox, addAttributeButton);

            // Functions Section
            detailBox.getChildren().add(new Label("Functions:"));
            VBox functionsBox = new VBox(5);
            updateFunctionsBox(clazz, functionsBox);
            Button addFunctionButton = new Button("Add Function");
            addFunctionButton.setOnAction(e -> {
                Function newFunc = new Function("void", "newFunction"); // Default function
                clazz.addFunction(newFunc);
                updateFunctionsBox(clazz, functionsBox);
            });
            detailBox.getChildren().addAll(functionsBox, addFunctionButton);

            // Submit Button to Apply Changes
            Button submitButton = new Button("Submit");
            submitButton.setOnAction(event -> {
                // Apply changes to the original class instance
                clazz.setClassName(classNameField.getText());
                redrawcanvas();
                detailStage.close();
            });
            detailBox.getChildren().add(submitButton);
        }
        // Additional checks for other element types can be added here

        Scene scene = new Scene(detailBox, 300, 200);
        detailStage.setScene(scene);
        detailStage.setTitle("Selected Class");
        detailStage.show();
    }

    private void updateAttributesBox(Class clazz, VBox attributesBox) {
        attributesBox.getChildren().clear();
        for (Attribute attribute : clazz.getAttributes()) {
            HBox attrBox = new HBox(5);

            TextField nameField = new TextField(attribute.getName());
            TextField typeField = new TextField(attribute.getDataType());
            ComboBox<String> accessModifierBox = new ComboBox<>();
            accessModifierBox.getItems().addAll("public", "private", "protected");
            accessModifierBox.setValue(attribute.getAccessModifier());

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                clazz.removeAttribute(attribute);
                updateAttributesBox(clazz, attributesBox);
            });

            attrBox.getChildren().addAll(
                    new Label("Name:"), nameField,
                    new Label("Type:"), typeField,
                    new Label("Access:"), accessModifierBox,
                    deleteButton
            );
            attributesBox.getChildren().add(attrBox);

            // Update attribute on field change
            nameField.textProperty().addListener((obs, oldText, newText) -> attribute.setName(newText));
            typeField.textProperty().addListener((obs, oldText, newText) -> attribute.setDataType(newText));
            accessModifierBox.valueProperty().addListener((obs, oldVal, newVal) -> attribute.setAccessModifier(newVal));
        }
    }

    private void updateFunctionsBox(Class clazz, VBox functionsBox) {
        functionsBox.getChildren().clear();
        for (Function function : clazz.getFunctions()) {
            VBox funcBox = new VBox(5);

            TextField nameField = new TextField(function.getName());
            TextField returnTypeField = new TextField(function.getReturnType());
            ComboBox<String> accessModifierBox = new ComboBox<>();
            accessModifierBox.getItems().addAll("public", "private", "protected");
            accessModifierBox.setValue(function.getAccessModifier());

            // Manage parameters
            VBox parametersBox = new VBox(5);
            updateParametersBox(function, parametersBox);
            Button addParameterButton = new Button("Add Parameter");
            addParameterButton.setOnAction(e -> {
                function.addAttribute(new Attribute("param", "String"));
                updateParametersBox(function, parametersBox);
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                clazz.removeFunction(function);
                updateFunctionsBox(clazz, functionsBox);
            });

            funcBox.getChildren().addAll(
                    new Label("Function Name:"), nameField,
                    new Label("Return Type:"), returnTypeField,
                    new Label("Access:"), accessModifierBox,
                    new Label("Parameters:"), parametersBox, addParameterButton,
                    deleteButton
            );

            functionsBox.getChildren().add(funcBox);

            // Update function on field change
            nameField.textProperty().addListener((obs, oldText, newText) -> function.setName(newText));
            returnTypeField.textProperty().addListener((obs, oldText, newText) -> function.setReturnType(newText));
            accessModifierBox.valueProperty().addListener((obs, oldVal, newVal) -> function.setAccessModifier(newVal));
        }
    }
    private void updateParametersBox(Function function, VBox parametersBox) {
        parametersBox.getChildren().clear();
        for (Attribute parameter : function.getAttributes()) {
            HBox paramBox = new HBox(5);

            TextField paramNameField = new TextField(parameter.getName());
            TextField paramTypeField = new TextField(parameter.getDataType());

            Button deleteParamButton = new Button("Delete");
            deleteParamButton.setOnAction(e -> {
                function.removeAttribute(parameter);
                updateParametersBox(function, parametersBox);
            });

            paramBox.getChildren().addAll(
                    new Label("Param Name:"), paramNameField,
                    new Label("Type:"), paramTypeField,
                    deleteParamButton
            );
            parametersBox.getChildren().add(paramBox);

            // Update parameter on field change
            paramNameField.textProperty().addListener((obs, oldText, newText) -> parameter.setName(newText));
            paramTypeField.textProperty().addListener((obs, oldText, newText) -> parameter.setDataType(newText));
        }
    }

    private double getMaxLabelWidth(VBox vbox) {
        double maxWidth = 0;
        for (Node node : vbox.getChildren()) {
            if (node instanceof Label) {
                maxWidth = Math.max(maxWidth, ((Label) node).getWidth());
            }
        }
        return maxWidth + 10;
    }

    public void redrawcanvas() {
        canvasPane.getChildren().clear();
        for (Class myClass : classes) {
            redrawClass(myClass);
        }
    }


//    public void redrawCanvas() {
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the canvas before redrawing
//
//        // Redraw all classes
//        for (Class clazz : classes) {
//            double x = clazz.getInitialPoint().getX();
//            double y = clazz.getInitialPoint().getY();
//           // drawClass(x, y, clazz); // Pass position and class to draw
//            redrawClass(clazz);
//        }
//
//        // Redraw all associations
//        for (Association association : associations) {
//            Class sourceClass = association.getInitialClass();
//            Class targetClass = association.getFinalClass();
//
//            // Get the start and end points from the classes
//            double startX = sourceClass.getInitialPoint().getX();
//            double startY = sourceClass.getInitialPoint().getY();
//            double endX = targetClass.getInitialPoint().getX();
//            double endY = targetClass.getInitialPoint().getY();
//
//            // Draw the association between the classes
//            drawAssociation(startY, endX); // Use start and end points
//        }
//    }


    private void redrawClass(Class claz) {
        Point initialPoint = new Point(claz.getInitialPoint().getX(), claz.getInitialPoint().getY());
        double initialWidth = 120;
        claz.setClassName("changed class");
        VBox classBox = new VBox();
        classBox.setLayoutX(initialPoint.getX());
        classBox.setLayoutY(initialPoint.getY());
        classBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 5; -fx-background-color: #e0e0e0;");

        Label classNameLabel = new Label(claz.getClassName());
        classNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        VBox classNameBox = new VBox(classNameLabel);
        classNameBox.setMinWidth(initialWidth);
        classNameBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");

        VBox attributesBox = new VBox();
        attributesBox.setMinWidth(initialWidth);
        attributesBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");

        ArrayList<Attribute> attributes = claz.getAttributes();
        for (Attribute attribute : attributes) {
            Label attributeLabel = new Label(attribute.toString());
            attributesBox.getChildren().add(attributeLabel);
        }

        VBox functionsBox = new VBox();
        functionsBox.setMinWidth(initialWidth);
        functionsBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");

        ArrayList<Function> functions = claz.getFunctions();
        for (Function function : functions) {
            Label functionLabel = new Label(function.toString());
            functionsBox.getChildren().add(functionLabel);
        }

        double maxWidth = Math.max(initialWidth, Math.max(getMaxLabelWidth(classNameBox), Math.max(getMaxLabelWidth(attributesBox), getMaxLabelWidth(functionsBox))));
        classNameBox.setMinWidth(maxWidth);
        attributesBox.setMinWidth(maxWidth);
        functionsBox.setMinWidth(maxWidth);

        classBox.getChildren().addAll(classNameBox, attributesBox, functionsBox);
        canvasPane.getChildren().add(classBox);

        elementMap.put(classBox, claz);
    }

//    private void drawComment(double x, double y) {
//        activeTool = null;
//        // Implement similar logic to track comments as elements
//    }

    public void snapCanvas(){
        activeTool = null;
    }


//    private void drawAssociation(double x, double y) {
//        if (sourceNode == null) {
//            for (Map.Entry<Node, Object> entry : elementMap.entrySet()) {
//                Node node = entry.getKey();
//                if (isWithinBounds(node, x, y)) {
//                    sourceNode = node;
//                    break;
//                }
//            }
//        } else {
//            for (Map.Entry<Node, Object> entry : elementMap.entrySet()) {
//                Node node = entry.getKey();
//                if (isWithinBounds(node, x, y) && node != sourceNode) {
//                    targetNode = node;
//
//                    // Create Association
//                    Class sourceClass = (Class) elementMap.get(sourceNode);
//                    Class targetClass = (Class) elementMap.get(targetNode);
//                    Association association = new Association(sourceClass, targetClass);
//                    associations.add(association);
//
//                    // Draw the association (line between the two classes)
//                    gc.setStroke(Color.BLACK);
//                    gc.setLineWidth(2);
//                    gc.strokeLine(sourceNode.getLayoutX() + sourceNode.getBoundsInParent().getWidth() / 2,
//                            sourceNode.getLayoutY() + sourceNode.getBoundsInParent().getHeight() / 2,
//                            targetNode.getLayoutX() + targetNode.getBoundsInParent().getWidth() / 2,
//                            targetNode.getLayoutY() + targetNode.getBoundsInParent().getHeight() / 2);
//
//                    sourceNode = null;
//                    targetNode = null;
//
//                    break;
//                }
//            }
//        }
//    }

//    private void drawAssociationLine(Node sourceNode, Node targetNode) {
//        gc.setStroke(Color.BLACK);
//        gc.setLineWidth(2);
//
//        double startX = sourceNode.getLayoutX() + sourceNode.getBoundsInParent().getWidth() / 2;
//        double startY = sourceNode.getLayoutY() + sourceNode.getBoundsInParent().getHeight() / 2;
//        double endX = targetNode.getLayoutX() + targetNode.getBoundsInParent().getWidth() / 2;
//        double endY = targetNode.getLayoutY() + targetNode.getBoundsInParent().getHeight() / 2;
//
//        gc.strokeLine(startX, startY, endX, endY);
//    }

    private Node findNodeAtPosition(double x, double y) {
        for (Node node : elementMap.keySet()) {
            if (isWithinBounds(node, x, y)) {
                return node;
            }
        }
        return null;
    }



}




