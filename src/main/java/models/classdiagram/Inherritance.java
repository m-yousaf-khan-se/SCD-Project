package models.classdiagram;


import models.Component;
import models.Relationship;

public class Inherritance extends Relationship {

    public Inherritance(Component from, Component to, String label) {
        super(from, to, "Inherritance", label);
    }

    @Override
    public void display() {
        System.out.println("Displaying Inherritance from " + getFrom().getDetails() + " to " + getTo().getDetails() +
                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
    }
}