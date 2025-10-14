package dev.aston.fill;

import dev.aston.fill.FileFounder.JsonParse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class FileFillTest {

    private JsonParse jsonParseMock;
    private FileFill fileFill;

    @BeforeEach
    void setUp() {
        jsonParseMock = Mockito.mock(JsonParse.class);
        fileFill = new FileFill(jsonParseMock);
    }

    @Test
    void testFillWithAllObjectsDelegatesToJsonParse() {
        fileFill.fillWithAllObjects();
        verify(jsonParseMock).collectAllInfoJson();
    }

    @Test
    void testFillWithObjectDoesNothing() {
        fileFill.fillWithObject("Person");
    }
}
