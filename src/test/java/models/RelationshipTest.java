package models;

import static org.junit.jupiter.api.Assertions.*;

import models.Component;
import models.classdiagram.Class;
import models.classdiagram.Association;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RelationshipTest {

    @Test
    void testAssociationConstructorWithValidInputs() {
        // Arrange
        Component from = new Class("Component1");
        Component to = new Class("Component2");
        String label = "has";
        double labelX = 10.0;
        double labelY = 20.0;
        String multiplicity1 = "1";
        String multiplicity2 = "many";

        // Act
        Association association = new Association(from, to, label, labelX, labelY, multiplicity1, multiplicity2);

        // Assert
        assertNotNull(association);
        assertEquals(from, association.getFrom());
        assertEquals(to, association.getTo());
        assertEquals("Association", association.getType());
        assertEquals(label, association.getLabel());
        assertEquals(labelX, association.getLabelX());
        assertEquals(labelY, association.getLabelY());
        assertEquals(multiplicity1, association.getMultiplicity1());
        assertEquals(multiplicity2, association.getMultiplicity2());
    }

    @Test
    void testAssociationConstructorWithNullFromComponent() {
        // Arrange
        Component to = new Class("Component2");
        String label = "extends";
        double labelX = 10.0;
        double labelY = 20.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Association(null, to, label, labelX, labelY, "1", "many");
        });
    }

    @Test
    void testAssociationConstructorWithNullToComponent() {
        // Arrange
        Component from = new Class("Component1");
        String label = "extends";
        double labelX = 10.0;
        double labelY = 20.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Association(from, null, label, labelX, labelY, "1", "many");
        });
    }

    @Test
    void testSetAndGetFrom() {
        // Arrange
        Component from = new Class("Component1");
        Component newFrom = new Class("Component3");
        Component to = new Class("Component2");
        Association association = new Association(from, to, "label", 10.0, 20.0, "1", "many");

        // Act
        association.setFrom(newFrom);

        // Assert
        assertEquals(newFrom, association.getFrom());
    }

    @Test
    void testSetAndGetTo() {
        // Arrange
        Component from = new Class("Component1");
        Component to = new Class("Component2");
        Component newTo = new Class("Component3");
        Association association = new Association(from, to, "label", 10.0, 20.0, "1", "many");

        // Act
        association.setTo(newTo);

        // Assert
        assertEquals(newTo, association.getTo());
    }

    @Test
    void testSetAndGetType() {
        // Arrange
        Component from = new Class("Component1");
        Component to = new Class("Component2");
        Association association = new Association(from, to,  "label", 10.0, 20.0, "1", "many");

        // Act
        association.setType("Include");

        // Assert
        assertEquals("Include", association.getType());
    }

    @Test
    void testSetAndGetLabel() {
        // Arrange
        Component from = new Class("Component1");
        Component to = new Class("Component2");
        Association association = new Association(from, to, "has", 10.0, 20.0, "1", "many");

        // Act
        association.setLabel("Implements");

        // Assert
        assertEquals("Implements", association.getLabel());
    }

    @Test
    void testSetAndGetLabelX() {
        // Arrange
        Component from = new Class("Component1");
        Component to = new Class("Component2");
        Association association = new Association(from, to,  "has", 10.0, 20.0, "1", "many");

        // Act
        association.setLabelX(30.0);

        // Assert
        assertEquals(30.0, association.getLabelX());
    }

    @Test
    void testSetAndGetLabelY() {
        // Arrange
        Component from = new Class("Component1");
        Component to = new Class("Component2");
        Association association = new Association(from, to, "has", 10.0, 20.0, "1", "many");

        // Act
        association.setLabelY(40.0);

        // Assert
        assertEquals(40.0, association.getLabelY());
    }
}
