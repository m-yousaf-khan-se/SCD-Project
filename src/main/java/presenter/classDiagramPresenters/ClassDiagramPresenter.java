package presenter.classDiagramPresenters;

import controllers.IController;
import controllers.ViewIController;
import models.IModel;

import java.util.List;

public class ClassDiagramPresenter{


    ViewIController view;
    IModel model;
    ClassDiagramPresenter(IModel model, IController view)
    {
        //initialize models
        this.model = model;
        this.view = (ViewIController)view;
    }
    //----------------------related to Classes------------------------------
    public void addClass(String name) {
        // this funtion will be called in views
        //model function to add classes
        //model.addClass(name);
    }
    public void updateClassName(String name){
    }
    public void removeClass(String name) {
        //model funtion to remove classes
    }
    public void addMethod(String className, String methodDetail) {
        //model.addClassFuntion();
    }
    public void removeMethod(String className, String methodDetail) {
    }
    public void addField(String className, String fieldDetail) {
        //model.addClass(className, fieldDetail);
    }
    public void removeField(String className, String fieldDetail) {
    }
    public List<String> getClassNames() {
        //model funtion to get class name
        return null;
    }
    public List<String> getMethodDetail(String className) {
        return List.of();
    }
    public List<String> getFieldDetail(String className) {
        return List.of();
    }

    public void setClassCoordinates(String styleClass, String className1, String className2)
    {
        view.getComponentCoordinates(styleClass, className1); //returns Double[] of size 2 of class Coordinates
        view.getComponentCoordinates(styleClass, className1, className2); //returns Double[] of size 2 of other component Coordinates of class Diagram like association aggregation
    }

    //----------------------related to Aggregation------------------------------
    public void addAggregation(String className1, String className2) {
    }
    public void updateAggregation(String className1, String newClassName1, String className2, String newClassName2){
    }
    public void removeAggregation(String className1, String className2){
    }

    //----------------------related to Association--------------------------------
    public void addAssociation(String className1, String muliplicity1, String className2, String muliplicity2) {
    }
    public void updateAssociationMultiplicity(String multiplicity1, String newMultiplicity1, String multiplicity2, String newMultiplicity2){
    }
    public void updateAssociation(String className1, String newClassName1, String className2, String newClassName2){
    }
    public void removeAssociation(String className1, String className2){
    }

    //----------------------related to Composition--------------------------------
    public void addComposition(String className1, String className2) {
    }
    public void updateComposition(String className1, String newClassName1, String className2, String newClassName2){
    }
    public void removeComposition(String className1, String className2){
    }

    //----------------------related to Generalization--------------------------------
    public void addGeneralization(String className1, String className2) {
    }
    public void updateGeneralization(String className1, String newClassName1, String className2, String newClassName2){
    }
    public void removeGeneralization(String className1, String className2){
    }

}
