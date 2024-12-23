package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.classDiagramControllers.*;
import controllers.useCaseDiagramControllers.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;


import MainClass.scdprojectupdated.ApplicationMain;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.usecase.Association;
import presenter.classDiagramPresenters.ClassDiagramPresenter;
import presenter.useCaseDiagramPresenters.UseCaseDiagramPresenter;

import javax.imageio.ImageIO;

public class ViewIController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane MainViewRightBorderArea;

    @FXML
    private ScrollPane paneCanvasScrollPane;
    @FXML
    private Pane paneCanvas;

    @FXML
    private MenuItem generateJavaCodeFromClassDiagramMenuItem; //using this fx:id just to enable and disable this option

    protected static HashMap<Node, IController> canvasClassNodes = new HashMap<Node, IController>();
    protected static HashMap<Node, IController> canvasUseCaseNodes = new HashMap<Node, IController>();

    private static ViewIController instance; //for using sigleton pattern

    private static ClassDiagramPresenter classDiagramPresenter;
    private static UseCaseDiagramPresenter useCaseDiagramPresenter;

    private static Stage OwnerWin;

    boolean CurrentDiagramSaved = false;

    private void bindCanvasToScrollPane() {
        // Bind the width and height of the canvas to the viewport of the ScrollPane
        paneCanvas.prefWidthProperty().bind(paneCanvasScrollPane.viewportBoundsProperty().map(bounds -> bounds.getWidth()));
        paneCanvas.prefHeightProperty().bind(paneCanvasScrollPane.viewportBoundsProperty().map(bounds -> bounds.getHeight()));
    }

    // ------------------------Not useable


    // ------------------------------------

    //----------------------------of Classe Diagram------------------------------------
    public static void storeClassController(Node node, IController controller) {
        instance.canvasClassNodes.put(node, controller);

        if (controller instanceof UMLClassIController) {
            UMLClassIController classController = (UMLClassIController) controller;
            instance.classDiagramPresenter.addClass(classController.getUMLClassName());
        } else if (controller instanceof AggregationIController) {
            AggregationIController aggregationController = (AggregationIController) controller;
            String[] classes = aggregationController.getClassesName();
            instance.classDiagramPresenter.addAggregation(classes[0], classes[1]);
        } else if (controller instanceof AssociationIController) {
            AssociationIController associationController = (AssociationIController) controller;
            String[] classes = associationController.getClassesName();
            String[] multiplicities = associationController.getMultiplicities();
            instance.classDiagramPresenter.addAssociation(classes[0], multiplicities[0], classes[1], multiplicities[1]);
        } else if (controller instanceof CompositionIController) {
            CompositionIController compositionController = (CompositionIController) controller;
            String[] classes = compositionController.getClassesName();
            instance.classDiagramPresenter.addComposition(classes[0], classes[1]);
        } else if (controller instanceof GeneralizationIController) {
            GeneralizationIController generalizationController = (GeneralizationIController) controller;
            String[] classes = generalizationController.getClassesName();
            instance.classDiagramPresenter.addInherritance(classes[0], classes[1]);
        } else {
            System.err.println("Unsupported controller type: " + controller.getClass().getName());
        }
    }


    //fetch only the components of class
    public Double[] getClassComponentCoordinates(String styleClass, String className)
    {
        for(Map.Entry<Node, IController> entry : canvasClassNodes.entrySet())
        {
            if(!entry.getKey().getStyleClass().contains(styleClass))
                continue;

            if(entry.getValue().getClassesName().equals(className))
                return entry.getValue().getCoordinates();
        }
        return null;
    }
    //can fetch all the components of class Diagram through their styleClass and their connected classes
    public Double[] getClassComponentCoordinates(String styleClass, String className1, String className2)
    {
        for(Map.Entry<Node, IController> entry : canvasClassNodes.entrySet())
        {
            if(!entry.getKey().getStyleClass().contains(styleClass))
                continue;

            String []classesNames = entry.getValue().getClassesName();

            if(classesNames != null &&
                    (classesNames[0].equals(className1) && classesNames[1].equals(className2)) ||
                        (classesNames[1].equals(className1) && classesNames[2].equals(className2)))
            {
                return entry.getValue().getCoordinates();
            }
        }
        return null;
    }
//Protected methods are being called in respective Controller Class( for now UMLClassController)
    //----------------------of Classes
    protected void addOrUpdateClassName(String oldName, String newName) {
        classDiagramPresenter.updateClassName(oldName, newName);
    }

    protected void addOrUpdateMethodToClass(String className, String oldMethodDetails, String newMethodDetails) {
        classDiagramPresenter.addClassMethod(className, oldMethodDetails ,newMethodDetails);
    }

    protected void addOrUpdateFieldToClass(String className, String oldFieldName, String newFieldName) {
        classDiagramPresenter.addOrUpdateClassField(className, oldFieldName, newFieldName);
    }

    protected void removeMethodFromClass(String className, String methodDetails) {
        classDiagramPresenter.removeClassMethod(className, methodDetails);
    }

    protected void removeFieldFromClass(String className, String fieldName) {
        classDiagramPresenter.removeClassField(className, fieldName);
    }

    protected static void removeClass(String className)
    {
        classDiagramPresenter.removeClass(className);
    }

    //----------------------of Aggregation
    protected void updateAggregation(String className1, String newClassName1, String className2, String newClassName2) {
        classDiagramPresenter.updateAggregation(className1, newClassName1, className2, newClassName2);
    }

    protected static void removeAggregation(String className1, String className2)
    {
        classDiagramPresenter.removeAggregation(className1, className2);
    }

    //----------------------of Association
    protected void updateAssociation(String className1, String newClassName1, String className2, String newClassName2) {
        classDiagramPresenter.updateAssociation(className1, newClassName1, className2, newClassName2);
    }

    protected void updateAssociationMultiplicity(String className1, String multiplicity1, String className2, String multiplicity2){
        classDiagramPresenter.updateAssociationMultiplicity(className1, multiplicity1, className2, multiplicity2);
    }

    protected static void removeAssociation(String className1, String className2)
    {
        classDiagramPresenter.removeAssociation(className1, className2);
    }
    //----------------------of Composition
    protected void updateComposition(String className1, String newClassName1, String className2, String newClassName2) {
        classDiagramPresenter.updateComposition(className1, newClassName1, className2, newClassName2);
    }
    protected static void removeComposition(String className1, String className2)
    {
        classDiagramPresenter.removeComposition(className1, className2);
    }
    //----------------------of Generalization

    protected void updateGeneratlization(String className1, String newClassName1, String className2, String newClassName2) {
        classDiagramPresenter.updateinherritance(className1, newClassName1, className2, newClassName2);
    }
    protected static void removeGeneralization(String className1, String className2)
    {
        classDiagramPresenter.removeInherritance(className1, className2);
    }


    //----------------------------------------------------------------UseCase----------
    public static void storeUseCaseController(Node node, IController controller)
    {
        //Stores nodes in useCase HashMap
        instance.canvasUseCaseNodes.put(node, controller);
        //Stores Actors in arraylist and respective diagram model.
        if(controller instanceof ActorController)
        {
            ActorController actorController = (ActorController)controller;
            instance.useCaseDiagramPresenter.addActor(actorController.getActorName());
        }
        else if(controller instanceof useCaseController)
        {
            useCaseController useCase_Controller = (useCaseController)controller;
            instance.useCaseDiagramPresenter.addUseCase(useCase_Controller.getUseCaseName());
        }
        else if (controller instanceof includeIController) {
            includeIController includeController = (includeIController) controller;
            String[] useCases = includeController.getUseCaseNames();
            instance.useCaseDiagramPresenter.addInclude(useCases[0], useCases[1]);
        }
        else if (controller instanceof extendIController) {
            extendIController extendController = (extendIController) controller;
            String[] useCases = extendController.getUseCaseNames();
            instance.useCaseDiagramPresenter.addExtend(useCases[0], useCases[1]);
        }
        else if (controller instanceof useCaseAssociationIController) {
            useCaseAssociationIController useCaseAssociationController = (useCaseAssociationIController) controller;
            String[] actorAndUseCaseNames = useCaseAssociationController.getActorAndUseCaseNames();
            instance.useCaseDiagramPresenter.addAssociation(actorAndUseCaseNames[0], actorAndUseCaseNames[1]);
        }

    }
    //----------------------------------------------------------------For Actor----------
    protected void addOrUpdateActorName(String oldName, String newName) {
        useCaseDiagramPresenter.updateActorName(oldName, newName);
    }
    protected static void removeActor(String name){
        useCaseDiagramPresenter.removeActor(name);
    }
    //----------------------------------------------------------------For UseCase----------
    protected void addOrUpdateUseCaseName(String oldName, String newName) {
        useCaseDiagramPresenter.updateUseCaseName(oldName, newName);
    }
    protected static void removeUseCase(String name){
        useCaseDiagramPresenter.removeUseCase(name);
    }
    //----------------------------------------------------------------For UseCaseAssociation----------

    protected void updateUseCaseAssociation(String oldActorName, String newactorName, String olduseCaseName, String newuseCaseName) {
       useCaseDiagramPresenter.updateAssociation(oldActorName, newactorName, olduseCaseName, newuseCaseName);
    }
    protected static void removeUseCaseAssociation(String actorName, String useCaseName) {
        useCaseDiagramPresenter.removeAssociation(actorName, useCaseName);
    }
    //----------------------------------------------------------------For Include Link----------

    protected void updateIncludeLink(String oldUseCaseName1, String newUseCaseName1, String oldUseCaseName2, String newUseCaseName2) {
      useCaseDiagramPresenter.updateInclude(oldUseCaseName1, newUseCaseName1, oldUseCaseName2, newUseCaseName2);
    }
    protected static void removeIncludeLink(String useCaseName1, String useCaseName2){
        useCaseDiagramPresenter.removeInclude(useCaseName1, useCaseName2);
    }

    //----------------------------------------------------------------For Extend Link-----------

    protected void updateExtendLink(String oldUseCaseName1, String newUseCaseName1, String oldUseCaseName2, String newUseCaseName2) {
     useCaseDiagramPresenter.updateExtend(oldUseCaseName1, newUseCaseName1, oldUseCaseName2, newUseCaseName2);
    }
    protected static void removeExtendLink(String useCaseName1, String useCaseName2){
        useCaseDiagramPresenter.removeExtend(useCaseName1, useCaseName2);
    }

    public static IController getClassController(Node node)
    {
        return instance.canvasClassNodes.get(node);
    }
    public static IController getUseCaseController(Node node)
    {
        return instance.canvasUseCaseNodes.get(node);
    }

    public static void removeNode(Node node)
    {
        if(instance.canvasClassNodes.get(node) != null)
            instance.canvasClassNodes.remove(node);
        else if(instance.canvasUseCaseNodes.get(node) != null)
            instance.canvasUseCaseNodes.remove(node);
    }

    public static ObservableList<Node> getCanvasChildren() {
        return instance.paneCanvas.getChildren();
    }

    @FXML
    private void aboutUMLEditorListener(ActionEvent event) {

    }

    @FXML
    private void createNewClassDiagramListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/ClassDiagramTools.fxml"));
        instance.MainViewRightBorderArea.setContent(loader.load());
    }

    @FXML
    private void createNewUseCaseDiagramListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/UseCaseDiagramsTools.fxml"));
        MainViewRightBorderArea.setContent(loader.load());
    }

    @FXML
    private void exportAsPNGListener(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Diagram as PNG");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                // Capture the diagram as an image
                WritableImage image = paneCanvas.snapshot(null, null);
                // Convert JavaFX WritableImage to BufferedImage
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                // Write the image to the selected file
                ImageIO.write(bufferedImage, "png", file);

                System.out.println("Diagram exported successfully to " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                // Display an error message to the user
                System.out.println("Error exporting diagram: " + e.getMessage());
            }
        }
    }


    @FXML
    private void loadExistingProject(ActionEvent event) throws IOException {

        if(!paneCanvas.getChildren().isEmpty() && !CurrentDiagramSaved)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Information");
            alert.setContentText("Your current project is not saved. If you load a new project, the current project will be lost!");

            // Show the alert and wait for a response
            Optional<ButtonType> result = alert.showAndWait();

            // Handle the result
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("User chose OK - proceed to load the new project.");
                paneCanvas.getChildren().clear();
                canvasClassNodes.clear();
                canvasUseCaseNodes.clear();

            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                System.out.println("User chose Cancel - keep the current project.");
                return;
            }
        }

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Project");

// Set extension filter to .json
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonFilter);

// Open the open dialog
        File file = fileChooser.showOpenDialog(OwnerWin);


        if (file != null) {

            if (!file.getName().endsWith(".json")) {
                file = new File(file.getAbsolutePath() + ".json");
            }

            System.out.println("File path to load project is set successfully!");

            if(!generateJavaCodeFromClassDiagramMenuItem.isDisable()) //if the option to generate Java code is available this means that the user was creating class Diagram
            {
                System.out.println("Passing the follwing path to class Presenter to load project from file: "+ file.getPath().toString());
                if(!classDiagramPresenter.loadClassDiagram(file)){
                    showAlert(Alert.AlertType.ERROR, "Error", "Unable to load project from this file!");
                    return;
                }
            }
            else // when user was making a Use Case Diagram
            {
                System.out.println("Passing the follwing path to useCase Presenter to load project from file: "+ file.getPath().toString());
                if(!useCaseDiagramPresenter.loadUseCaseDiagram(file)){
                    showAlert(Alert.AlertType.ERROR, "Error", "Unable to load project from this file!");
                    return;
                }
            }
        }
        else
        {
            System.err.println("File is not set!");
        }
    }

    @FXML
    private void generateJavaCodeFromClassDiagramListener(ActionEvent event) throws IOException {
        // Create a new stage for the window
        Stage stage = new Stage();

        // Fetch the generated Java code from the presenter
        String javaCode = classDiagramPresenter.generateJavaCodeFromClassDiagram();
        System.out.println("Java code is fetched from the presenter");

        // Load the FXML and initialize the controller
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/TextAreaForGeneratedJavaCode.fxml"));
        Parent root = loader.load(); // This initializes the controller

        // Get the controller and set the text in the TextArea
        generateCodeWindowController controller = loader.getController();
        controller.setTextArea(javaCode);

        // Set the scene and show the stage
        stage.setScene(new Scene(root));
        stage.setTitle("Generated Java Code");
        stage.show();
    }


    @FXML
    private void setStatusForGenerateJavaCodeFromClassDiagramStatusListener(Event event) {
        VBox vb = (VBox) MainViewRightBorderArea.getContent();
        if( vb != null && (((Button)vb.getChildren().get(1)).getText().equals("Class")))
        {
            generateJavaCodeFromClassDiagramMenuItem.setDisable(false);
            System.out.println("Generate Code Menu enabled");
        }
        else
        {
            generateJavaCodeFromClassDiagramMenuItem.setDisable(true);
            System.out.println("Generate Code Menu disabled");
        }
    }

    @FXML
    private void saveProjectListener(ActionEvent event) {

        if(paneCanvas.getChildren().isEmpty())
        {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Please add something on Canvas to Save!");
            return;
        }

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save current Project");

        // Set extension filter to .json
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonFilter);

        // Open the save dialog
        File file = fileChooser.showSaveDialog(OwnerWin);

        if (file != null) {

            if (!file.getName().endsWith(".json")) {
                file = new File(file.getAbsolutePath() + ".json");
            }

            System.out.println("File path to save project is set successfully!");
//            classDiagramPresenter.saveDiagramProject(file);

            if(!generateJavaCodeFromClassDiagramMenuItem.isDisable()) //if the option to generate Java code is available this means that the user was creating class Diagram
            {
                //setting up co-ordinates before passing file to store data.
                System.out.println("Passing Co-ordinates of the Class Diagram components to the presenter!");
                for(Map.Entry<Node, IController> entry : canvasUseCaseNodes.entrySet())
                {
                    if(entry.getKey().getStyleClass().contains("uml-class"))
                    {
                        UMLClassIController ctrler = (UMLClassIController)entry.getValue();
                        Double []coordinates = ctrler.getCoordinates();
                        String className = ctrler.getUMLClassName();
                        classDiagramPresenter.setClassCoordinates(className, coordinates[0], coordinates[1]);
                    }
                }
                System.out.println("Passing the follwing path to Presenter to save file: "+ file.getPath().toString());
                CurrentDiagramSaved = classDiagramPresenter.saveClassDiagramProject(file);

            }
            else // when user was making a Use Case Diagram
            {
                //setting up co-ordinates before passing file to store data.
                System.out.println("Passing Co-ordinates of the UseCase Diagram components to the presenter!");
                //For UseCases
                for(Map.Entry<Node, IController> entry : canvasUseCaseNodes.entrySet())
                {
                    if(entry.getKey().getStyleClass().contains("uml-useCase"))
                    {
                        useCaseController ctrler = (useCaseController)entry.getValue();
                        Double []coordinates = ctrler.getCoordinates();
                        String useCaseName = ctrler.getUseCaseName();
                        useCaseDiagramPresenter.setUseCaseCoordinates(useCaseName, coordinates[0], coordinates[1]);
                    }
                }
                //For Actors
                for(Map.Entry<Node, IController> entry : canvasUseCaseNodes.entrySet())
                {
                    if(entry.getKey().getStyleClass().contains("usecase-actor"))
                    {
                        ActorController ctrler = (ActorController)entry.getValue();
                        Double []coordinates = ctrler.getCoordinates();
                        String actorName = ctrler.getActorName();
                        useCaseDiagramPresenter.setActorCoordinates(actorName, coordinates[0], coordinates[1]);
                    }
                }


                System.out.println("Passing the follwing path to Presenter to save file: "+ file.getPath().toString());
                CurrentDiagramSaved = useCaseDiagramPresenter.saveDiagramProject(file);
            }
        }
        else
        {
            System.err.println("File is not set!");
        }
    }

    public void setOwnerWindow(Stage stage)
    {
        OwnerWin = stage;
    }

    @FXML
    void initialize() {
        //Following function will bind our paneCanvas according to the size of screen means jitna scrollPane ka size hoga utna paneCanvas ka size hoga.
        bindCanvasToScrollPane();
        //Actually this code is loaded from the scene builder sample Controller. And I don't why I get these two line below.
        assert MainViewRightBorderArea != null : "fx:id=\"MainViewRightBorderArea\" was not injected: check your FXML file 'view.fxml'.";
        assert paneCanvas != null : "fx:id=\"paneCanvas\" was not injected: check your FXML file 'view.fxml'.";

        instance = this;

        // Adding a listener to the children list of the paneCanvas

        //For Delete functionality of nodes.
        instance.paneCanvas.getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Node newChild : change.getAddedSubList()) {
                        DragAndDropHandler.add(newChild); // Add drag-and-drop functionality
                        DeleteHandler.add(newChild, paneCanvas, paneCanvas.getScene()); // Add delete functionality
                        System.out.println("A new child was added: " + newChild.getClass().getName());
                    }
                }

                if (change.wasRemoved()) {
                    for (Node removedChild : change.getRemoved()) {
                        System.out.println("A child was removed: " + removedChild.getClass().getName());
                    }
                }
            }
        });
    }

    public static Pane getPaneCanvas() {
        if (instance == null) {
            throw new IllegalStateException("ViewController has not been initialized yet.");
        }
        return instance.paneCanvas;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: No header
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setPresenter(ClassDiagramPresenter classPresenter, UseCaseDiagramPresenter useCasePresenter) {
        classDiagramPresenter = classPresenter;
        useCaseDiagramPresenter = useCasePresenter;

        if(useCaseDiagramPresenter == null || classDiagramPresenter == null)
        {
            System.err.println("Presenters are not set in the View!!");
        }
        else
        {
            System.out.println("Preseters are set successfully in the View!");
        }
    }
//----------------Deserializaton For UseCases--------
    public static void loadUseCase(String name, Double x, Double y) throws IOException {
        System.out.println("Creating Use Case");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/useCasePane.fxml"));
        Parent container = loader.load();
        useCaseController controller=loader.getController();

        controller.setUseCaseName(name);
        controller.setCoordinates(x,y);
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeUseCaseController(container, controller);
    }
    public static void loadActor(String name, Double x, Double y) throws IOException {
        System.out.println("Creating Actor");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/actor.fxml"));
        Parent container = loader.load();
        ActorController controller=loader.getController();

        controller.setCoordinates(x,y);
        controller.setActorName(name);
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeUseCaseController(container, controller);
    }
    public static void loadUseCaseAssociation(String actorName, String useCaseName) throws IOException {
        System.out.println("Creating Association Link");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/association.fxml"));
        Parent container = loader.load();
        useCaseAssociationIController controller=loader.getController();

        controller.setUseCaseName(useCaseName);
        controller.setActorName(actorName);

        for(Map.Entry<Node,IController>entry:canvasUseCaseNodes.entrySet()){
            if(!(entry.getValue() instanceof useCaseAssociationIController)){
                continue;
            }
            useCaseAssociationIController ctrl=(useCaseAssociationIController)entry.getValue();
            String[]useCaseAndActorNames= ctrl.getActorAndUseCaseNames();

            if(useCaseAndActorNames[0].equals(actorName)){
                controller.setAttachedActorNode(entry.getKey());
            }
            if(useCaseAndActorNames[1].equals(useCaseName)){
                controller.setAttachedUseCaseNode(entry.getKey());
            }
        }
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeUseCaseController(container, controller);
    }
    public static void loadInclude(String useCaseName1, String useCaseName2) throws IOException {
        System.out.println("Creating Include Link");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/include.fxml"));
        Parent container = loader.load();
        includeIController controller=loader.getController();

        controller.setUseCaseName1(useCaseName1);
        controller.setUseCaseName2(useCaseName2);

        for(Map.Entry<Node,IController>entry:canvasUseCaseNodes.entrySet()){
            if(!(entry.getValue() instanceof includeIController)){
                continue;
            }
            includeIController ctrl=(includeIController)entry.getValue();
            String[]useCaseNames= ctrl.getUseCaseNames();

            if(useCaseNames[0].equals(useCaseName1)){
                controller.setAttachedNode1(entry.getKey());
            }
            if(useCaseNames[1].equals(useCaseName2)){
                controller.setAttachedNode2(entry.getKey());
            }
        }
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeUseCaseController(container, controller);
    }
    public static void loadExtend(String useCaseName1, String useCaseName2) throws IOException {
        System.out.println("Creating Extend Link");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/extend.fxml"));
        Parent container = loader.load();
        extendIController controller=loader.getController();

        controller.setUseCaseName1(useCaseName1);
        controller.setUseCaseName2(useCaseName2);
        for(Map.Entry<Node,IController>entry:canvasUseCaseNodes.entrySet()){
            if(!(entry.getValue() instanceof extendIController)){
                continue;
            }
            extendIController ctrl=(extendIController)entry.getValue();
            String[]useCaseNames= ctrl.getUseCaseNames();

            if(useCaseNames[0].equals(useCaseName1)){
                controller.setAttachedNode1(entry.getKey());
            }
            if(useCaseNames[1].equals(useCaseName2)){
                controller.setAttachedNode2(entry.getKey());
            }
        }
        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeUseCaseController(container, controller);
    }


    //------------------------------------Data Loading from class models --------------------------------
    public static void loadClass(String name, String[] fieldDetails, String[] methodDetails, Double x, Double y) throws IOException {
        //code of creating class
        System.out.println("Creating empty class");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/UMLclass.fxml"));
        Node container = loader.load();
        UMLClassIController controller = loader.getController();

        controller.setClassName1(name);
        for(String field : fieldDetails)
        {
            controller.addNewField(field);
        }
        for(String method : methodDetails)
        {
            controller.addNewMethod(method);
        }
        controller.setCoordinates(x,y);

        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeClassController(container, controller);
    }

    public static void loadAggregation(String className1, String className2) throws IOException {
        System.out.println("Creating Aggregation");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/aggregation.fxml"));
        Parent container = loader.load();
        AggregationIController controller = loader.getController();

        controller.setClassName1(className1);
        controller.setClassName2(className2);
        for(Map.Entry<Node, IController> entry : canvasClassNodes.entrySet())
        {
            if(!(entry.getValue() instanceof AggregationIController))
                continue;

            AggregationIController actrl = (AggregationIController) entry.getValue();

            String []classNames = actrl.getClassesName();

            if(classNames[0].equals(className1))
                controller.setAttachedNode1(entry.getKey());

            if(classNames[1].equals(className1))
                controller.setAttachedNode2(entry.getKey());
        }

        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeClassController(container, controller);
    }

    public static void loadAssociation(String className1, String className2, String multiplicity1, String multipliciy2) throws IOException {
        System.out.println("Creating Association");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/association.fxml"));
        Parent container = loader.load();
        AssociationIController controller = loader.getController();

        controller.setClassName1(className1);
        controller.setClassName2(className2);
        for(Map.Entry<Node, IController> entry : canvasClassNodes.entrySet())
        {
            if(!(entry.getValue() instanceof AssociationIController))
                continue;

            AssociationIController actrl = (AssociationIController) entry.getValue();

            String []classNames = actrl.getClassesName();

            if(classNames[0].equals(className1))
                controller.setAttachedNode1(entry.getKey());

            if(classNames[1].equals(className1))
                controller.setAttachedNode2(entry.getKey());
        }

        controller.setMultiplicity1(multiplicity1);
        controller.setMultiplicity2(multipliciy2);

        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeClassController(container, controller);
    }

    public static void loadComposition(String className1, String className2) throws IOException {
        System.out.println("Creating Composition");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/composition.fxml"));
        Parent container = loader.load();
        CompositionIController controller = loader.getController();

        controller.setClassName1(className1);
        controller.setClassName2(className2);
        for(Map.Entry<Node, IController> entry : canvasClassNodes.entrySet())
        {
            if(!(entry.getValue() instanceof CompositionIController))
                continue;

            CompositionIController actrl = (CompositionIController) entry.getValue();

            String []classNames = actrl.getClassesName();

            if(classNames[0].equals(className1))
                controller.setAttachedNode1(entry.getKey());

            if(classNames[1].equals(className1))
                controller.setAttachedNode2(entry.getKey());
        }

        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeClassController(container, controller);
    }

    public static void loadGeneralization(String className1, String className2) throws IOException {
        System.out.println("Creating Generalization");
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/generalization.fxml"));
        Parent container = loader.load();
        GeneralizationIController controller = loader.getController();

        controller.setClassName1(className1);
        controller.setClassName2(className2);
        for(Map.Entry<Node, IController> entry : canvasClassNodes.entrySet())
        {
            if(!(entry.getValue() instanceof GeneralizationIController))
                continue;

            GeneralizationIController actrl = (GeneralizationIController) entry.getValue();

            String []classNames = actrl.getClassesName();

            if(classNames[0].equals(className1))
                controller.setAttachedNode1(entry.getKey());

            if(classNames[1].equals(className1))
                controller.setAttachedNode2(entry.getKey());
        }

        ViewIController.getPaneCanvas().getChildren().add(container);
        ViewIController.storeClassController(container, controller);
    }


}