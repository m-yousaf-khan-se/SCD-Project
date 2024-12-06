package controllers;

public interface IController {

    //overriden by every component of class Diagram (every component of class Diagram has this function)
    public Double[] getCoordinates(); // return Double[] of size 2 (x, y respectively)
    public String[] getClassesName(); /* used by every component other than class itself
                                        returns String[0] as className1 and String[1] as className2 */
    public String getUMLClassName(); //unique to UMLClassIController

    //related to classes
    void addOrUpdateClassName(String oldName, String newName);
    void addOrUpdateMethodToClass(String className, String oldMethodDetails, String newMethodDetails);
    void addOrUpdateFieldToClass(String className, String oldFieldName, String newFieldName);
    void removeMethodFromClass(String className, String methodDetails);
    void removeFieldFromClass(String className, String fieldName);

    //related to Aggregation
    void addAggregation(String className1, String className2);
    void updateAggregation(String className1, String newClassName1, String className2, String newClassName2);

    //related to Association
    void addAssociation(String className1, String className2);
    void updateAssociation(String className1, String newClassName1, String className2, String newClassName2);
    Double[] updateMultiplicity(String className1, String className2);

    //related to Composition
    void addComposition(String className1, String className2);
    void updateComposition(String className1, String newClassName1, String className2, String newClassName2);

    //related to Generatlization
    void addGeneratlization(String className1, String className2);
    void updateGeneratlization(String className1, String newClassName1, String className2, String newClassName2);


}
