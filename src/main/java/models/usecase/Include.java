package models.usecase;

import models.Component;
import models.Relationship;

public class Include extends Relationship {

    public Include(){};
    public Include(Component from, Component to, String label,int x,int y) {
        super(from, to, "Include", label,x,y);
    }

//    @Override
//    public void display() {
//        System.out.println("Displaying Include from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}

