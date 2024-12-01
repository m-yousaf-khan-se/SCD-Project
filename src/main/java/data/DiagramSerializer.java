package data;

//public class DiagramSerializer {
//}


//package serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.DiagramModel;
import java.io.File;
import java.io.IOException;

public class DiagramSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveToFile(DiagramModel diagram, String filename) {
        try {
            objectMapper.writeValue(new File(filename), diagram);
            System.out.println("Diagram saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving diagram: " + e.getMessage());
        }
    }

    public static DiagramModel loadFromFile(String filename) {
        try {
            return objectMapper.readValue(new File(filename), DiagramModel.class);
        } catch (IOException e) {
            System.err.println("Error loading diagram: " + e.getMessage());
        }
        return null;
    }
}
