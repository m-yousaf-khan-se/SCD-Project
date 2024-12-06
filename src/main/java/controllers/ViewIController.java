package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import controllers.classDiagramControllers.UMLClassIController;
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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
    private Pane paneCanvas;

    HashMap<Node, IController> canvasClassNodes = new HashMap<Node, IController>();
    HashMap<Node, IController> canvasUseCaseNodes = new HashMap<Node, IController>();

    private static ViewIController instance; //for using sigleton pattern

    private static ClassDiagramPresenter classDiagramPresenter;
    private static UseCaseDiagramPresenter useCaseDiagramPresenter;

    // ------------------------Not useable


    // ------------------------------------

    //----------------------------of Classe Diagram------------------------------------
    public static void storeClassController(Node node, IController controller)
    {
        instance.canvasClassNodes.put(node, controller);
        if(controller instanceof UMLClassIController)
        {
            UMLClassIController classController = (UMLClassIController)controller;
            instance.classDiagramPresenter.addClass(classController.getUMLClassName());
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
    //----------------------of Aggregation
    protected void addAggregation(String className1, String className2) {
    }

    protected void updateAggregation(String className1, String newClassName1, String className2, String newClassName2) {
    }

    //----------------------of Association
    protected void addAssociation(String className1, String className2) {

    }

    protected void updateAssociation(String className1, String newClassName1, String className2, String newClassName2) {

    }

    protected Double[] updateMultiplicity(String className1, String className2) {
        return new Double[0];
    }

    //----------------------of Composition

    protected void addComposition(String className1, String className2) {

    }

    protected void updateComposition(String className1, String newClassName1, String className2, String newClassName2) {

    }

    //----------------------of Generalization

    protected void addGeneratlization(String className1, String className2) {

    }

    protected void updateGeneratlization(String className1, String newClassName1, String className2, String newClassName2) {

    }

    //----------------------------------------------------------------
    protected static void storeUseCaseController(Node node, IController controller)
    {
        instance.canvasUseCaseNodes.put(node, controller);
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

    @FXML
    private MenuItem generateJavaCodeFromClassDiagramMenuItem; //using this fx:id just to enable and disable this option

    public static ObservableList<Node> getCanvasChildren() {
        return instance.paneCanvas.getChildren();
    }

    @FXML
    private void aboutUMLEditorListener(ActionEvent event) {

    }

    @FXML
    private void createNewClassDiagramListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/ClassDiagramTools.fxml"));
        MainViewRightBorderArea.setContent(loader.load());
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
    private void loadExistingProject(ActionEvent event) {

    }

    @FXML
    private void generateJavaCodeFromClassDiagramListener(ActionEvent event) {
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

    }

    @FXML
    void initialize() {
        //Actually this code is loaded from the scene builder sample Controller. And I don't why I get these two line below.
        assert MainViewRightBorderArea != null : "fx:id=\"MainViewRightBorderArea\" was not injected: check your FXML file 'view.fxml'.";
        assert paneCanvas != null : "fx:id=\"paneCanvas\" was not injected: check your FXML file 'view.fxml'.";

        instance = this;

        // Adding a listener to the children list of the paneCanvas

//For Delete functionality of nodes.
        paneCanvas.getChildren().addListener((ListChangeListener<Node>) change -> {
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


}