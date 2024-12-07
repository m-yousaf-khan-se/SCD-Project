package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DiagramModel implements IModel, Serializable {
    @JsonProperty("components")
    private List<Component> components; // List of components (e.g., classes, actors)
    private List<Relationship> relationships; // List of relationships (e.g., association, include)

    public DiagramModel() {
        components = new ArrayList<>();
        relationships = new ArrayList<>();
    }

    @Override
    public void addComponent(Component component) {
        components.add(component);
        System.out.println("Component Added");
    }


    @Override
    public void removeComponent(Component component) {
        components.remove(component);
        System.out.println("Component Removed");

    }

    @JsonProperty("components")
    @Override
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
        System.out.println("Relationship Added");

    }

    @Override
    public void removeRelationship(Relationship relationship) {
        relationships.remove(relationship);
        System.out.println("Relationship Removed");

    }

    @Override
    public List<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    public void clear() {
        components.clear();
        relationships.clear();
    }

//    public void saveFile(String filename) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
//            oos.writeObject(this);  // Serialize the DiagramModel object to the file
//            System.out.println("Diagram saved to file: " + filename);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Method to open and load DiagramModel object from a file
//    public static DiagramModel openFile(String filename) {
//        DiagramModel diagramModel = null;
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
//            diagramModel = (DiagramModel) ois.readObject();  // Deserialize the DiagramModel object from the file
//            System.out.println("Diagram loaded from file: " + filename);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return diagramModel;
//    }
}

