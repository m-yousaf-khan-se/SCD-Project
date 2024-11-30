package models.usecase;

import models.Component;
import java.io.Serializable;


import models.Component;

public class UseCase implements Component,Serializable {
    private String name;
    private String description;
    private int x; // X coordinate
    private int y; // Y coordinate

    public UseCase(String name, String description, int x, int y) {
        this.name = name;
        this.description = description;
        this.x = x;
        this.y = y;
    }

    @Override
    public void display() {
        System.out.println("UseCase: " + name + " - " + description + " at coordinates (" + x + ", " + y + ")");
    }

    @Override
    public String getDetails() {
        return "UseCase: " + name + " at coordinates (" + x + ", " + y + ")";
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

