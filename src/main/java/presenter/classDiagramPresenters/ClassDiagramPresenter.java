package presenter.classDiagramPresenters;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import controllers.ViewIController;
import data.DiagramSerializer;
import models.*;
import models.classdiagram.Aggregation;
import models.classdiagram.Class;
import models.classdiagram.Association;
import models.classdiagram.generalization;
import models.classdiagram.Inherritance;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassDiagramPresenter {


    ViewIController view;
    IModel model1;
    models.classdiagram.Class clazz = new models.classdiagram.Class();
    models.classdiagram.Aggregation aggregation = new Aggregation();
    models.classdiagram.Association association = new Association();
    models.classdiagram.generalization generalizations = new generalization();
    models.classdiagram.Inherritance inherritance = new Inherritance();
    models.CodeGenerator code = new models.CodeGenerator();

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
//    public void addClassMethod(String className, String oldMethodDetails, String newMethodDetails) {
//        for (models.classdiagram.Class clazz : classes) {
//            if (clazz.getName().equals(className)) {
//                if(oldMethodDetails.isEmpty())
//                {
//                    clazz.addMethod(newMethodDetails);
//                }
//                else
//                {
//                    for(String method : clazz.getMethods())
//                    {
//                        if(method.equals(oldMethodDetails))
//                            method = newMethodDetails;
//                    }
//                }
//
//                // Update the corresponding component in DiagramModel
//                for (models.Component component : model.getComponents()) {
//                    if (component instanceof models.classdiagram.Class && ((models.classdiagram.Class) component).getName().equals(className)) {
//                        ((Class) component).addMethod(newMethodDetails);
//                        break;
//                    }
//                }
//                break;
//            }
//        }
//    }

    public void addClassMethod(String className, String oldMethodDetails, String newMethodDetails) {
        for (models.classdiagram.Class clazz : classes) {
            if (clazz.getName().equals(className)) {
                // If no old method is specified, check and add the new method if it doesn't already exist
                if (oldMethodDetails.isEmpty()) {
                    if (!clazz.getMethods().contains(newMethodDetails)) {
                        clazz.addMethod(newMethodDetails);
                    } else {
                        System.out.println("Method already exists: " + newMethodDetails);
                    }
                } else {
                    // Update the method details if oldMethodDetails is found
                    boolean methodUpdated = false;
                    for (int i = 0; i < clazz.getMethods().size(); i++) {
                        if (clazz.getMethods().get(i).equals(oldMethodDetails)) {
                            clazz.getMethods().set(i, newMethodDetails);
                            methodUpdated = true;
                            break;
                        }
                    }

                    // If the old method was not found, optionally add the new method
                    if (!methodUpdated) {
                        System.out.println("Old method not found: " + oldMethodDetails);
                    }
                }

                // Update the corresponding component in DiagramModel
                for (models.Component component : model.getComponents()) {
                    if (component instanceof models.classdiagram.Class &&
                            ((models.classdiagram.Class) component).getName().equals(className)) {
                        Class diagramClass = (Class) component;

                        // Ensure the new method is added only if it doesn't exist
                        if (!diagramClass.getMethods().contains(newMethodDetails)) {
                            diagramClass.addMethod(newMethodDetails);
                        } else {
                            System.out.println("Method already exists in DiagramModel: " + newMethodDetails);
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

//    public void addOrUpdateClassField(String className, String oldFieldName, String newFieldName) {
//        for (Class clazz : classes) {
//            if (clazz.getName().equals(className)) {
//                // If oldFieldName exists, update it
//                List<String> attributes = clazz.getAttributes();
//                if (attributes.contains(oldFieldName)) {
//                    int index = attributes.indexOf(oldFieldName);
//                    attributes.set(index, newFieldName); // Update the field
//                } else {
//                    // If oldFieldName does not exist, add newFieldName
//                    clazz.addAttribute(newFieldName);
//                }
//                break;
//            }
//        }
//    }

    public void addOrUpdateClassField(String className, String oldFieldName, String newFieldName) {
        for (Class clazz : classes) {
            if (clazz.getName().equals(className)) {
                // Get the list of attributes
                List<String> attributes = clazz.getAttributes();

                // If oldFieldName exists, update it
                if (attributes.contains(oldFieldName)) {
                    int index = attributes.indexOf(oldFieldName);
                    attributes.set(index, newFieldName); // Update the field
                } else {
                    // If oldFieldName does not exist, check if newFieldName already exists
                    if (!attributes.contains(newFieldName)) {
                        clazz.addAttribute(newFieldName); // Add the new field
                    } else {
                        System.out.println("Field already exists: " + newFieldName);
                    }
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
    public void setClassCoordinates(String className, Double x, Double y) {
        System.out.println("Co-ordinates: x="+x+" y="+y+" of the class: " + className + " passed to the class presenter.");
        for (Component component : model.getComponents()) {
            if (component instanceof Class && component.getName().equals(className)) {
                Class clazz = (Class) component;
                System.out.println("Setting up Co-ordinates of the class: " + clazz.getName());
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
    public boolean saveClassDiagramProject(File file)
    {
        System.out.println("Presenter is passing the follwing file:"+file.getName()+" with path to be saved: " + file.getPath());

        if (file.getName().endsWith(".json")) {
            System.out.println("Fetching file Path to save the project: " + file.getPath().toString());
            return DiagramSerializer.saveToFile(model , file);
        }
        else
        {
            System.err.println("File path doesn't ends with .json extension!");
        }

        return false;
    }

    public String generateJavaCodeFromClassDiagram() {
        String javaCode = CodeGenerator.generateCode(model);
        return javaCode;
    }

    public boolean loadClassDiagram(File file) throws IOException {
        System.out.println("Presenter received the follwing file:"+file.getName()+" with path to be loaded : " + file.getPath());
        List<models.classdiagram.Class> newClasses = new ArrayList<>();
        List<models.classdiagram.Aggregation> newAggreations = new ArrayList<>();
        List<models.classdiagram.Association> newAssociations = new ArrayList<>();
        List<models.classdiagram.generalization> newCopositions = new ArrayList<>();
        List<models.classdiagram.Inherritance> newInheritance = new ArrayList<>();


        if (file.getName().endsWith(".json")) {
            System.out.println("Fetching file Path to load the project: " + file.getPath().toString() + " from that file.");
            DiagramModel loadedModel = DiagramSerializer.loadFromFile(file);
            if(loadedModel == null)
            {
                return false;
            }

            for(models.Component components : loadedModel.getComponents())
            {
                if(components instanceof models.classdiagram.Class)
                {
                    newClasses.add((models.classdiagram.Class)components);
                }
            }

            for(models.Relationship relationships : loadedModel.getRelationships())
            {
                if(relationships instanceof models.classdiagram.Aggregation)
                {
                    newAggreations.add((models.classdiagram.Aggregation)relationships);
                }
                else if(relationships instanceof models.classdiagram.Association)
                {
                    newAssociations.add((models.classdiagram.Association)relationships);
                }
                else if(relationships instanceof models.classdiagram.generalization)
                {
                    newCopositions.add((models.classdiagram.generalization)relationships);
                }
                else if(relationships instanceof models.classdiagram.Inherritance)
                {
                    newInheritance.add((models.classdiagram.Inherritance)relationships);
                }
            }


            for(models.classdiagram.Class cls : newClasses)
            {
                view.loadClass(cls.getName(), (String[]) cls.getAttributes().toArray(), (String[]) cls.getMethods().toArray(), cls.getX(), cls.getY());
            }

            for(models.classdiagram.Aggregation agr : newAggreations)
            {
                view.loadAggregation(agr.getFrom().getName(), agr.getTo().getName());
            }

            for(models.classdiagram.Association agr : newAssociations)
            {
                view.loadAssociation(agr.getFrom().getName(), agr.getTo().getName(), agr.getMultiplicity1(), agr.getMultiplicity2());
            }

            for(models.classdiagram.generalization agr : newCopositions)
            {
                view.loadComposition(agr.getFrom().getName(), agr.getTo().getName());
            }

            for(models.classdiagram.Inherritance agr : newInheritance)
            {
                view.loadGeneralization(agr.getFrom().getName(), agr.getTo().getName());
            }

        }
        else
        {
            System.err.println("Unable to load Project: File path doesn't ends with .json extension!");
        }

        return false;
    }
}

