package models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import models.classdiagram.Aggregation;
import models.classdiagram.Association;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class CodeGeneratorTest {

    private CodeGenerator codeGenerator;
    private DiagramModel diagramModel;
    private models.classdiagram.Class classComponent;
    private Relationship inheritanceRelationship;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        codeGenerator = new CodeGenerator();
        diagramModel = mock(DiagramModel.class); // Mock DiagramModel
        classComponent = mock(models.classdiagram.Class.class); // Mock Class
        inheritanceRelationship = mock(Relationship.class); // Mock Relationship

        // Capture the output from System.out
        outputStream = new ByteArrayOutputStream();
        originalSystemOut = System.out;  // Save the original System.out
        System.setOut(new PrintStream(outputStream));  // Redirect System.out to capture the output
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalSystemOut);  // Restore original System.out after each test
    }

    @Test
    void testGenerateCodeForEmptyDiagram() {
        // Mocking an empty list of components and relationships
        when(diagramModel.getComponents()).thenReturn(List.of());
        when(diagramModel.getRelationships()).thenReturn(List.of());

        // Call generateCode
        codeGenerator.generateCode(diagramModel);

        // Verify that no code is printed
        assertEquals("", outputStream.toString().trim(), "Expected no output for empty diagram");
    }



    @Test
    void testGenerateCodeForClassComponent() {
        // Setup mock for the class component
        when(classComponent.getName()).thenReturn("MyClass");
        when(classComponent.getAttributes()).thenReturn(List.of());
        when(classComponent.getMethods()).thenReturn(List.of());
        when(diagramModel.getComponents()).thenReturn(List.of(classComponent));
        when(diagramModel.getRelationships()).thenReturn(List.of());

        // Call generateCode and capture the output
        String generatedCode = codeGenerator.generateCode(diagramModel);

        // Define the expected code
        String expectedCode = """
        //MyClass:\npublic class MyClass {
        }\n\n//--------------------------------------------------\n
        """;

        // Assert that the actual generated code matches the expected code
        assertEquals(expectedCode.trim(), generatedCode.trim(), "Generated code does not match the expected code.");
    }




    @Test
    void testGenerateCodeForClassWithInheritance() {
        // Mocking class names and inheritance
        models.classdiagram.Class subclass = mock(models.classdiagram.Class.class);
        models.classdiagram.Class superclass = mock(models.classdiagram.Class.class);

        // Set up mock behavior for class names
        when(subclass.getName()).thenReturn("Subclass");
        when(superclass.getName()).thenReturn("Superclass");

        // Mock inheritance relationship
        when(inheritanceRelationship.getType()).thenReturn("Inherritance");
        when(inheritanceRelationship.getFrom()).thenReturn(subclass);
        when(inheritanceRelationship.getTo()).thenReturn(superclass);

        // Mock DiagramModel to return the subclass and inheritance relationship
        when(diagramModel.getComponents()).thenReturn(List.of(subclass));
        when(diagramModel.getRelationships()).thenReturn(List.of(inheritanceRelationship));

        // Call generateCode and capture the output
        String generatedCode = codeGenerator.generateCode(diagramModel);

        // Define the expected generated code for a class with inheritance
        String expectedCode = """
        //Subclass:\npublic class Subclass extends Superclass {
        }\n\n//--------------------------------------------------\n
        """;

        // Assert that the generated code matches the expected code
        assertEquals(expectedCode.trim(), generatedCode.trim(), "Generated code does not match the expected inheritance structure.");
    }


    @Test
    void testGenerateCodeForClassWithAssociation() {
        // Setup mock for the class and related components
        models.classdiagram.Class class1 = mock(models.classdiagram.Class.class);
        models.classdiagram.Class class2 = mock(models.classdiagram.Class.class);
        when(class1.getName()).thenReturn("Class1");
        when(class2.getName()).thenReturn("Class2");

        // Mock an association relationship
        Relationship association = mock(Association.class);
        when(association.getType()).thenReturn("Association");
        when(association.getFrom()).thenReturn(class1);
        when(association.getTo()).thenReturn(class2);

        // Mock DiagramModel to return the relationship
        when(diagramModel.getComponents()).thenReturn(List.of(class1));
        when(diagramModel.getRelationships()).thenReturn(List.of(association));

        // Call generateCode
        codeGenerator.generateCode(diagramModel);

        // Verify that the generated code contains the association
        String expectedCode = "    private Class2 class2;\n";

        // Ensure the output matches only the expected code
        String generatedCode = extractRelevantOutput(outputStream);
        assertTrue(generatedCode.contains(expectedCode), "Expected association to be added to class code.");
    }

    // Helper method to extract relevant output from the output stream
    private String extractRelevantOutput(ByteArrayOutputStream outputStream) {
        String output = outputStream.toString();

        // Here, we assume that the relevant code starts with "public class" and ends with "}\n".
        int startIdx = output.indexOf("public class");
        int endIdx = output.indexOf("}\n", startIdx) + 2;  // Include the closing brace
        if (startIdx != -1 && endIdx != -1) {
            return output.substring(startIdx, endIdx);
        }
        return "";  // Return an empty string if no relevant output was found
    }
}
