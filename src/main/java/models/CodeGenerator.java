package models;

import models.classdiagram.Aggregation;
import models.classdiagram.Association;
import models.classdiagram.generalization;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeGenerator {



    public static String generateCode(DiagramModel diagramModel) {
        clearConsole(); // Clear the console before printing
        StringBuilder allClassCode = new StringBuilder();

        for (Component component : diagramModel.getComponents()) {
           if (component instanceof models.classdiagram.Class) {
               String classCode = generateClassCode((models.classdiagram.Class) component, diagramModel);
               allClassCode
                      .append("//")
                      .append(((models.classdiagram.Class) component).getName())
                      .append(":\n")
                      .append(classCode)
                       .append("\n//--------------------------------------------------\n");
        }
    }

    System.out.println(allClassCode.toString()); // Print all the generated code
    return allClassCode.toString(); // Return the concatenated code
}


    private static String generateClassCode(models.classdiagram.Class clazz, DiagramModel diagramModel) {
        StringBuilder code = new StringBuilder();

        // Add class declaration
        code.append("public class ").append(clazz.getName());

        // Handle inheritance
        String parentClass = getParentClass(clazz, diagramModel);
        if (parentClass != null) {
            code.append(" extends ").append(parentClass);
        }

        code.append(" {\n");

        // Add attributes
        addAttributes(clazz, code);

        addRelationships(clazz, diagramModel, code);

        // Add methods
        addMethods(clazz, code);

        // Handle relationships (Association, Aggregation, Composition)
        //addRelationships(clazz, diagramModel, code);

        // Close class
        code.append("}\n");

        return code.toString();
    }

    private static String getParentClass(models.classdiagram.Class clazz, DiagramModel diagramModel) {
        // Iterate through relationships to check for inheritance
        for (Relationship relationship : diagramModel.getRelationships()) {
            //System.out.println("Inside the generate code getparent class function");
            //System.out.println("Relationship Type: " + relationship.getType());

            if ("Inherritance".equalsIgnoreCase(relationship.getType())) {
                //System.out.println("Inside the generate code getparent class if check1");

                // Check if relationship.getFrom() is valid and is not the same as clazz (to prevent self-inheritance)
                if (relationship.getFrom() != null && relationship.getFrom().getName().equals(clazz.getName())) {
                    //System.out.println("Inside the generate code getparent class if check2");

                    Component parentComponent = relationship.getTo();
                    if (parentComponent instanceof models.classdiagram.Class) {
                        return ((models.classdiagram.Class) parentComponent).getName(); // Return the parent class name
                    }
                }
            }
        }
        return null; // No inheritance or self-inheritance found
    }


    private static void addAttributes(models.classdiagram.Class clazz, StringBuilder code) {
        // Use a Set to track already added attributes
        Set<String> addedAttributes = new HashSet<>();

        for (String attribute : clazz.getAttributes()) {
            if (addedAttributes.contains(attribute)) {
                continue; // Skip duplicates
            }
            addedAttributes.add(attribute); // Mark this attribute as added

            // Determine visibility
            String visibility;
            if (attribute.startsWith("+")) {
                visibility = "public";
            } else if (attribute.startsWith("-")) {
                visibility = "private";
            } else if (attribute.startsWith("#")) {
                visibility = "protected";
            } else {
                visibility = "private"; // Default to private if no visibility is specified
            }

            // Add the attribute to the code
            code.append("    ").append(visibility).append(" ").append(attribute.substring(1).trim()).append(";\n");
        }
    }


    private static void addMethods(models.classdiagram.Class clazz, StringBuilder code) {
        // Use a Set to track already added methods
        Set<String> addedMethods = new HashSet<>();

        for (String method : clazz.getMethods()) {
            if (addedMethods.contains(method)) {
                continue; // Skip duplicates
            }
            addedMethods.add(method); // Mark this method as added

            // Determine visibility
            String visibility;
            if (method.startsWith("+")) {
                visibility = "public";
            } else if (method.startsWith("-")) {
                visibility = "private";
            } else if (method.startsWith("#")) {
                visibility = "protected";
            } else {
                visibility = "public"; // Default to public if no visibility is specified
            }

            // Add the method to the code
            code.append("    ").append(visibility).append(" ").append(method.substring(1).trim()).append("() {\n");
            code.append("        // TODO: Implement this method\n");
            code.append("    }\n");
        }
    }


    private static void addRelationships(models.classdiagram.Class clazz, DiagramModel diagramModel, StringBuilder code) {
        // Handle Association, Aggregation, and Composition
        //System.out.println("Inside addRelationship");
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
                }
                else if ("generalization".equalsIgnoreCase(relationshipType)) {
                    // Add aggregation
                    code.append("    private List<").append(relatedClassName).append("> ")
                            .append(relatedClassName.toLowerCase()).append("s;\n");
                }
            }
        }
    }

    private static void clearConsole() {
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
