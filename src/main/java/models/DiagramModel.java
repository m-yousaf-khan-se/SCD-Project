package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiagramModel implements IModel, Serializable {
    private List<Component> components; // List of components (e.g., classes, actors)
    private List<Relationship> relationships; // List of relationships (e.g., association, include)

    public DiagramModel() {
        components = new ArrayList<>();
        relationships = new ArrayList<>();
    }

    @Override
    public void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public void removeComponent(Component component) {
        components.remove(component);
    }

    @Override
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
    }

    @Override
    public void removeRelationship(Relationship relationship) {
        relationships.remove(relationship);
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
