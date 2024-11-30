package models;

//public interface IModel {
//}


import java.util.List;

public interface IModel {
    void addComponent(Component component); // Add a new component
    void removeComponent(Component component); // Remove an existing component
    List<Component> getComponents(); // Retrieve all components

    void addRelationship(Relationship relationship); // Add a new relationship
    void removeRelationship(Relationship relationship); // Remove an existing relationship
    List<Relationship> getRelationships(); // Retrieve all relationships

    void clear(); // Clear all components and relationships (reset the model)
}
