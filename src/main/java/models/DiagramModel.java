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


}

