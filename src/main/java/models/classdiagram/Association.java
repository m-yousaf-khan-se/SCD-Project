package models.classdiagram;

import models.Component;
import models.Relationship;

public class Association extends Relationship {

    private String multiplicity1;
    private String multiplicity2;

    // Constructor
    public Association(Component from, Component to, String label, int x, int y, String multiplicity1, String multiplicity2) {
        super(from, to, "Association", label, x, y);
        this.multiplicity1 = multiplicity1;
        this.multiplicity2 = multiplicity2;
    }

    // Getters and Setters
    public String getMultiplicity1() {
        return multiplicity1;
    }

    public void setMultiplicity1(String multiplicity1) {
        this.multiplicity1 = multiplicity1;
    }

    public String getMultiplicity2() {
        return multiplicity2;
    }

    public void setMultiplicity2(String multiplicity2) {
        this.multiplicity2 = multiplicity2;
    }


}
