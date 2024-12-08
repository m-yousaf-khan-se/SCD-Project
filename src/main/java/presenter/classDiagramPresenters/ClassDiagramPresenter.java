package presenter.classDiagramPresenters;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import controllers.ViewIController;
import data.DiagramSerializer;
import models.DiagramModel;
import models.IModel;
import models.Component;
import models.Relationship;
import models.classdiagram.Aggregation;
import models.classdiagram.Class;
import models.classdiagram.Association;
import models.classdiagram.generalization;
import models.classdiagram.Inherritance;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassDiagramPresenter {


    ViewIController view;
    IModel model1;
    models.classdiagram.Class clazz=new models.classdiagram.Class();
    models.classdiagram.Aggregation aggregation=new Aggregation();
    models.classdiagram.Association association=new Association();
    models.classdiagram.generalization generalizations=new generalization();
    models.classdiagram.Inherritance inherritance=new Inherritance();
    models.CodeGenerator code=new models.CodeGenerator();

    List<models.classdiagram.Class> classes = new ArrayList<>();
    private models.DiagramModel model; // Reference to the DiagramModel
//clazz,aggregation,association,generalizations,inherritance,classes


    int count=0;
    public ClassDiagramPresenter(models.DiagramModel model,models.classdiagram.Class clazz,models.classdiagram.Aggregation aggregation,models.classdiagram.Association association,models.classdiagram.generalization generalizations,models.classdiagram.Inherritance inherritance,List<models.classdiagram.Class> classes , ViewIController view) {


        //initialize models
//        this.model = (DiagramModel)model1;
        this.model=model;
        this.clazz=clazz;
        this.aggregation=aggregation;
        this.association=association;
        this.generalizations=generalizations;
        this.inherritance=inherritance;
        this.classes=classes;
        this.view = view;
    }

    //----------------------related to Classes------------------------------
    public void addClass(String name) {

        count++;
        models.classdiagram.Class clazz = new models.classdiagram.Class(name);

        // Add the class to the local list
        classes.add(clazz);

        // Add the class as a component to the DiagramModel
        model.addComponent(clazz);


    }

    // Update the class name in the DiagramModel
    public void updateClassName(String oldName, String newName) {
        System.out.println("Inside UpdateClassName");
        for (models.classdiagram.Class clazz : classes) {
            if (clazz.getName().equals(oldName)) {
                clazz.setName(newName);

                // Ensure the DiagramModel is updated
                List<models.Component> components = model.getComponents();
                for (models.Component component : components) {
                    if (component instanceof models.classdiagram.Class && ((models.classdiagram.Class) component).getName().equals(oldName)) {
                        ((models.classdiagram.Class) component).setName(newName);
                        break;
                    }
                }
                break;
            }
        }
    }

    // Remove a class from the DiagramModel
    public void removeClass(String name) {
        classes.removeIf(clazz -> clazz.getName().equals(name));

        // Remove the class from the DiagramModel's components
        model.getComponents().removeIf(component -> component instanceof models.classdiagram.Class && ((models.classdiagram.Class) component).getName().equals(name));
    }

    // Add a method to a class in the DiagramModel
    public void addClassMethod(String className, String oldMethodDetails, String newMethodDetails) {
        for (models.classdiagram.Class clazz : classes) {
            if (clazz.getName().equals(className)) {
                if(oldMethodDetails.isEmpty())
                {
                    clazz.addMethod(newMethodDetails);
                }
                else
                {
                    for(String method : clazz.getMethods())
                    {
                        if(method.equals(oldMethodDetails))
                            method = newMethodDetails;
                    }
                }

                // Update the corresponding component in DiagramModel
                for (models.Component component : model.getComponents()) {
                    if (component instanceof models.classdiagram.Class && ((models.classdiagram.Class) component).getName().equals(className)) {
                        ((Class) component).addMethod(newMethodDetails);
                        break;
                    }
                }
                break;
            }
        }
    }

    public void addOrUpdateClassField(String className, String oldFieldName, String newFieldName) {
        for (Class clazz : classes) {
            if (clazz.getName().equals(className)) {
                // If oldFieldName exists, update it
                List<String> attributes = clazz.getAttributes();
                if (attributes.contains(oldFieldName)) {
                    int index = attributes.indexOf(oldFieldName);
                    attributes.set(index, newFieldName); // Update the field
                } else {
                    // If oldFieldName does not exist, add newFieldName
                    clazz.addAttribute(newFieldName);
                }
                break;
            }
        }
    }


    public void removeClassMethod(String className, String methodDetails) {
        for (Class clazz : classes) {
            if (clazz.getName().equals(className)) {
                // Assuming the Class model has a removeMethod method
                clazz.removeMethod(methodDetails);
                break;
            }
        }
    }


    public void removeClassField(String className, String fieldName) {
        for (Class clazz : classes) {
            if (clazz.getName().equals(className)) {
                // Assuming the Class model has a method to remove attributes
                List<String> attributes = clazz.getAttributes();
                attributes.remove(fieldName);
                break;
            }
        }
    }
    //function for setting the cordinates of the class ... call it when we are saving the diagram
    public void setClassCoordinates(String className, int x, int y) {
        for (Component component : model.getComponents()) {
            if (component instanceof Class && component.getDetails().equals(className)) {
                Class clazz = (Class) component;
                clazz.setX(x);
                clazz.setY(y);
                break;
            }
        }
    }


    //----------------------related to Aggregation------------------------------
    private Class getClassByName(String className) {
        for (Component component : model.getComponents()) {
            if (component instanceof Class && ((Class) component).getName().equals(className)) {
                return (Class) component;
            }
        }
        return null;
    }
    public void addAggregation(String className1, String className2) {
        // Get the Class components using the class names provided
        Class class1 = getClassByName(className1);
        Class class2 = getClassByName(className2);

        System.out.println("Inside Presnter add Aggregation");
        if (class1 != null && class2 != null) {
            // Create the Aggregation relationship between class1 and class2
            Aggregation aggregation = new Aggregation(class1, class2, "Aggregation", 0, 0);  // Position can be adjusted
            model.addRelationship(aggregation);  // Add to diagram model
        }
        else {
            Aggregation aggregation=new Aggregation("Aggregation");
            model.addRelationship(aggregation);
        }
    }
//    public void updateAggregation(String className1, String newClassName1, String className2, String newClassName2) {
//        // Find and update the relationship if exists
//        for (Relationship relationship : model.getRelationships()) {
//            if (relationship instanceof Aggregation) {
//                Aggregation aggregation = (Aggregation) relationship;
//                if (aggregation.getFrom().getDetails().equals(className1) && aggregation.getTo().getDetails().equals(className1)) {
//                    // Update the components if the relationship is found
//                    Class newClass1 = getClassByName(newClassName1);
//                    Class newClass2 = getClassByName(newClassName2);
//                    if (newClass1 != null && newClass2 != null) {
//                        aggregation.setFrom(newClass1);
//                        aggregation.setTo(newClass2);
//                        break;
//                    }
//                }
//            }
//        }
//    }
public void updateAggregation(String oldClassName1, String newClassName1, String oldClassName2, String newClassName2) {
    System.out.println("Presenter Update Inherritance");
    System.out.println("Old class names: " + oldClassName1 + ", " + oldClassName2);
    System.out.println("New class names: " + newClassName1 + ", " + newClassName2);

    // Iterate through all relationships in the model
    for (Relationship relationship : model.getRelationships()) {
        System.out.println("Inside for loop");

        // Check if the relationship is an instance of Inherritance
        if (relationship instanceof Aggregation) {
            System.out.println("Inside if condition");
            Aggregation association = (Aggregation) relationship;

            // Check if the relationship involves the old class names
            boolean isMatchingFrom = (oldClassName1 == null || association.getFrom().getDetails().equals(oldClassName1));
            boolean isMatchingTo = (oldClassName2 == null || association.getTo().getDetails().equals(oldClassName2));

            System.out.println("Checking relationship: " + association);
            System.out.println("Matching from: " + isMatchingFrom + ", Matching to: " + isMatchingTo);

            // If both the from and to classes match, update them
            if (isMatchingFrom || isMatchingTo) {
                // Find the new components based on the new class names
                Component newClass1 = findComponentByName(newClassName1);
                Component newClass2 = findComponentByName(newClassName2);

                // Update the "from" component if the new class is found
                if (newClass1 != null) {
                    System.out.println("Setting 'from' to: " + newClassName1);
                    association.setFrom(newClass1); // Update "from" with the new class
                } else {
                    System.err.println("Warning: Could not find class: " + newClassName1);
                }

                // Update the "to" component if the new class is found
                if (newClass2 != null) {
                    System.out.println("Setting 'to' to: " + newClassName2);
                    association.setTo(newClass2); // Update "to" with the new class
                } else {
                    System.err.println("Warning: Could not find class: " + newClassName2);
                }

                // Exit after updating the relationship
                break;
            }
        }
    }
}

    public void removeAggregation(String className1, String className2) {
        // Remove the aggregation relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof Aggregation &&
                        ((Aggregation) relationship).getFrom().getDetails().equals(className1) &&
                        ((Aggregation) relationship).getTo().getDetails().equals(className1));
    }

    //----------------------related to Association--------------------------------
    // Function to find a class component by its name
    private Component findComponentByName(String name) {
        // Loop through all components in the diagram model to find the class by name
        for (Component component : model.getComponents()) {
            if (component instanceof Class && ((Class) component).getName().equals(name)) {
                return component;  // Return the component (class) if it matches the name
            }
        }
        return null;  // Return null if no matching component is found
    }

    public void addAssociation(String className1, String multiplicity1, String className2, String multiplicity2) {
        // Find components representing the classes
        Component class1 = findComponentByName(className1);
        Component class2 = findComponentByName(className2);

//        Association association = new Association(class1, class2, "Association", 0, 0, multiplicity1, multiplicity2);
//        model.addRelationship(association);

        System.out.println("Inside Association PResenter");
        if (class1 != null && class2 != null) {
            Association association = new Association(class1, class2, "Association", 0, 0, multiplicity1, multiplicity2);
            model.addRelationship(association);
        }
        else{
            Association association=new Association("Association");
            model.addRelationship(association);
        }
        // Create the association and add it to the diagram model

    }

    public void updateAssociationMultiplicity(String className1, String newMultiplicity1, String className2, String newMultiplicity2) {
        for (Relationship relationship : model.getRelationships()) {
            if (relationship instanceof Association) {
                Association association = (Association) relationship;

                // Check if the association is between the specified classes
                if (association.getFrom().getDetails().equals(className1) &&
                        association.getTo().getDetails().equals(className2)) {

                    // Update multiplicities
                    association.setMultiplicity1(newMultiplicity1);
                    association.setMultiplicity2(newMultiplicity2);
                    break;
                }
            }
        }
    }

//    public void updateAssociation(String oldClassName1, String newClassName1, String oldClassName2, String newClassName2) {
//        System.out.println("Presenter Update Association");
//        for (Relationship relationship : model.getRelationships()) {
//            if (relationship instanceof Association) {
//                Association association = (Association) relationship;
//
//                // Check if the association is between the old class names
//                if (association.getFrom().getDetails().equals(oldClassName1) &&
//                        association.getTo().getDetails().equals(oldClassName2)) {
//
//                    // Update the components (assuming findClassByName works for the new names)
//                    Component newClass1 = findComponentByName(newClassName1);
//                    Component newClass2 = findComponentByName(newClassName2);
//
//                    if (newClass1 != null && newClass2 != null) {
//                        association.setFrom(newClass1);
//                        association.setTo(newClass2);
//                    }
//                    break;
//                }
//            }
//        }
//    }

    public void updateAssociation(String oldClassName1, String newClassName1, String oldClassName2, String newClassName2) {
        System.out.println("Presenter Update Inherritance");
        System.out.println("Old class names: " + oldClassName1 + ", " + oldClassName2);
        System.out.println("New class names: " + newClassName1 + ", " + newClassName2);

        // Iterate through all relationships in the model
        for (Relationship relationship : model.getRelationships()) {
            System.out.println("Inside for loop");

            // Check if the relationship is an instance of Inherritance
            if (relationship instanceof Association) {
                System.out.println("Inside if condition");
                Association association = (Association) relationship;

                // Check if the relationship involves the old class names
                boolean isMatchingFrom = (oldClassName1 == null || association.getFrom().getDetails().equals(oldClassName1));
                boolean isMatchingTo = (oldClassName2 == null || association.getTo().getDetails().equals(oldClassName2));

                System.out.println("Checking relationship: " + association);
                System.out.println("Matching from: " + isMatchingFrom + ", Matching to: " + isMatchingTo);

                // If both the from and to classes match, update them
                if (isMatchingFrom || isMatchingTo) {
                    // Find the new components based on the new class names
                    Component newClass1 = findComponentByName(newClassName1);
                    Component newClass2 = findComponentByName(newClassName2);

                    // Update the "from" component if the new class is found
                    if (newClass1 != null) {
                        System.out.println("Setting 'from' to: " + newClassName1);
                        association.setFrom(newClass1); // Update "from" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newClassName1);
                    }

                    // Update the "to" component if the new class is found
                    if (newClass2 != null) {
                        System.out.println("Setting 'to' to: " + newClassName2);
                        association.setTo(newClass2); // Update "to" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newClassName2);
                    }

                    // Exit after updating the relationship
                    break;
                }
            }
        }
    }
    public void removeAssociation(String className1, String className2) {
        model.getRelationships().removeIf(relationship -> {
            if (relationship instanceof Association) {
                Association association = (Association) relationship;
                return association.getFrom().getDetails().equals(className1) &&
                        association.getTo().getDetails().equals(className2);
            }
            return false;
        });
    }


    //----------------------related to Composition--------------------------------
    public void addComposition(String className1, String className2) {
        // Get the Class components using the class names provided
        Class class1 = getClassByName(className1);
        Class class2 = getClassByName(className2);

        if (class1 != null && class2 != null) {
            // Create the Composition relationship between class1 and class2
            generalization composition = new generalization(class1, class2, "generalization", 0, 0);  // Position can be adjusted
            model.addRelationship(composition);  // Add to diagram model
        }
        else {
            generalization composition=new generalization("generalization");
            model.addRelationship(composition);
        }
    }

//    public void updateComposition(String className1, String newClassName1, String className2, String newClassName2) {
//        // Find and update the relationship if exists
//        for (Relationship relationship : model.getRelationships()) {
//            if (relationship instanceof generalization) {
//                generalization composition = (generalization) relationship;
//                if (composition.getFrom().getDetails().equals(className1) && composition.getTo().getDetails().equals(className2)) {
//                    // Update the components if the relationship is found
//                    Class newClass1 = getClassByName(newClassName1);
//                    Class newClass2 = getClassByName(newClassName2);
//                    if (newClass1 != null && newClass2 != null) {
//                        composition.setFrom(newClass1);
//                        composition.setTo(newClass2);
//                        break;
//                    }
//                }
//            }
//        }
//    }
public void updateComposition(String oldClassName1, String newClassName1, String oldClassName2, String newClassName2) {
    System.out.println("Presenter Update Inherritance");
    System.out.println("Old class names: " + oldClassName1 + ", " + oldClassName2);
    System.out.println("New class names: " + newClassName1 + ", " + newClassName2);

    // Iterate through all relationships in the model
    for (Relationship relationship : model.getRelationships()) {
        System.out.println("Inside for loop");

        // Check if the relationship is an instance of Inherritance
        if (relationship instanceof generalization) {
            System.out.println("Inside if condition");
            generalization association = (generalization) relationship;

            // Check if the relationship involves the old class names
            boolean isMatchingFrom = (oldClassName1 == null || association.getFrom().getDetails().equals(oldClassName1));
            boolean isMatchingTo = (oldClassName2 == null || association.getTo().getDetails().equals(oldClassName2));

            System.out.println("Checking relationship: " + association);
            System.out.println("Matching from: " + isMatchingFrom + ", Matching to: " + isMatchingTo);

            // If both the from and to classes match, update them
            if (isMatchingFrom || isMatchingTo) {
                // Find the new components based on the new class names
                Component newClass1 = findComponentByName(newClassName1);
                Component newClass2 = findComponentByName(newClassName2);

                // Update the "from" component if the new class is found
                if (newClass1 != null) {
                    System.out.println("Setting 'from' to: " + newClassName1);
                    association.setFrom(newClass1); // Update "from" with the new class
                } else {
                    System.err.println("Warning: Could not find class: " + newClassName1);
                }

                // Update the "to" component if the new class is found
                if (newClass2 != null) {
                    System.out.println("Setting 'to' to: " + newClassName2);
                    association.setTo(newClass2); // Update "to" with the new class
                } else {
                    System.err.println("Warning: Could not find class: " + newClassName2);
                }

                // Exit after updating the relationship
                break;
            }
        }
    }
}


    public void removeComposition(String className1, String className2) {
        // Remove the composition relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof generalization &&
                        ((generalization) relationship).getFrom().getDetails().equals(className1) &&
                        ((generalization) relationship).getTo().getDetails().equals(className2));
    }

    //----------------------related to Inherritance--------------------------------
    public void addInherritance(String className1, String className2) {
        // Get the Class components using the class names provided
        Class class1 = getClassByName(className1);
        Class class2 = getClassByName(className2);

        if (class1 != null && class2 != null) {
            // Create the Composition relationship between class1 and class2
            Inherritance inherritance = new Inherritance(class1, class2, "Inherritance", 0, 0);  // Position can be adjusted
            model.addRelationship(inherritance);  // Add to diagram model
        }
        else {
            Inherritance inherritance1=new Inherritance("Inherritance");
            model.addRelationship(inherritance1);
        }
    }

//    public void updateinherritance(String className1, String newClassName1, String className2, String newClassName2) {
//        // Find and update the relationship if exists
//        for (Relationship relationship : model.getRelationships()) {
//            if (relationship instanceof Inherritance) {
//                Inherritance inherritance = (Inherritance) relationship;
//                if (inherritance.getFrom().getDetails().equals(className1) && inherritance.getTo().getDetails().equals(className2)) {
//                    // Update the components if the relationship is found
//                    Class newClass1 = getClassByName(newClassName1);
//                    Class newClass2 = getClassByName(newClassName2);
//                    if (newClass1 != null && newClass2 != null) {
//                        inherritance.setFrom(newClass1);
//                        inherritance.setTo(newClass2);
//                        break;
//                    }
//                }
//            }
//        }
//    }

    public void updateinherritance(String oldClassName1, String newClassName1, String oldClassName2, String newClassName2) {
        System.out.println("Presenter Update Inherritance");
        System.out.println("Old class names: " + oldClassName1 + ", " + oldClassName2);
        System.out.println("New class names: " + newClassName1 + ", " + newClassName2);

        // Iterate through all relationships in the model
        for (Relationship relationship : model.getRelationships()) {
            System.out.println("Inside for loop");

            // Check if the relationship is an instance of Inherritance
            if (relationship instanceof Inherritance) {
                System.out.println("Inside if condition");
                Inherritance association = (Inherritance) relationship;

                // Check if the relationship involves the old class names
                boolean isMatchingFrom = (oldClassName1 == null || association.getFrom().getDetails().equals(oldClassName1));
                boolean isMatchingTo = (oldClassName2 == null || association.getTo().getDetails().equals(oldClassName2));

                System.out.println("Checking relationship: " + association);
                System.out.println("Matching from: " + isMatchingFrom + ", Matching to: " + isMatchingTo);

                // If both the from and to classes match, update them
                if (isMatchingFrom || isMatchingTo) {
                    // Find the new components based on the new class names
                    Component newClass1 = findComponentByName(newClassName1);
                    Component newClass2 = findComponentByName(newClassName2);

                    // Update the "from" component if the new class is found
                    if (newClass1 != null) {
                        System.out.println("Setting 'from' to: " + newClassName1);
                        association.setFrom(newClass1); // Update "from" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newClassName1);
                    }

                    // Update the "to" component if the new class is found
                    if (newClass2 != null) {
                        System.out.println("Setting 'to' to: " + newClassName2);
                        association.setTo(newClass2); // Update "to" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newClassName2);
                    }

                    // Exit after updating the relationship
                    break;
                }
            }
        }
    }


    public void removeInherritance(String className1, String className2) {
        // Remove the composition relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof Inherritance &&
                        ((Inherritance) relationship).getFrom().getDetails().equals(className1) &&
                        ((Inherritance) relationship).getTo().getDetails().equals(className2));
    }

    //Setting the cordinates of all the relationships such as agregation , association, inherritance and generalization
    public void setRelationshipCoordinates(String fromClassName, String toClassName, int labelX, int labelY) {
        for (Relationship relationship : model.getRelationships()) {
            if (relationship.getFrom().getDetails().equals(fromClassName) &&
                    relationship.getTo().getDetails().equals(toClassName)) {

                relationship.setLabelX(labelX);
                relationship.setLabelY(labelY);
                break;
            }
        }
    }

    //------------------------Save Projects to JSON ------------------------
    public void saveClassDiagramProject(File file)
    {
        System.out.println("Presenter is passing the follwing file:"+file.getName()+" with path to be saved: " + file.getPath());

        if (file.getName().endsWith(".json")) {
            System.out.println("Fetching file Path to save the project: " + file.getPath().toString());
            DiagramSerializer.saveToFile(model , file);
        }
        else
        {
            System.err.println("File path doesn't ends with .json extension!");
        }
    }
}

