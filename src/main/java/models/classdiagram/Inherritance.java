package models.classdiagram;


import models.Component;
import models.Relationship;

public class Inherritance extends Relationship {

    public Inherritance(Component from, Component to, String label,int x,int y) {
        super(from, to, "Inherritance", label,x,y);
        System.out.println("Inherritance constructor ");

    }

//    @Override
//    public void display() {
//        System.out.println("Displaying Inherritance from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}