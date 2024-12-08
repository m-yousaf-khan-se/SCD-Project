package models.classdiagram;

import models.Component;
import models.Relationship;

public class Aggregation extends Relationship {

    public Aggregation(){}
    public Aggregation(String type)
    {
        super(type);
    }
    public Aggregation(Component from, Component to, String label,double x,double y) {
        super(from, to, "Aggregation", label,x,y);
        System.out.println("Aggregation constructor ");

    }

//    @Override
//    public void display() {
//        System.out.println("Displaying Aggregation from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}