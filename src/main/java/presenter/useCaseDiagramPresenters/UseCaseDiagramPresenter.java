
package presenter.useCaseDiagramPresenters;

import controllers.ViewIController;


import data.DiagramSerializer;
import models.Component;
import models.DiagramModel;
import models.Relationship;
import models.classdiagram.Aggregation;
import models.classdiagram.Class;
import models.usecase.Actor;
import models.usecase.Association;
import models.usecase.UseCase;
import models.usecase.Extend;
import models.usecase.Include;
import  models.usecase.UseCaseDiagram;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UseCaseDiagramPresenter{
    ViewIController view;
    models.DiagramModel model;
    models.usecase.Actor actor;
    models.usecase.UseCase useCase;
    models.usecase.Include include;
    models.usecase.Extend extend;
    models.usecase.Association association;
    models.usecase.UseCaseDiagram useCaseDiagram;


    private List<models.usecase.Actor> actors = new ArrayList<>();
    private List<models.usecase.UseCase> useCases = new ArrayList<>();
    //actor,useCase,include,extend,associations,actors,useCases,parentView
    //Constructor
    public UseCaseDiagramPresenter(models.DiagramModel model,models.usecase.Actor actor,models.usecase.UseCase useCase,models.usecase.Include include,models.usecase.Extend extend,models.usecase.Association association,List<models.usecase.Actor> actors,List<models.usecase.UseCase> useCases,ViewIController view) {
        this.model=model;
        this.actor=actor;
        this.useCase=useCase;
        this.include=include;
        this.extend=extend;
        this.association=association;
        this.actors=actors;
        this.useCases=useCases;
        this.view = view;
    }

    //----------------------related to Actors------------------------------
    public void addActor(String name) {
        models.usecase.Actor actor = new models.usecase.Actor(name);

        // Add the class to the local list
        actors.add(actor);

        // Add the class as a component to the DiagramModel
        model.addComponent(actor);
    }
    // Update the actor name in the DiagramModel
    public void updateActorName(String oldName, String newName) {
        System.out.println("Inside updateActorName");

        // Find and update in the local list
        for (models.usecase.Actor actor : actors) {
            if (actor.getName().equals(oldName)) {
                actor.setName(newName);

                // Ensure the DiagramModel is updated as well
                List<models.Component> components = model.getComponents();
                for (models.Component component : components) {
                    if (component instanceof models.usecase.Actor && ((models.usecase.Actor) component).getName().equals(oldName)) {
                        ((models.usecase.Actor) component).setName(newName);
                        break;
                    }
                }
                break;
            }
        }
    }

    // Remove an Actor from the DiagramModel
    public void removeActor(String name) {
        // Remove the actor from the local list
        actors.removeIf(actor -> actor.getName().equals(name));

        // Remove the actor from the DiagramModel's components
        model.getComponents().removeIf(component -> component instanceof models.usecase.Actor && ((models.usecase.Actor) component).getName().equals(name));
    }

    public void setActorCoordinates(String ActorName, double x, double y) {
        for (models.Component component : model.getComponents()) {
            if (component instanceof models.usecase.Actor && component.getName().equals(ActorName)) {
                models.usecase.Actor actor1 = (models.usecase.Actor) component;
                actor1.setX(x);
                actor1.setY(y);
                break;
            }
        }
    }

    //----------------------related to Use Cases------------------------------

    public void addUseCase(String name) {
        models.usecase.UseCase useCase = new models.usecase.UseCase(name);

        // Add the class to the local list
        useCases.add(useCase);

        // Add the class as a component to the DiagramModel
        model.addComponent(useCase);
    }
    // Update the Use Case name in the DiagramModel
    public void updateUseCaseName(String oldName, String newName) {
        for (models.usecase.UseCase useCase : useCases) {
            if (useCase.getName().equals(oldName)) {
                useCase.setName(newName);

                // Ensure the DiagramModel is updated
                List<models.Component> components = model.getComponents();
                for (models.Component component : components) {
                    if (component instanceof models.usecase.UseCase && ((models.usecase.UseCase) component).getName().equals(oldName)) {
                        ((models.usecase.UseCase) component).setName(newName);
                        break;
                    }
                }
                break;
            }
        }
    }


    // Remove the Use Case from the DiagramModel and arraylist
    public void removeUseCase(String name) {
        // Remove the use case from useCases list
        useCases.removeIf(useCase -> useCase.getName().equals(name));

        // Remove the use case from the DiagramModel's components
        model.getComponents().removeIf(component -> component instanceof models.usecase.UseCase && ((models.usecase.UseCase) component).getName().equals(name));
    }


    public void setUseCaseCoordinates(String usecaseName, double x, double y) {
        for (models.Component component : model.getComponents()) {
            if (component instanceof models.usecase.UseCase && component.getName().equals(usecaseName)) {
                models.usecase.UseCase useCase1 = (models.usecase.UseCase) component;
                useCase1.setX(x);
                useCase1.setY(y);
                break;
            }
        }
    }
//----------------------related to Relationships------------------------------

    //----------------------related to Association------------------------------

    private models.usecase.UseCase getUseCaseByName(String useCaseName) {
        for (models.Component component : model.getComponents()) {
            if (component instanceof models.usecase.UseCase && ((models.usecase.UseCase) component).getName().equals(useCaseName)) {
                return (models.usecase.UseCase) component;
            }
        }
        return null;
    }
    private models.usecase.Actor getActorByName(String actorName) {
        for (models.Component component : model.getComponents()) {
            if (component instanceof models.usecase.Actor && ((models.usecase.Actor) component).getName().equals(actorName)) {
                return (models.usecase.Actor) component;
            }
        }
        return null;
    }
    public void addAssociation(String actorName, String useCaseName) {
        // Get the Actor and UseCase components using their names provided
        models.usecase.Actor actorObj = getActorByName(actorName);
        models.usecase.UseCase useCaseObj = getUseCaseByName(useCaseName);

        if (actorObj != null && useCaseObj != null) {
            // Create the Association link relationship between actor and useCase
            models.usecase.Association association = new models.usecase.Association(actorObj, useCaseObj, "Association", 0, 0);  // Position can be adjusted
            model.addRelationship(association);  // Add to diagram model
        }
    }
//    public void updateAssociation(String oldActorName, String newActorName, String oldUseCaseName, String newUseCaseName) {
//        // Find and update the relationship if exists
//        for (models.Relationship relationship : model.getRelationships()) {
//            if (relationship instanceof models.usecase.Association) {
//                models.usecase.Association association = (models.usecase.Association) relationship;
//                if (association.getFrom().getDetails().equals(oldActorName) && association.getTo().getDetails().equals(oldActorName)) {
//                    // Update the components if the relationship is found
//                    models.usecase.Actor actorObj = getActorByName(newActorName);
//                    models.usecase.UseCase useCaseObj = getUseCaseByName(newUseCaseName);
//                    if (newActorName != null && newUseCaseName != null) {
//                        association.setFrom(actorObj);
//                        association.setTo(useCaseObj);
//                        break;
//                    }
//                }
//            }
//        }
//    }

    public void updateAssociation(String oldActorName, String newActorName, String oldUseCaseName, String newUseCaseName) {
        System.out.println("Presenter Update Inherritance");
        // System.out.println("Old class names: " + oldClassName1 + ", " + oldClassName2);
        // System.out.println("New class names: " + newClassName1 + ", " + newClassName2);

        // Iterate through all relationships in the model
        for (Relationship relationship : model.getRelationships()) {
            System.out.println("Inside for loop");

            // Check if the relationship is an instance of Inherritance
            if (relationship instanceof Include) {
                System.out.println("Inside if condition");
                Include association = (Include) relationship;

                // Check if the relationship involves the old class names
                boolean isMatchingFrom = (oldActorName == null || association.getFrom().getDetails().equals(oldActorName));
                boolean isMatchingTo = (oldUseCaseName == null || association.getTo().getDetails().equals(oldUseCaseName));

                System.out.println("Checking relationship: " + association);
                System.out.println("Matching from: " + isMatchingFrom + ", Matching to: " + isMatchingTo);

                // If both the from and to classes match, update them
                if (isMatchingFrom || isMatchingTo) {
                    // Find the new components based on the new class names
                    Component newClass1 = getActorByName(newActorName);
                    Component newClass2 = getUseCaseByName(newUseCaseName);

                    // Update the "from" component if the new class is found
                    if (newClass1 != null) {
                        System.out.println("Setting 'from' to: " + newActorName);
                        association.setFrom(newClass1); // Update "from" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newActorName);
                    }

                    // Update the "to" component if the new class is found
                    if (newClass2 != null) {
                        System.out.println("Setting 'to' to: " + newUseCaseName);
                        association.setTo(newClass2); // Update "to" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newUseCaseName);
                    }

                    // Exit after updating the relationship
                    break;
                }
            }
        }
    }
    public void removeAssociation(String actorName, String useCaseName) {
        // Remove the aggregation relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof models.usecase.Association &&
                        ((models.usecase.Association) relationship).getFrom().getDetails().equals(actorName) &&
                        ((models.usecase.Association) relationship).getTo().getDetails().equals(actorName));
    }

    // Extend



    public void addExtend(String useCaseName1, String useCaseName2) {
        // Get the Actor and UseCase components using their names provided
        models.usecase.UseCase useCaseObj1 = getUseCaseByName(useCaseName1);
        models.usecase.UseCase useCaseObj2 = getUseCaseByName(useCaseName2);


        if (useCaseObj1 != null && useCaseObj2 != null) {
            // Create the Association link relationship between actor and useCase
            models.usecase.Extend extend = new models.usecase.Extend(useCaseObj1, useCaseObj2, "Extend", 0, 0);  // Position can be adjusted
            model.addRelationship(extend);  // Add to diagram model
        }
        else {
            models.usecase.Extend extend=new models.usecase.Extend();
        }
    }

    public void updateExtend(String oldUseCaseName1, String newUseCaseName1, String oldUseCaseName2, String newUseCaseName2) {
        System.out.println("Presenter Update Inherritance");
        // System.out.println("Old class names: " + oldClassName1 + ", " + oldClassName2);
        // System.out.println("New class names: " + newClassName1 + ", " + newClassName2);

        // Iterate through all relationships in the model
        for (Relationship relationship : model.getRelationships()) {
            System.out.println("Inside for loop");

            // Check if the relationship is an instance of Inherritance
            if (relationship instanceof Extend) {
                System.out.println("Inside if condition");
                Extend association = (Extend) relationship;

                // Check if the relationship involves the old class names
                boolean isMatchingFrom = (oldUseCaseName1 == null || association.getFrom().getDetails().equals(oldUseCaseName1));
                boolean isMatchingTo = (oldUseCaseName2 == null || association.getTo().getDetails().equals(oldUseCaseName2));

                System.out.println("Checking relationship: " + association);
                System.out.println("Matching from: " + isMatchingFrom + ", Matching to: " + isMatchingTo);

                // If both the from and to classes match, update them
                if (isMatchingFrom || isMatchingTo) {
                    // Find the new components based on the new class names
                    Component newClass1 = getUseCaseByName(newUseCaseName1);
                    Component newClass2 = getUseCaseByName(newUseCaseName2);

                    // Update the "from" component if the new class is found
                    if (newClass1 != null) {
                        System.out.println("Setting 'from' to: " + newUseCaseName1);
                        association.setFrom(newClass1); // Update "from" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newUseCaseName1);
                    }

                    // Update the "to" component if the new class is found
                    if (newClass2 != null) {
                        System.out.println("Setting 'to' to: " + newUseCaseName2);
                        association.setTo(newClass2); // Update "to" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newUseCaseName2);
                    }

                    // Exit after updating the relationship
                    break;
                }
            }
        }
    }
//    public void updateExtend(String oldUseCaseName1, String newUseCaseName1, String oldUseCaseName2, String newUseCaseName2) {
//        // Find and update the relationship if exists
//        for (models.Relationship relationship : model.getRelationships()) {
//            if (relationship instanceof models.usecase.Extend) {
//                models.usecase.Extend extend = (models.usecase.Extend) relationship;
//                if (extend.getFrom().getDetails().equals(oldUseCaseName1) && extend.getTo().getDetails().equals(oldUseCaseName1)) {
//                    // Update the components if the relationship is found
//                    models.usecase.UseCase useCaseObj1 = getUseCaseByName(newUseCaseName1);
//                    models.usecase.UseCase useCaseObj2 = getUseCaseByName(newUseCaseName2);
//                    if (newUseCaseName1 != null && newUseCaseName2 != null) {
//                        extend.setFrom(useCaseObj1);
//                        extend.setTo(useCaseObj2);
//                        break;
//                    }
//                }
//            }
//        }
//    }
    public void removeExtend(String useCaseName1, String useCaseName2) {
        // Remove the aggregation relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof models.usecase.Extend &&
                        ((models.usecase.Extend) relationship).getFrom().getDetails().equals(useCaseName1) &&
                        ((models.usecase.Extend) relationship).getTo().getDetails().equals(useCaseName2));
    }



    //Include


    public void addInclude(String useCaseName1, String useCaseName2) {
        // Get the Actor and UseCase components using their names provided
        models.usecase.UseCase useCaseObj1 = getUseCaseByName(useCaseName1);
        models.usecase.UseCase useCaseObj2 = getUseCaseByName(useCaseName2);


        if (useCaseObj1 != null && useCaseObj2 != null) {
            // Create the Association link relationship between actor and useCase
            models.usecase.Include include = new models.usecase.Include(useCaseObj1, useCaseObj2, "Include", 0, 0);  // Position can be adjusted
            model.addRelationship(include);  // Add to diagram model
        }
        else {
            models.usecase.Include include=new models.usecase.Include();
        }
    }
//    public void updateInclude(String oldUseCaseName1, String newUseCaseName1, String oldUseCaseName2, String newUseCaseName2) {
//        // Find and update the relationship if exists
//        for (models.Relationship relationship : model.getRelationships()) {
//            if (relationship instanceof models.usecase.Include) {
//                models.usecase.Include include = (models.usecase.Include) relationship;
//                if (include.getFrom().getDetails().equals(oldUseCaseName1) && include.getTo().getDetails().equals(oldUseCaseName1)) {
//                    // Update the components if the relationship is found
//                    models.usecase.UseCase useCaseObj1 = getUseCaseByName(newUseCaseName1);
//                    models.usecase.UseCase useCaseObj2 = getUseCaseByName(newUseCaseName2);
//                    if (newUseCaseName1 != null && newUseCaseName2 != null) {
//                        include.setFrom(useCaseObj1);
//                        include.setTo(useCaseObj2);
//                        break;
//                    }
//                }
//            }
//        }
//    }

    public void updateInclude(String oldUseCaseName1, String newUseCaseName1, String oldUseCaseName2, String newUseCaseName2) {
        System.out.println("Presenter Update Inherritance");
       // System.out.println("Old class names: " + oldClassName1 + ", " + oldClassName2);
       // System.out.println("New class names: " + newClassName1 + ", " + newClassName2);

        // Iterate through all relationships in the model
        for (Relationship relationship : model.getRelationships()) {
            System.out.println("Inside for loop");

            // Check if the relationship is an instance of Inherritance
            if (relationship instanceof Include) {
                System.out.println("Inside if condition");
                Include association = (Include) relationship;

                // Check if the relationship involves the old class names
                boolean isMatchingFrom = (oldUseCaseName1 == null || association.getFrom().getDetails().equals(oldUseCaseName1));
                boolean isMatchingTo = (oldUseCaseName2 == null || association.getTo().getDetails().equals(oldUseCaseName2));

                System.out.println("Checking relationship: " + association);
                System.out.println("Matching from: " + isMatchingFrom + ", Matching to: " + isMatchingTo);

                // If both the from and to classes match, update them
                if (isMatchingFrom || isMatchingTo) {
                    // Find the new components based on the new class names
                    Component newClass1 = getUseCaseByName(newUseCaseName1);
                    Component newClass2 = getUseCaseByName(newUseCaseName2);

                    // Update the "from" component if the new class is found
                    if (newClass1 != null) {
                        System.out.println("Setting 'from' to: " + newUseCaseName1);
                        association.setFrom(newClass1); // Update "from" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newUseCaseName1);
                    }

                    // Update the "to" component if the new class is found
                    if (newClass2 != null) {
                        System.out.println("Setting 'to' to: " + newUseCaseName2);
                        association.setTo(newClass2); // Update "to" with the new class
                    } else {
                        System.err.println("Warning: Could not find class: " + newUseCaseName2);
                    }

                    // Exit after updating the relationship
                    break;
                }
            }
        }
    }

    public void removeInclude(String useCaseName1, String useCaseName2) {
        // Remove the aggregation relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof models.usecase.Include &&
                        ((models.usecase.Include) relationship).getFrom().getDetails().equals(useCaseName1) &&
                        ((models.usecase.Include) relationship).getTo().getDetails().equals(useCaseName2));
    }


    public void setRelationshipCoordinates(String fromClassName, String toClassName, double labelX, double labelY) {
        for (models.Relationship relationship : model.getRelationships()) {
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
