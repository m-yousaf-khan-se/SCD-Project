package models.usecase;

import models.Component;
import models.Relationship;

public class Extend extends Relationship {

    public Extend(Component from, Component to, String label) {
        super(from, to, "Extend", label);
    }

    @Override
    public void display() {
        System.out.println("Displaying Extend from " + getFrom().getDetails() + " to " + getTo().getDetails() +
                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
    }
}
