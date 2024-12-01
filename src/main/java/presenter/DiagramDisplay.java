package presenter;

//public class DiagramDisplay {
//}

//package presentation;

import models.Component;
import models.Relationship;
import java.util.List;

public class DiagramDisplay {
    public static void displayComponents(List<Component> components) {
        for (Component component : components) {
            System.out.println(component.getDetails());
        }
    }

    public static void displayRelationships(List<Relationship> relationships) {
        for (Relationship relationship : relationships) {
            System.out.println("Relationship: " + relationship.getType() +
                    " from " + relationship.getFrom().getDetails() +
                    " to " + relationship.getTo().getDetails());
        }
    }
}
