package dev.aston.fill;

import dev.aston.FileWrite.FileWrite;
import dev.aston.entities.Car;
import dev.aston.entities.Person;
import dev.aston.entities.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RandomFillTest {

    private InitCollection initCollection;
    private ObjectAddService objectAddService;
    private FileWrite fileWriteMock;
    private RandomFill randomFill;

    @BeforeEach
    void setUp() {
        initCollection = new InitCollection();
        objectAddService = new ObjectAddService(initCollection);

        fileWriteMock = mock(FileWrite.class);

        randomFill = new RandomFill(objectAddService);
        randomFill.fileWrite = fileWriteMock; // Подменяем
    }

    @Test
    void testFillWithObjectPerson() {
        randomFill.fillWithObject("Person");

        assertFalse(initCollection.getList().isEmpty());
        Object obj = initCollection.getList().get(0);
        assertTrue(obj instanceof Person);

        verify(fileWriteMock).writeObjectToFile(obj);
    }

    @Test
    void testFillWithObjectPhone() {
        randomFill.fillWithObject("Phone");

        assertFalse(initCollection.getList().isEmpty());
        Object obj = initCollection.getList().get(0);
        assertTrue(obj instanceof Phone);

        verify(fileWriteMock).writeObjectToFile(obj);
    }

    @Test
    void testFillWithObjectCar() {
        randomFill.fillWithObject("Car");

        assertFalse(initCollection.getList().isEmpty());
        Object obj = initCollection.getList().get(0);
        assertTrue(obj instanceof Car);

        verify(fileWriteMock).writeObjectToFile(obj);
    }

    @Test
    void testMultipleObjectsAdded() {
        for (int i = 0; i < 5; i++) {
            randomFill.fillWithObject("Person");
        }

        assertEquals(5, initCollection.getList().size());
        initCollection.getList().forEach(obj -> assertTrue(obj instanceof Person));
    }
}
