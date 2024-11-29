package models;

public interface Component {
    void display(); // Display the component on the UI
    String getDetails(); // Return component details for relationships and display


    void setCoordinates(int x, int y); // Set the position of the component
    int getX(); // Get the X coordinate
    int getY();
}
