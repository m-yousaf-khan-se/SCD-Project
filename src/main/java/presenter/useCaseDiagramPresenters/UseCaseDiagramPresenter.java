
package presenter.useCaseDiagramPresenters;

import controllers.ViewIController;

import models.Component;
import models.DiagramModel;
import models.Relationship;
import models.classdiagram.Class;
import models.usecase.Actor;
import models.usecase.Association;
import models.usecase.UseCase;
import models.usecase.Extend;
import models.usecase.Include;
import  models.usecase.UseCaseDiagram;

import java.util.ArrayList;
import java.util.List;

public class UseCaseDiagramPresenter{
    ViewIController view;
    DiagramModel model;
    models.usecase.Actor actor;
    models.usecase.UseCase useCase;
    models.usecase.Include include;
    models.usecase.Extend extend;
    models.usecase.Association association;
    models.usecase.UseCaseDiagram useCaseDiagram;


    private List<Actor> actors = new ArrayList<>();
    private List<UseCase> useCases = new ArrayList<>();
    //actor,useCase,include,extend,associations,actors,useCases,parentView
    //Constructor
    public UseCaseDiagramPresenter(DiagramModel model,models.usecase.Actor actor,models.usecase.UseCase useCase,models.usecase.Include include,models.usecase.Extend extend,models.usecase.Association association,List<Actor> actors,List<UseCase> useCases,ViewIController view) {
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
        for (Actor actor : actors) {
            if (actor.getName().equals(oldName)) {
                actor.setName(newName);

                // Ensure the DiagramModel is updated as well
                List<Component> components = model.getComponents();
                for (Component component : components) {
                    if (component instanceof Actor && ((Actor) component).getName().equals(oldName)) {
                        ((Actor) component).setName(newName);
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
        model.getComponents().removeIf(component -> component instanceof Actor && ((Actor) component).getName().equals(name));
    }

    public void setActorCoordinates(String ActorName, int x, int y) {
        for (Component component : model.getComponents()) {
            if (component instanceof models.usecase.Actor && component.getDetails().equals(ActorName)) {
                models.usecase.Actor actor1 = (models.usecase.Actor) component;
                actor1.setX(x);
                actor1.setY(y);
                break;
            }
        }
    }

    //----------------------related to Use Cases------------------------------

    public void addUseCase(String name) {
        UseCase useCase = new UseCase(name);

        // Add the class to the local list
        useCases.add(useCase);

        // Add the class as a component to the DiagramModel
        model.addComponent(useCase);
    }
    // Update the Use Case name in the DiagramModel
    public void updateUseCaseName(String oldName, String newName) {
        for (UseCase useCase : useCases) {
            if (useCase.getName().equals(oldName)) {
                useCase.setName(newName);

                // Ensure the DiagramModel is updated
                List<Component> components = model.getComponents();
                for (Component component : components) {
                    if (component instanceof UseCase && ((UseCase) component).getName().equals(oldName)) {
                        ((UseCase) component).setName(newName);
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
        model.getComponents().removeIf(component -> component instanceof UseCase && ((UseCase) component).getName().equals(name));
    }


    public void setUseCaseCoordinates(String usecaseName, int x, int y) {
        for (Component component : model.getComponents()) {
            if (component instanceof models.usecase.UseCase && component.getDetails().equals(usecaseName)) {
                models.usecase.UseCase useCase1 = (models.usecase.UseCase) component;
                useCase1.setX(x);
                useCase1.setY(y);
                break;
            }
        }
    }
//----------------------related to Relationships------------------------------

    //----------------------related to Association------------------------------

    private UseCase getUseCaseByName(String useCaseName) {
        for (Component component : model.getComponents()) {
            if (component instanceof UseCase && ((UseCase) component).getName().equals(useCaseName)) {
                return (UseCase) component;
            }
        }
        return null;
    }
    private Actor getActorByName(String actorName) {
        for (Component component : model.getComponents()) {
            if (component instanceof Actor && ((Actor) component).getName().equals(actorName)) {
                return (Actor) component;
            }
        }
        return null;
    }
    public void addAssociation(String actorName, String useCaseName) {
        // Get the Actor and UseCase components using their names provided
        Actor actorObj = getActorByName(actorName);
        UseCase useCaseObj = getUseCaseByName(useCaseName);

        if (actorObj != null && useCaseObj != null) {
            // Create the Association link relationship between actor and useCase
            Association association = new Association(actorObj, useCaseObj, "Association", 0, 0);  // Position can be adjusted
            model.addRelationship(association);  // Add to diagram model
        }
    }
    public void updateAssociation(String oldActorName, String newActorName, String oldUseCaseName, String newUseCaseName) {
        // Find and update the relationship if exists
        for (Relationship relationship : model.getRelationships()) {
            if (relationship instanceof Association) {
                Association association = (Association) relationship;
                if (association.getFrom().getDetails().equals(oldActorName) && association.getTo().getDetails().equals(oldActorName)) {
                    // Update the components if the relationship is found
                    Actor actorObj = getActorByName(newActorName);
                    UseCase useCaseObj = getUseCaseByName(newUseCaseName);
                    if (newActorName != null && newUseCaseName != null) {
                        association.setFrom(actorObj);
                        association.setTo(useCaseObj);
                        break;
                    }
                }
            }
        }
    }
    public void removeAssociation(String actorName, String useCaseName) {
        // Remove the aggregation relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof Association &&
                        ((Association) relationship).getFrom().getDetails().equals(actorName) &&
                        ((Association) relationship).getTo().getDetails().equals(actorName));
    }

    // Extend



    public void addExtend(String useCaseName1, String useCaseName2) {
        // Get the Actor and UseCase components using their names provided
        UseCase useCaseObj1 = getUseCaseByName(useCaseName1);
        UseCase useCaseObj2 = getUseCaseByName(useCaseName2);


        if (useCaseObj1 != null && useCaseObj2 != null) {
            // Create the Association link relationship between actor and useCase
            Extend extend = new Extend(useCaseObj1, useCaseObj2, "Extend", 0, 0);  // Position can be adjusted
            model.addRelationship(extend);  // Add to diagram model
        }
    }
    public void updateExtend(String oldUseCaseName1, String newUseCaseName1, String oldUseCaseName2, String newUseCaseName2) {
        // Find and update the relationship if exists
        for (Relationship relationship : model.getRelationships()) {
            if (relationship instanceof Extend) {
                Extend extend = (Extend) relationship;
                if (extend.getFrom().getDetails().equals(oldUseCaseName1) && extend.getTo().getDetails().equals(oldUseCaseName1)) {
                    // Update the components if the relationship is found
                    UseCase useCaseObj1 = getUseCaseByName(newUseCaseName1);
                    UseCase useCaseObj2 = getUseCaseByName(newUseCaseName2);
                    if (newUseCaseName1 != null && newUseCaseName2 != null) {
                        extend.setFrom(useCaseObj1);
                        extend.setTo(useCaseObj2);
                        break;
                    }
                }
            }
        }
    }
    public void removeExtend(String useCaseName1, String useCaseName2) {
        // Remove the aggregation relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof Extend &&
                        ((Extend) relationship).getFrom().getDetails().equals(useCaseName1) &&
                        ((Extend) relationship).getTo().getDetails().equals(useCaseName2));
    }



    //Include


    public void addInclude(String useCaseName1, String useCaseName2) {
        // Get the Actor and UseCase components using their names provided
        UseCase useCaseObj1 = getUseCaseByName(useCaseName1);
        UseCase useCaseObj2 = getUseCaseByName(useCaseName2);


        if (useCaseObj1 != null && useCaseObj2 != null) {
            // Create the Association link relationship between actor and useCase
            Include include = new Include(useCaseObj1, useCaseObj2, "Include", 0, 0);  // Position can be adjusted
            model.addRelationship(include);  // Add to diagram model
        }
    }
    public void updateInclude(String oldUseCaseName1, String newUseCaseName1, String oldUseCaseName2, String newUseCaseName2) {
        // Find and update the relationship if exists
        for (Relationship relationship : model.getRelationships()) {
            if (relationship instanceof Include) {
                Include include = (Include) relationship;
                if (include.getFrom().getDetails().equals(oldUseCaseName1) && include.getTo().getDetails().equals(oldUseCaseName1)) {
                    // Update the components if the relationship is found
                    UseCase useCaseObj1 = getUseCaseByName(newUseCaseName1);
                    UseCase useCaseObj2 = getUseCaseByName(newUseCaseName2);
                    if (newUseCaseName1 != null && newUseCaseName2 != null) {
                        include.setFrom(useCaseObj1);
                        include.setTo(useCaseObj2);
                        break;
                    }
                }
            }
        }
    }
    public void removeInclude(String useCaseName1, String useCaseName2) {
        // Remove the aggregation relationship based on class names
        model.getRelationships().removeIf(relationship ->
                relationship instanceof Include &&
                        ((Include) relationship).getFrom().getDetails().equals(useCaseName1) &&
                        ((Include) relationship).getTo().getDetails().equals(useCaseName2));
    }


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


}
