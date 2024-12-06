package models;

import java.io.Serializable;

public abstract class Relationship implements Serializable {
    private Component from; // Starting component
    private Component to;   // Target component
    private String type;    // Relationship type (e.g., Association, Include)
    private String label;   // Label for the relationship (e.g., "extends", "includes")
    private int labelX;     // X-coordinate for the label
    private int labelY;     // Y-coordinate for the label

    // Constructor
    public Relationship(){}
    public Relationship(Component from, Component to, String type, String label,int labelX,int labelY) {
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

    public int getLabelX() {
        return labelX;
    }

    public void setLabelX(int labelX) {
        this.labelX = labelX;
    }

    public int getLabelY() {
        return labelY;
    }

    public void setLabelY(int labelY) {
        this.labelY = labelY;
    }

    // Abstract method to render the relationship visually
    //public abstract void display();
}
