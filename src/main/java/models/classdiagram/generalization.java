package models.classdiagram;

import models.Component;
import models.Relationship;



public class generalization extends Relationship {

    public generalization(){}
    public generalization(String type)
    {
        super(type);
    }
    public generalization(Component from, Component to, String label,double x,double y) {
        super(from, to, "generalization", label,x,y);
        System.out.println("Generalization constructor ");

    }

//    @Override
//    public void display() {
//        System.out.println("Displaying generalization from " + getFrom().getDetails() + " to " + getTo().getDetails() +
//                " with label: " + getLabel() + " at (" + getLabelX() + ", " + getLabelY() + ")");
//    }
}