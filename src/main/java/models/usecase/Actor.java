package models.usecase;

import models.Component;

public class Actor implements Component {
    private String name;
    private int x; // X coordinate
    private int y; // Y coordinate

    public Actor(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public void display() {
        System.out.println("Actor: " + name + " at coordinates (" + x + ", " + y + ")");
    }

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
