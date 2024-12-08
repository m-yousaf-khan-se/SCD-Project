package models;

import java.io.Serializable;

public abstract class Relationship implements Serializable {
    private Component from; // Starting component
    private Component to;   // Target component
    private String type;    // Relationship type (e.g., Association, Include)
    private String label;   // Label for the relationship (e.g., "extends", "includes")
    private double labelX;     // X-coordinate for the label
    private double labelY;     // Y-coordinate for the label

    // Constructor
    public Relationship(){}
    public Relationship(String type)
    {
        this.from=null;
        this.to=null;
        this.type=type;
        this.label=null;
        this.labelX=0;
        this.labelY=0;

    }
    public Relationship(Component from, Component to, String type, String label,double labelX,double labelY) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("From and To components cannot be null");
        }
        this.from = from;
        this.to = to;
        this.type = type;
        this.label = label;
        this.labelX = labelX; // Default coordinates
        this.labelY = labelY;
    }

    // Getters and Setters
    public Component getFrom() {
        return from;
    }

    public void setFrom(Component from) {
        this.from = from;
    }

    public Component getTo() {
        return to;
    }

    public void setTo(Component to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getLabelX() {
        return labelX;
    }

    public void setLabelX(double labelX) {
        this.labelX = labelX;
    }

    public double getLabelY() {
        return labelY;
    }

    public void setLabelY(double labelY) {
        this.labelY = labelY;
    }

    // Abstract method to render the relationship visually
    //public abstract void display();
}
