package models.usecase;

import models.Component;
import models.Diagram;
import models.DiagramModel;
import models.Relationship;

public class UseCaseDiagram implements Diagram {
    private DiagramModel model; // Use DiagramModel for data storage

    public UseCaseDiagram() {
        this.model = new DiagramModel();
    }

//    @Override
//    public void display() {
//        for (Component component : model.getComponents()) {
//            component.display();
//        }
//        for (Relationship relationship : model.getRelationships()) {
//            relationship.display();
//        }
//    }

    @Override
    public void addComponent(Component component) {
        model.addComponent(component);
    }

    @Override
    public void removeComponent(Component component) {
        model.removeComponent(component);
    }

    public void addRelationship(Relationship relationship) {
        model.addRelationship(relationship);
    }

    public void removeRelationship(Relationship relationship) {
        model.removeRelationship(relationship);
    }
}

