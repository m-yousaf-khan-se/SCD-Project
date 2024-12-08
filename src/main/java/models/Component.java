package models;

public interface Component {
    //void display(); // Display the component on the UI
    String getDetails(); // Return component details for relationships and display
    void setCoordinates(double x, double y); // Set the position of the component
    double getX(); // Get the X coordinate
    double getY();
    public String getName();

}
