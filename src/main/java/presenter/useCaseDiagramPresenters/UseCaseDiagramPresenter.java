package presenter.useCaseDiagramPresenters;

import controllers.ViewIController;
import models.Component;
import models.DiagramModel;
import models.IModel;
import models.Relationship;
import models.classdiagram.Aggregation;
import models.classdiagram.Class;
import models.usecase.Actor;
import models.usecase.Association;
import models.usecase.UseCase;

import java.util.ArrayList;
import java.util.List;

public class UseCaseDiagramPresenter{
    ViewIController view;
    IModel model1;
    models.usecase.Actor actor;

    List<models.usecase.Actor> actors = new ArrayList<>();
    List<UseCase> useCases=new ArrayList<>();
    private DiagramModel model; // Reference to the DiagramModel

    //Constructor
    public UseCaseDiagramPresenter(IModel model1, ViewIController view) {


        //initialize models
        this.model = (DiagramModel)model1;
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
        for (Actor actor : actors) {
            if (actor.getName().equals(oldName)) {
                actor.setName(newName);

                // Ensure the DiagramModel is updated
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
        //Remove the actor from actors list
        actors.removeIf(actor -> actor.getName().equals(name));

        // Remove the actor from the DiagramModel's components
        model.getComponents().removeIf(component -> component instanceof Actor && ((Actor) component).getName().equals(name));
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
                relationship instanceof Aggregation &&
                        ((Aggregation) relationship).getFrom().getDetails().equals(actorName) &&
                        ((Aggregation) relationship).getTo().getDetails().equals(actorName));
    }

}
