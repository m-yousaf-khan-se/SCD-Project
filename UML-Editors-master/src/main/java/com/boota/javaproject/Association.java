//package com.boota.javaproject;
//
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//public class Association {
//    Point start;
//    Point end;
//    Multiplicity startMultiplicity;
//    Multiplicity endMultiplicity;
//    String text;
//    Class initialClass;
//    Class finalClass;
//
////    public Association(Point start, Point end, Class initialClass, Class finalClass) {
////        this.start = start;
////        this.end = end;
////        this.initialClass = initialClass;
////        this.finalClass = finalClass;
////    }
//
//    public Association(Class initialClass, Class finalClass) {
//        this.initialClass = initialClass;
//        this.finalClass = finalClass;
//    }
//
//    public Association(Point start, Point end, Multiplicity startMultiplicity,
//                       Multiplicity endMultiplicity, String text, Class initialClass,
//                       Class finalClass) {
//        this.start = start;
//        this.end = end;
//        this.startMultiplicity = startMultiplicity;
//        this.endMultiplicity = endMultiplicity;
//        this.text = text;
//        this.initialClass = initialClass;
//        this.finalClass = finalClass;
//    }
//
//    public Point getStart() {
//        return start;
//    }
//
//    public void setStart(Point start) {
//        this.start = start;
//    }
//
//    public Point getEnd() {
//        return end;
//    }
//
//    public void setEnd(Point end) {
//        this.end = end;
//    }
//
//    public Multiplicity getStartMultiplicity() {
//        return startMultiplicity;
//    }
//
//    public void setStartMultiplicity(Multiplicity startMultiplicity) {
//        this.startMultiplicity = startMultiplicity;
//    }
//
//    public Multiplicity getEndMultiplicity() {
//        return endMultiplicity;
//    }
//
//    public void setEndMultiplicity(Multiplicity endMultiplicity) {
//        this.endMultiplicity = endMultiplicity;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public Class getInitialClass() {
//        return initialClass;
//    }
//
//    public void setInitialClass(Class initialClass) {
//        this.initialClass = initialClass;
//    }
//
//    public Class getFinalClass() {
//        return finalClass;
//    }
//
//    public void setFinalClass(Class finalClass) {
//        this.finalClass = finalClass;
//    }
//
//}




package com.boota.javaproject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Association {
    Point start;
    Point end;
    String startMultiplicity;  // Using String for multiplicity (e.g., "1", "0..*", etc.)
    String endMultiplicity;    // Using String for multiplicity
    String text;               // Association name (e.g., "has", "belongs to")
    Class initialClass;        // Source class
    Class finalClass;          // Target class

    // Constructor to handle just the source and target classes without multiplicity
    public Association(Class initialClass, Class finalClass) {
        this.initialClass = initialClass;
        this.finalClass = finalClass;
    }

    // Constructor to handle full association details with multiplicity and text
    public Association(Point start, Point end, String startMultiplicity, String endMultiplicity,
                       String text, Class initialClass, Class finalClass) {
        this.start = start;
        this.end = end;
        this.startMultiplicity = startMultiplicity;
        this.endMultiplicity = endMultiplicity;
        this.text = text;
        this.initialClass = initialClass;
        this.finalClass = finalClass;
    }

    // Getters and Setters
    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public String getStartMultiplicity() {
        return startMultiplicity;
    }

    public void setStartMultiplicity(String startMultiplicity) {
        this.startMultiplicity = startMultiplicity;
    }

    public String getEndMultiplicity() {
        return endMultiplicity;
    }

    public void setEndMultiplicity(String endMultiplicity) {
        this.endMultiplicity = endMultiplicity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Class getInitialClass() {
        return initialClass;
    }

    public void setInitialClass(Class initialClass) {
        this.initialClass = initialClass;
    }

    public Class getFinalClass() {
        return finalClass;
    }

    public void setFinalClass(Class finalClass) {
        this.finalClass = finalClass;
    }
}

