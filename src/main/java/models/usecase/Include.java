package models.usecase;

import models.Component;
import models.Relationship;

public class Include extends Relationship {

    public Include(Component from, Component to, String label) {
        super(from, to, "Include", label);
    }

//    @Override
//    public void display() {
//        System.out.println("Displaying Include from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}

