package models.usecase;

import models.Component;
import models.Relationship;

public class Extend extends Relationship {

    public Extend(){}
    public Extend(Component from, Component to, String label,int x,int y) {
        super(from, to, "Extend", label,x,y);
    }

//    @Override
//    public void display() {
//        System.out.println("Displaying Extend from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}
