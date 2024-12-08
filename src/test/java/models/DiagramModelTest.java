package models;

import static org.junit.jupiter.api.Assertions.*;

import models.classdiagram.Class;
import models.DiagramModel;
import models.Relationship;
import  models.classdiagram.Association;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiagramModelTest {

    private DiagramModel diagramModel;
    private Class classComponent1;
    private Class classComponent2;
    private Association relationship1;

    @BeforeEach
    public void setUp() {
        diagramModel = new DiagramModel();
        classComponent1 = new Class("ClassA");
        classComponent2 = new Class("ClassB");
        relationship1 = new Association(classComponent1, classComponent2,"related", 10.0, 20.0,"*","*1");
    }

    @Test
    public void testAddComponent() {
        // Initially, the components list should be empty
        assertEquals(0, diagramModel.getComponents().size());

        // Add classComponent1 to the diagram
        diagramModel.addComponent(classComponent1);

        // After adding the component, the list should contain one component
        assertEquals(1, diagramModel.getComponents().size());
        assertTrue(diagramModel.getComponents().contains(classComponent1));
    }

    @Test
    public void testRemoveComponent() {
        // Add classComponent1 first
        diagramModel.addComponent(classComponent1);

        // Now remove it
        diagramModel.removeComponent(classComponent1);

        // After removal, the components list should be empty
        assertEquals(0, diagramModel.getComponents().size());
        assertFalse(diagramModel.getComponents().contains(classComponent1));
    }

    @Test
    public void testAddRelationship() {
        // Initially, the relationships list should be empty
        assertEquals(0, diagramModel.getRelationships().size());

        // Add relationship1 to the diagram
        diagramModel.addRelationship(relationship1);

        // After adding the relationship, the list should contain one relationship
        assertEquals(1, diagramModel.getRelationships().size());
        assertTrue(diagramModel.getRelationships().contains(relationship1));
    }

    @Test
    public void testRemoveRelationship() {
        // Add relationship1 first
        diagramModel.addRelationship(relationship1);

        // Now remove the relationship
        diagramModel.removeRelationship(relationship1);

        // After removal, the relationships list should be empty
        assertEquals(0, diagramModel.getRelationships().size());
        assertFalse(diagramModel.getRelationships().contains(relationship1));
    }

    @Test
    public void testClear() {
        // Add a component and a relationship
        diagramModel.addComponent(classComponent1);
        diagramModel.addRelationship(relationship1);

        // Clear the diagram
        diagramModel.clear();

        // After clearing, both components and relationships lists should be empty
        assertEquals(0, diagramModel.getComponents().size());
        assertEquals(0, diagramModel.getRelationships().size());
    }

    @Test
    public void testGetComponents() {
        // Add classComponent1 to the diagram
        diagramModel.addComponent(classComponent1);

        // Retrieve the list of components and verify the added component is there
        assertNotNull(diagramModel.getComponents());
        assertEquals(1, diagramModel.getComponents().size());
        assertEquals(classComponent1, diagramModel.getComponents().get(0));
    }

    @Test
    public void testGetRelationships() {
        // Add relationship1 to the diagram
        diagramModel.addRelationship(relationship1);

        // Retrieve the list of relationships and verify the added relationship is there
        assertNotNull(diagramModel.getRelationships());
        assertEquals(1, diagramModel.getRelationships().size());
        assertEquals(relationship1, diagramModel.getRelationships().get(0));
    }
}
