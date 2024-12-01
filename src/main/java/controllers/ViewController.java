package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

public class ViewController implements Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane MainViewRightBorderArea;

    @FXML
    private Pane paneCanvas;

    HashMap<Node, Controller> canvasNodes = new HashMap<Node, Controller>();

    private static ViewController instance; //using sigleton pattern

    public static void storeController(Node node, Controller controller)
    {
        instance.canvasNodes.put(node, controller);
    }

    public static Controller getController(Node node)
    {
        return instance.canvasNodes.get(node);
    }

    public static void removeNode(Node node)
    {
        if(instance.canvasNodes.get(node) != null)
            instance.canvasNodes.remove(node);
    }

    @FXML
    private MenuItem generateJavaCodeFromClassDiagramMenuItem; //using this fx:id just to enable and disable this option

    public static ObservableList<Node> getCanvasChildren() {
        return instance.paneCanvas.getChildren();
    }

    @FXML
    void aboutUMLEditorListener(ActionEvent event) {

    }

    @FXML
    void createNewClassDiagramListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlClassViews/ClassDiagramTools.fxml"));
        MainViewRightBorderArea.setContent(loader.load());
    }

    @FXML
    void createNewUseCaseDiagramListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("Views/umlUseCaseViews/UseCaseDiagramsTools.fxml"));
        MainViewRightBorderArea.setContent(loader.load());
    }

    @FXML
    void exportAsPNGListener(ActionEvent event) {
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
    void loadExistingProject(ActionEvent event) {

    }

    @FXML
    void generateJavaCodeFromClassDiagramListener(ActionEvent event) {
    }

    @FXML
    void setStatusForGenerateJavaCodeFromClassDiagramStatusListener(Event event) {
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
    void saveProjectListener(ActionEvent event) {

    }

    @FXML
    void initialize() {
        //Actually this code is loaded from the scene builder sample Controller. And I don't why I get these two line below.
        assert MainViewRightBorderArea != null : "fx:id=\"MainViewRightBorderArea\" was not injected: check your FXML file 'view.fxml'.";
        assert paneCanvas != null : "fx:id=\"paneCanvas\" was not injected: check your FXML file 'view.fxml'.";

        instance = this;

        // Adding a listener to the children list of the paneCanvas
        paneCanvas.getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    // Process newly added children
                    for (Node newChild : change.getAddedSubList()) {
                        DragAndDropHandler.add(newChild);
                        System.out.println("A new child was added: " + newChild.getClass().getName());
                        // You can perform additional actions based on the type of the new child, etc.

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

}