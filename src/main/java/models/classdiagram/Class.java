package models.classdiagram;

import models.Component;
import java.io.Serializable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Class implements Component, Serializable {
    private String name;
    private List<String> attributes;
    private List<String> methods;
    private int x;  // X-coordinate for positioning the component
    private int y;  // Y-coordinate for positioning the component

    // Constructor
    public Class(String name, int x, int y) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.x = x;  // Set the initial x-coordinate
        this.y = y;  // Set the initial y-coordinate
    }

    // Method to add an attribute
    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }

    // Method to add a method
    public void addMethod(String method) {
        methods.add(method);
    }

    // Getter and setter for coordinates
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

//    @Override
//    public void display() {
//        System.out.println("Class: " + name);
//        System.out.println("Attributes: " + attributes);
//        System.out.println("Methods: " + methods);
//        System.out.println("Position: (" + x + ", " + y + ")");
//    }

    @Override
    public String getDetails() {
        return "Class: " + name + " at (" + x + ", " + y + ")";
    }

    // Implementing setCoordinates from the Component interface
    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
