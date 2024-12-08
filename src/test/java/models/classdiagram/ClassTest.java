package models.classdiagram;

import static org.junit.jupiter.api.Assertions.*;

import models.classdiagram.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassTest {

    private Class testClass;

    @BeforeEach
    void setUp() {
        testClass = new Class("TestClass");
    }

    @Test
    void testConstructor() {
        assertEquals("TestClass", testClass.getName());
        assertNotNull(testClass.getAttributes());
        assertNotNull(testClass.getMethods());
        assertEquals(0.0, testClass.getX());
        assertEquals(0.0, testClass.getY());
    }

    @Test
    void testSetName() {
        testClass.setName("NewClassName");
        assertEquals("NewClassName", testClass.getName());
    }

    @Test
    void testAddAttribute() {
        testClass.addAttribute("attribute1");
        testClass.addAttribute("attribute2");

        List<String> attributes = testClass.getAttributes();
        assertTrue(attributes.contains("attribute1"));
        assertTrue(attributes.contains("attribute2"));
    }

    @Test
    void testRemoveAttribute() {
        testClass.addAttribute("attribute1");
        testClass.addAttribute("attribute2");
        testClass.removeAttribute("attribute1");

        List<String> attributes = testClass.getAttributes();
        assertFalse(attributes.contains("attribute1"));
        assertTrue(attributes.contains("attribute2"));
    }

    @Test
    void testAddMethod() {
        testClass.addMethod("method1()");
        testClass.addMethod("method2()");

        List<String> methods = testClass.getMethods();
        assertTrue(methods.contains("method1()"));
        assertTrue(methods.contains("method2()"));
    }

    @Test
    void testRemoveMethod() {
        testClass.addMethod("method1()");
        testClass.addMethod("method2()");
        testClass.removeMethod("method1()");

        List<String> methods = testClass.getMethods();
        assertFalse(methods.contains("method1()"));
        assertTrue(methods.contains("method2()"));
    }

    @Test
    void testSetCoordinates() {
        testClass.setCoordinates(100.0, 200.0);
        assertEquals(100.0, testClass.getX());
        assertEquals(200.0, testClass.getY());
    }

    @Test
    void testGetDetails() {
        testClass.setCoordinates(100.0, 200.0);
        String expectedDetails = "Class: TestClass at (100.0, 200.0)";
        assertEquals(expectedDetails, testClass.getDetails());
    }

    @Test
    void testEqualsAndHashCode() {
        Class anotherClass = new Class("TestClass");
        assertEquals(testClass, anotherClass);
        assertEquals(testClass.hashCode(), anotherClass.hashCode());

        anotherClass.setName("DifferentClass");
        assertNotEquals(testClass, anotherClass);
    }
}
