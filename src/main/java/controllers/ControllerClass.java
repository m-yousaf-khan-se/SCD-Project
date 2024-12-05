package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.List;

public class ControllerClass {

   // ControllerClass(){};

    //Class Diagram Controllers
    //Agreggation functions
    public void setupAggregationLineBindings(){};

    public void setupCornerDragHandlers(){};

    public void onCornerDragged(MouseEvent event){};

    public void snapToNearestClass(Circle corner){};

    //public Node findNearestUMLClass(Circle corner){};

    //public double calculateDistanceToNode(Circle corner, Bounds bounds) {};

    public void updateCornerPosition(Circle corner, Node node) {};

    public void updateAttachedClass(Circle corner, Node node) {};

    public void bindCornerToNode(Circle corner, Node node) {};

    //Asociation functions
    public void setupMultiplicityChoiceBoxes() {};

    public void setupAssociationLineBindings() {};

    public void onMultiplicity1Changed(ActionEvent event) {};

    public void onMultiplicity2Changed(ActionEvent event) {};

    //Composition function
    public void setupcompositionLineBindings() {};

    //Generalization
    public void setupgeneralizationLineBindings() {};

    //ClassDiagramTool
    @FXML
    public void createAggregationListener(ActionEvent event) throws IOException {};

    @FXML
    public void createAssociationListener(ActionEvent event) throws IOException {};

    @FXML
    public void createClassListener(ActionEvent event) throws IOException {};

    @FXML
    public void createCompositionListener(ActionEvent event) throws IOException {};

    @FXML
    public void createGeneralizationListener(ActionEvent event) throws IOException{};

    @FXML
    public void initialize() {
    };

    //UML class Controller
    // public String getUMLClassName()
//    {
//        return className;
//    }

    //public ContextMenu createContextMenu(TextField textField) {};

    public void handleDeleteField(TextField textField) {};

    public void attachFocusChangeListener(TextField textField) {};

    public void setupTextField(TextField textField, String prompt) {};

//    @FXML
//    public void addNewFieldListener(ActionEvent event){};
//
//    @FXML
//    public void addNewMethodListener(ActionEvent event){};


    //UseCase Diagram Controller
    //ActorController
    public void setupActorBindings() {};

    public void setupDragHandlers() {};

    public void onActorDragged(MouseEvent event) {};

     //Extend and Include
     public void updateArrowLines() {};
     public void updateTextPosition() {};
    public void onLineDragged(MouseEvent event) {};
    public void onArrowDragged(MouseEvent event, boolean isLeftArrow) {};
    public void onNodeDragged(Node node, MouseEvent event, boolean isStart) {};
    public void snapToNearestNodeEdge(Node node, boolean isStart) {};
    //public Node findNearestUseCaseNode(Node node) {};
    //public double calculateDistanceToNode(Node node, Bounds bounds) {};
    public void updateNodePositionToEdge(Node node, Node nearestNode) {};
    //public double clamp(double value, double min, double max){};
    public void updateAttachedNode(Node node, Node nearestNode, boolean isStart) {};

    //UseCaseAssociation Controller
    public void setupCircleDragHandlers() {};
    public void onCircleDragged(Circle circle, MouseEvent event) {};
    public void snapToNearestNodeEdge(Circle circle) {};
    //private Node findNearestNode(Circle circle) {};
    //    private double calculateDistanceToNode(Circle circle, Bounds bounds) {};
        public void updateCirclePositionToEdge(Circle circle, Node node) {};
    public void updateAttachedNode(Circle circle, Node node){};

    //UseCaseDiagramToolController
    @FXML
    public void createActorListener(ActionEvent event) throws IOException {};

    @FXML
    public void createExtendListener(ActionEvent event) throws IOException {};

    @FXML
    public void createIncludeListener(ActionEvent event) throws IOException {};

    @FXML
    public void createMultiplicityListener(ActionEvent event) throws IOException {};
    @FXML
    public void createUseCaseLIstener(ActionEvent event) throws IOException {};

    //ViewController
    public static void storeController(Node node, ControllerClass controller){};

  //public static Controller getClassController(Node node){};
    public static void removeNode(Node node){};
    @FXML
    public void aboutUMLEditorListener(ActionEvent event){};
    @FXML
    public void createNewClassDiagramListener(ActionEvent event) throws IOException {};
    @FXML
    public void createNewUseCaseDiagramListener(ActionEvent event) throws IOException {};
    @FXML
    public void exportAsPNGListener(ActionEvent event) {};
    @FXML
    public void loadExistingProject(ActionEvent event){};
    @FXML
    public void generateJavaCodeFromClassDiagramListener(ActionEvent evt){};
    @FXML
    public void setStatusForGenerateJavaCodeFromClassDiagramStatusListener(Event event){};
    @FXML
    public void saveProjectListener(ActionEvent event){};
    //public static Pane getPaneCanvas() {}

//Drag and DropHandler
    public static void add(Node node){};
    public static void addAll(List<Node> nodes){};
    private static void handleMousePressed(MouseEvent event) {};
    private static void handleMouseDragged(MouseEvent event) {};
    private static void handleMouseReleased(MouseEvent event) {};


    }
