package presenter.classDiagramPresenters;

import controllers.IController;
import controllers.ViewIController;
import models.DiagramModel;
import models.IModel;
import models.Component;
import models.Relationship;
import models.classdiagram.Aggregation;
import models.classdiagram.Class;
import models.classdiagram.Association;
import models.classdiagram.generalization;
import models.classdiagram.Inherritance;


import java.util.ArrayList;
import java.util.List;

public class ClassDiagramPresenter {


    ViewIController view;
    IModel model1;
    Class clazz;
    Aggregation aggregation;
    Association association;
    generalization generalizations;
    Inherritance inherritance;

    List<Class> classes = new ArrayList<>();
    private DiagramModel model; // Reference to the DiagramModel


    public ClassDiagramPresenter(IModel model1, ViewIController view) {


        //initialize models
        this.model = (DiagramModel)model1;
        this.view = view;
    }

    //----------------------related to Classes------------------------------
    public void addClass(String name) {
        Class clazz = new Class(name);

        // Add the class to the local list
        classes.add(clazz);

        // Add the class as a component to the DiagramModel
        model.addComponent(clazz);
    }

    // Update the class name in the DiagramModel
    public void updateClassName(String oldName, String newName) {
        for (Class clazz : classes) {
            if (clazz.getName().equals(oldName)) {
                clazz.setName(newName);

                // Ensure the DiagramModel is updated
                List<Component> components = model.getComponents();
                for (Component component : components) {
                    if (component instanceof Class && ((Class) component).getName().equals(oldName)) {
                        ((Class) component).setName(newName);
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
        model.getComponents().removeIf(component -> component instanceof Class && ((Class) component).getName().equals(name));
    }

    // Add a method to a class in the DiagramModel
    public void addClassMethod(String className, String oldMethodDetails, String newMethodDetails) {
        for (Class clazz : classes) {
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
                for (Component component : model.getComponents()) {
                    if (component instanceof Class && ((Class) component).getName().equals(className)) {
                        ((Class) component).addMethod(newMethodDetails);
                        break;
                    }
                }
                break;
            }
        }
    }

    public void addOrUpdateClassField(String className, String oldFieldName, String newFieldName) {
    }

    public void removeClassMethod(String className, String methodDetails) {
    }

    public void removeClassField(String className, String fieldName) {
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

        if (class1 != null && class2 != null) {
            // Create the Aggregation relationship between class1 and class2
            Aggregation aggregation = new Aggregation(class1, class2, "Aggregation", 0, 0);  // Position can be adjusted
            model.addRelationship(aggregation);  // Add to diagram model
        }
    }
    public void updateAggregation(String className1, String newClassName1, String className2, String newClassName2) {
        // Find and update the relationship if exists
        for (Relationship relationship : model.getRelationships()) {
            if (relationship instanceof Aggregation) {
                Aggregation aggregation = (Aggregation) relationship;
                if (aggregation.getFrom().getDetails().equals(className1) && aggregation.getTo().getDetails().equals(className1)) {
                    // Update the components if the relationship is found
                    Class newClass1 = getClassByName(newClassName1);
                    Class newClass2 = getClassByName(newClassName2);
                    if (newClass1 != null && newClass2 != null) {
                        aggregation.setFrom(newClass1);
                        aggregation.setTo(newClass2);
                        break;
                    }
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

        if (class1 == null || class2 == null) {
            System.out.println("Error: One or both classes not found");
            return;
        }

        // Create the association and add it to the diagram model
        Association association = new Association(class1, class2, "Association", 0, 0, multiplicity1, multiplicity2);
        model.addRelationship(association);
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

    public void updateAssociation(String oldClassName1, String newClassName1, String oldClassName2, String newClassName2) {
        for (Relationship relationship : model.getRelationships()) {
            if (relationship instanceof Association) {
                Association association = (Association) relationship;

                // Check if the association is between the old class names
                if (association.getFrom().getDetails().equals(oldClassName1) &&
                        association.getTo().getDetails().equals(oldClassName2)) {

                    // Update the components (assuming findClassByName works for the new names)
                    Component newClass1 = findComponentByName(newClassName1);
                    Component newClass2 = findComponentByName(newClassName2);

                    if (newClass1 != null && newClass2 != null) {
                        association.setFrom(newClass1);
                        association.setTo(newClass2);
                    }
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
            generalization composition = new generalization(class1, class2, "Composition", 0, 0);  // Position can be adjusted
            model.addRelationship(composition);  // Add to diagram model
        }
    }

    public void updateComposition(String className1, String newClassName1, String className2, String newClassName2) {
        // Find and update the relationship if exists
        for (Relationship relationship : model.getRelationships()) {
            if (relationship instanceof generalization) {
                generalization composition = (generalization) relationship;
                if (composition.getFrom().getDetails().equals(className1) && composition.getTo().getDetails().equals(className2)) {
                    // Update the components if the relationship is found
                    Class newClass1 = getClassByName(newClassName1);
                    Class newClass2 = getClassByName(newClassName2);
                    if (newClass1 != null && newClass2 != null) {
                        composition.setFrom(newClass1);
                        composition.setTo(newClass2);
                        break;
                    }
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
            Inherritance inherritance = new Inherritance(class1, class2, "Inherritane", 0, 0);  // Position can be adjusted
            model.addRelationship(inherritance);  // Add to diagram model
        }
    }

    public void updateinherritance(String className1, String newClassName1, String className2, String newClassName2) {
        // Find and update the relationship if exists
        for (Relationship relationship : model.getRelationships()) {
            if (relationship instanceof Inherritance) {
                Inherritance inherritance = (Inherritance) relationship;
                if (inherritance.getFrom().getDetails().equals(className1) && inherritance.getTo().getDetails().equals(className2)) {
                    // Update the components if the relationship is found
                    Class newClass1 = getClassByName(newClassName1);
                    Class newClass2 = getClassByName(newClassName2);
                    if (newClass1 != null && newClass2 != null) {
                        inherritance.setFrom(newClass1);
                        inherritance.setTo(newClass2);
                        break;
                    }
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

}

