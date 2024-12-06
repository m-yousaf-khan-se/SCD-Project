package controllers;

public interface IController {

    //overriden by every component of class Diagram (every component of class Diagram has this function)
    public Double[] getCoordinates(); // return Double[] of size 2 (x, y respectively)
    public String[] getClassesName(); /* used by every component other than class itself
                                        returns String[0] as className1 and String[1] as className2 */



}
