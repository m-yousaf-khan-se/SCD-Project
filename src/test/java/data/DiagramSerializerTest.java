package data;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import models.DiagramModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class DiagramSerializerTest {

    @Test
    void testSaveToFileSuccessfully() throws Exception {
        // Mock DiagramModel and File
        DiagramModel mockDiagram = mock(DiagramModel.class);
        File mockFile = mock(File.class);

        // Mock file behavior
        when(mockFile.getPath()).thenReturn("mockPath");
        when(mockFile.getName()).thenReturn("mockFile.json");

        // Spy on ObjectMapper
        ObjectMapper objectMapperSpy = spy(new ObjectMapper());

        // Mock ObjectWriter
        ObjectWriter mockObjectWriter = mock(ObjectWriter.class);
        when(objectMapperSpy.writer(any(DefaultPrettyPrinter.class))).thenReturn(mockObjectWriter);

        // Use reflection to inject the spied ObjectMapper
        Field field = DiagramSerializer.class.getDeclaredField("objectMapper");
        field.setAccessible(true);
        field.set(null, objectMapperSpy);

        // Call saveToFile
        DiagramSerializer.saveToFile(mockDiagram, mockFile);

        // Verify ObjectWriter's writeValue was called
        verify(mockObjectWriter).writeValue(eq(mockFile), eq(mockDiagram));
    }

    @Test
    void testSaveToFileThrowsIOException() throws Exception {
        // Mock DiagramModel and File
        DiagramModel mockDiagram = mock(DiagramModel.class);
        File mockFile = mock(File.class);

        // Mock file behavior
        when(mockFile.getPath()).thenReturn("mockPath");
        when(mockFile.getName()).thenReturn("mockFile.json");

        // Spy on ObjectMapper and mock ObjectWriter
        ObjectMapper objectMapperSpy = spy(new ObjectMapper());
        ObjectWriter mockWriter = mock(ObjectWriter.class);

        // Stub methods
        when(objectMapperSpy.writer(any(DefaultPrettyPrinter.class))).thenReturn(mockWriter);
        doThrow(new IOException("Mock IOException")).when(mockWriter).writeValue(eq(mockFile), eq(mockDiagram));

        // Use reflection to inject the spied ObjectMapper
        Field field = DiagramSerializer.class.getDeclaredField("objectMapper");
        field.setAccessible(true);
        field.set(null, objectMapperSpy);

        // Assert that no exception is thrown during the method call
        assertDoesNotThrow(() -> DiagramSerializer.saveToFile(mockDiagram, mockFile));
    }

}
