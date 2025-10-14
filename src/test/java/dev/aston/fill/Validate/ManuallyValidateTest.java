package dev.aston.fill.Validate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ManuallyValidateTest {

    private ManuallyValidate manuallyValidate;
    private Scanner scannerMock;

    @BeforeEach
    void setUp() {
        manuallyValidate = new ManuallyValidate();
        scannerMock = Mockito.mock(Scanner.class);
        manuallyValidate.scanner = scannerMock;
    }

    @Test
    void testCheckingStringValidName() {
        when(scannerMock.nextLine()).thenReturn("John");
        String result = manuallyValidate.checkingString("name");
        assertEquals("John", result);
    }

    @Test
    void testCheckingStringValidModel() {
        when(scannerMock.nextLine()).thenReturn("iPhone-13");
        String result = manuallyValidate.checkingString("model");
        assertEquals("iPhone-13", result);
    }

    @Test
    void testCheckingStringRetriesUntilValid() {
        when(scannerMock.nextLine())
                .thenReturn("123")
                .thenReturn("")
                .thenReturn("ValidName");
        String result = manuallyValidate.checkingString("name");
        assertEquals("ValidName", result);
    }

    @Test
    void testCheckingIntMemoryValid() {
        when(scannerMock.nextLine()).thenReturn("512");
        int result = manuallyValidate.checkingInt("memory");
        assertEquals(512, result);
    }

    @Test
    void testCheckingIntAgeValid() {
        when(scannerMock.nextLine()).thenReturn("30");
        int result = manuallyValidate.checkingInt("age");
        assertEquals(30, result);
    }

    @Test
    void testCheckingIntRetriesUntilValid() {
        when(scannerMock.nextLine())
                .thenReturn("abc")
                .thenReturn("0")
                .thenReturn("1024");
        int result = manuallyValidate.checkingInt("memory");
        assertEquals(1024, result);
    }
}
