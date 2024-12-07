package models;
import models.classdiagram.Aggregation;
import models.classdiagram.Association;
import models.classdiagram.generalization;

import java.util.List;

public class CodeGenerator {

    CodeGenerator(){}
    public void generateCode(DiagramModel diagramModel) {
        clearConsole(); // Clear the console before printing

        for (Component component : diagramModel.getComponents()) {
            if (component instanceof models.classdiagram.Class) {
                String classCode = generateClassCode((models.classdiagram.Class) component, diagramModel);
                System.out.println("Generated Code for " + ((models.classdiagram.Class) component).getName() + ":\n");
                System.out.println(classCode);
                System.out.println("--------------------------------------------------");
            }
        }
    }

    private String generateClassCode(models.classdiagram.Class clazz, DiagramModel diagramModel) {
        StringBuilder code = new StringBuilder();

        // Add class declaration
        code.append("public class ").append(clazz.getName());

        // Handle inheritance
        String parentClass = getParentClass(clazz, diagramModel);
        if (parentClass != null) {
            code.append(" extends ").append(parentClass);
        }

        code.append(" {\n");

        // Handle relationships (Association, Aggregation, Composition)
        addRelationships(clazz, diagramModel, code);

        // Close class
        code.append("}\n");

        return code.toString();
    }

    private String getParentClass(models.classdiagram.Class clazz, DiagramModel diagramModel) {
        // Iterate through relationships to check for inheritance
        for (Relationship relationship : diagramModel.getRelationships()) {
            if ("Inheritance".equalsIgnoreCase(relationship.getType())) { // Check if the relationship is Inheritance
                if (relationship.getFrom() == clazz) {
                    Component parentComponent = relationship.getTo();
                    if (parentComponent instanceof models.classdiagram.Class) {
                        return ((models.classdiagram.Class) parentComponent).getName(); // Safely cast and get name
                    }
                }
            }
        }
        return null; // No inheritance
    }


    private void addRelationships(models.classdiagram.Class clazz, DiagramModel diagramModel, StringBuilder code) {
        // Handle Association, Aggregation, and Composition
        for (Relationship relationship : diagramModel.getRelationships()) {
            if (relationship.getFrom() == clazz) { // Check if this relationship starts from the current class
                String relationshipType = relationship.getType();
                Component toComponent = relationship.getTo(); // Get the target component
                String relatedClassName = toComponent.getName(); // Get the name of the target class/component

                if ("Association".equalsIgnoreCase(relationshipType)) {
                    // Add association
                    code.append("    private ").append(relatedClassName).append(" ")
                            .append(relatedClassName.toLowerCase()).append(";\n");
                } else if ("Aggregation".equalsIgnoreCase(relationshipType)) {
                    // Add aggregation
                    code.append("    private List<").append(relatedClassName).append("> ")
                            .append(relatedClassName.toLowerCase()).append("s;\n");
                } else if ("Composition".equalsIgnoreCase(relationshipType)) {
                    // Add composition
                    code.append("    private ").append(relatedClassName).append(" ")
                            .append(relatedClassName.toLowerCase()).append(";\n");
                }
            }
        }
    }



    private void clearConsole() {
        // Attempt to clear the console (platform-dependent)
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            System.out.println("Unable to clear console.");
        }
    }
}
