//package models;
//
//public interface Diagram {
//    void display(); // Display the diagram
//    void addComponent(Component component);
//    void removeComponent(Component component);
//}


package models;

public interface Diagram {
    //void display(); // Display the diagram
    void addComponent(Component component); // Add a component to the diagram
    void removeComponent(Component component); // Remove a component from the diagram
    void addRelationship(Relationship relationship); // Add a relationship between components
    void removeRelationship(Relationship relationship); // Remove a relationship
}
