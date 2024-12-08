package models.usecase;

import models.Component;
import models.Relationship;

public class Association extends Relationship {


    // Constructor
    public Association(){}
    public Association(String type)
    {
        super(type);
    }
    public Association(Component from, Component to, String label, int x, int y) {
        super(from, to, "Association", label, x, y);
        System.out.println("Association constructor ");

    }
}