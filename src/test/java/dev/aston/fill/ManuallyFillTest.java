package dev.aston.fill;

import dev.aston.FileWrite.FileWrite;
import dev.aston.fill.Validate.ManuallyValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class ManuallyFillTest {

    private ObjectAddService objectAddService;
    private ManuallyValidate validate;
    private FileWrite fileWrite;
    private ManuallyFill manuallyFill;

    @BeforeEach
    void setUp() {
        objectAddService = mock(ObjectAddService.class);
        validate = mock(ManuallyValidate.class);
        fileWrite = mock(FileWrite.class);

        manuallyFill = new ManuallyFill(objectAddService);

        manuallyFill.validate = validate;
        manuallyFill.fileWrite = fileWrite;
    }

    @Test
    void testFillWithObjectPerson() {
        when(validate.checkingString("name")).thenReturn("John");
        when(validate.checkingString("surname")).thenReturn("Doe");
        when(validate.checkingInt("age")).thenReturn(30);

        manuallyFill.fillWithObject("Person");

        verify(objectAddService).addPerson("John", "Doe", 30);
        verify(fileWrite).writeObjectToFile(any());
    }

    @Test
    void testFillWithObjectPhone() {
        when(validate.checkingString("brand")).thenReturn("Samsung");
        when(validate.checkingString("model")).thenReturn("Galaxy S23");
        when(validate.checkingInt("memory")).thenReturn(256);
        when(validate.checkingInt("displaySize")).thenReturn(6);

        manuallyFill.fillWithObject("Phone");

        verify(objectAddService).addPhone("Samsung", "Galaxy S23", 256, 6);
        verify(fileWrite).writeObjectToFile(any());
    }

    @Test
    void testFillWithObjectCar() {
        when(validate.checkingString("brand")).thenReturn("Toyota");
        when(validate.checkingString("model")).thenReturn("Camry");
        when(validate.checkingInt("year")).thenReturn(2020);

        manuallyFill.fillWithObject("Car");

        verify(objectAddService).addCar("Toyota", "Camry", 2020);
        verify(fileWrite).writeObjectToFile(any());
    }

    @Test
    void testFillWithObjectUnknownType() {
        manuallyFill.fillWithObject("Unknown");

        verifyNoInteractions(objectAddService);
        verifyNoInteractions(fileWrite);
    }
}
