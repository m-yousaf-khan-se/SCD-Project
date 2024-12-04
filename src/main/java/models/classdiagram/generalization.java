package models.classdiagram;

import models.Component;
import models.Relationship;



public class generalization extends Relationship {

    public generalization(Component from, Component to, String label,int x,int y) {
        super(from, to, "generalization", label,x,y);
    }

//    @Override
//    public void display() {
//        System.out.println("Displaying generalization from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}