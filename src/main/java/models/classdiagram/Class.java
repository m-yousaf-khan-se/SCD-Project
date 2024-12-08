package models.classdiagram;

import models.Component;
import java.io.Serializable;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Class implements Component, Serializable {
    private String name;
    private List<String> attributes;
    private List<String> methods;
    private double x;  // X-coordinate for positioning the component
    private double y;  // Y-coordinate for positioning the component

    // Constructor
    public Class()
    {}
    public Class(String name) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.x = 0.0;  // Set the initial x-coordinate
        this.y = 0.0;// Set the initial y-coordinate
        System.out.println("Inside model class constructor");
    }


    public void setName(String name)
    {
        this.name=name;
        System.out.println("Setting the name of class ");
    }
    @Override
    public String getName()
    {
        System.out.println("Getting the name of class ");
        return name;

    }
    // Method to add an attribute
    public void addAttribute(String attribute) {
        attributes.add(attribute);
        System.out.println("Attribute added in the class ");

    }
    public void removeAttribute(String attribute){
        attributes.remove(attribute);
        System.out.println("Attribute removed from the class ");

    }

    // Method to add a method
    public void addMethod(String method) {
        methods.add(method);
        System.out.println("Method added in the class ");

    }

    public void removeMethod(String method){
        methods.remove(method);
        System.out.println("Method Removed from the class ");

    }
    // Getter and setter for coordinates
    @Override
    public double getX() {
        System.out.println("Getting x cordinate ");

        return x;
    }

    public void setX(double x) {
        System.out.println("Setting x cordinate ");

        this.x = x;
    }

    @Override
    public double getY()
    {
        System.out.println("Getting y cordinate ");

        return y;
    }

    public void setY(double y) {
        System.out.println("Setting x cordinate ");

        this.y = y;
    }

    public List<String> getAttributes()
    {
        return attributes;
    }
    public List<String> getMethods()
    {
        return methods;
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
    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same reference
        if (obj == null || getClass() != obj.getClass()) return false; // Null or different class
        models.classdiagram.Class other = (models.classdiagram.Class) obj;
        return this.name.equals(other.name); // Compare based on class name
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); // Hash based on class name
    }

}
