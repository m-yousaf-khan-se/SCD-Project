package data;

//public class DiagramSerializer {
//}


//package serialization;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.DiagramModel;
import java.io.File;
import java.io.IOException;

public class DiagramSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();

    public static void saveToFile(DiagramModel diagram, File file) {
        System.out.println("Saving File " + file.getPath().toString());
        try {
            objectMapper.writer(prettyPrinter).writeValue(file, diagram);
            System.out.println("Diagram saved to " + file.getName());
        } catch (IOException e) {
            System.err.println("Error saving diagram: " + e.getMessage());
        }
    }

    public static DiagramModel loadFromFile(File file) {
        try {
            return objectMapper.readValue(file, DiagramModel.class);
        } catch (IOException e) {
            System.err.println("Error loading diagram: " + e.getMessage());
        }
        return null;
    }
}
