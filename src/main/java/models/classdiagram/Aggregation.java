package models.classdiagram;

import models.Component;
import models.Relationship;

public class Aggregation extends Relationship {

    public Aggregation(Component from, Component to, String label,int x,int y) {
        super(from, to, "Aggregation", label,x,y);
    }

//    @Override
//    public void display() {
//        System.out.println("Displaying Aggregation from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}