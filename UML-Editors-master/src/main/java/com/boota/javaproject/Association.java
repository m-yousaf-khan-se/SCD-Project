//package com.boota.javaproject;
//
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
//
//    public Association(Point start, Point end, Class initialClass, Class finalClass) {
//        this.start = start;
//        this.end = end;
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
    Multiplicity startMultiplicity;
    Multiplicity endMultiplicity;
    String text;
    Class initialClass;
    Class finalClass;

//    public Association(Point start, Point end, Class initialClass, Class finalClass) {
//        this.start = start;
//        this.end = end;
//        this.initialClass = initialClass;
//        this.finalClass = finalClass;
//    }

    public Association(Class initialClass, Class finalClass) {
        this.initialClass = initialClass;
        this.finalClass = finalClass;
    }

    public Association(Point start, Point end, Multiplicity startMultiplicity,
                       Multiplicity endMultiplicity, String text, Class initialClass,
                       Class finalClass) {
        this.start = start;
        this.end = end;
        this.startMultiplicity = startMultiplicity;
        this.endMultiplicity = endMultiplicity;
        this.text = text;
        this.initialClass = initialClass;
        this.finalClass = finalClass;
    }

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

    public Multiplicity getStartMultiplicity() {
        return startMultiplicity;
    }

    public void setStartMultiplicity(Multiplicity startMultiplicity) {
        this.startMultiplicity = startMultiplicity;
    }

    public Multiplicity getEndMultiplicity() {
        return endMultiplicity;
    }

    public void setEndMultiplicity(Multiplicity endMultiplicity) {
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

    /**
     * Renders the association on the canvas.
     *
     * @param gc GraphicsContext for drawing on the canvas.
     */
    public void render(GraphicsContext gc) {
        // Set the stroke color and line width
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        // Draw the line for the association
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());

        // Draw the start multiplicity (if present)
        if (startMultiplicity != null) {
            gc.fillText(startMultiplicity.toString(), start.getX() - 10, start.getY() - 10);
        }

        // Draw the end multiplicity (if present)
        if (endMultiplicity != null) {
            gc.fillText(endMultiplicity.toString(), end.getX() + 10, end.getY() + 10);
        }

        // Draw the label (if text is provided)
        if (text != null && !text.isEmpty()) {
            double midX = (start.getX() + end.getX()) / 2;
            double midY = (start.getY() + end.getY()) / 2;
            gc.fillText(text, midX, midY);
        }
    }
}
