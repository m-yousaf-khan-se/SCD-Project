package models.classdiagram;

import models.Component;
import models.Relationship;

public class Association extends Relationship {

    public Association(Component from, Component to, String label,int x, int y) {
        super(from, to, "Association", label,x,y);
    }

//    @Override
//    public void display() {
//        System.out.println("Displaying Association from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}

