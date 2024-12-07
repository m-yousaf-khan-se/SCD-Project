package models.usecase;

import models.Component;
import java.io.Serializable;


public class Actor implements Component,Serializable {
    private String name;
    private int x; // X coordinate
    private int y; // Y coordinate

    public Actor(){}

    public Actor(String name) {
        this.name = name;
        this.x = 0;  // Set the initial x-coordinate
        this.y = 0;// Set the initial y-coordinate
        System.out.println("Inside model Actor constructor");
    }

    @Override
    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }


//    @Override
//    public void display() {
//        System.out.println("Actor: " + name + " at coordinates (" + x + ", " + y + ")");
//    }

    @Override
    public String getDetails() {
        return "Actor: " + name + " at coordinates (" + x + ", " + y + ")";
    }

    // Implement setCoordinates method from Component interface
    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Optionally, get the coordinates
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
