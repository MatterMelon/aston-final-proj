package dev.aston.fill.Validate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileValidationTest {

    private FileValidation fileValidation;

    @BeforeEach
    void setUp() {
        fileValidation = new FileValidation();
    }

    @Test
    void testCheckingStringValidName() {
        assertTrue(fileValidation.checkingString("John", "name"));
        assertTrue(fileValidation.checkingString("Иван", "name"));
        assertTrue(fileValidation.checkingString("John Doe", "surname"));
        assertTrue(fileValidation.checkingString("Toyota", "brand"));
    }

    @Test
    void testCheckingStringInvalidName() {
        assertFalse(fileValidation.checkingString("John123", "name"));
        assertFalse(fileValidation.checkingString("", "surname"));
        assertFalse(fileValidation.checkingString(null, "brand"));
        assertFalse(fileValidation.checkingString("Ford!", "brand"));
    }

    @Test
    void testCheckingStringValidModel() {
        assertTrue(fileValidation.checkingString("Galaxy S21", "model"));
        assertTrue(fileValidation.checkingString("iPhone-13", "model"));
        assertTrue(fileValidation.checkingString("Model X", "model"));
    }

    @Test
    void testCheckingStringInvalidModel() {
        assertFalse(fileValidation.checkingString("", "model"));
        assertFalse(fileValidation.checkingString(null, "model"));
        assertFalse(fileValidation.checkingString("Model@", "model"));
    }

    @Test
    void testCheckingStringUnknownVariable() {
        assertFalse(fileValidation.checkingString("Something", "unknown"));
    }

    @Test
    void testCheckingIntMemory() {
        assertTrue(fileValidation.checkingInt(512, "memory"));
        assertTrue(fileValidation.checkingInt(1, "memory"));
        assertTrue(fileValidation.checkingInt(1024, "memory"));

        assertFalse(fileValidation.checkingInt(0, "memory"));
        assertFalse(fileValidation.checkingInt(2048, "memory"));
        assertFalse(fileValidation.checkingInt(-5, "memory"));
    }

    @Test
    void testCheckingIntYear() {
        assertTrue(fileValidation.checkingInt(1885, "year"));
        assertTrue(fileValidation.checkingInt(2020, "year"));
        assertTrue(fileValidation.checkingInt(2025, "year"));

        assertFalse(fileValidation.checkingInt(1800, "year"));
        assertFalse(fileValidation.checkingInt(3000, "year"));
    }

    @Test
    void testCheckingIntDisplaySize() {
        assertTrue(fileValidation.checkingInt(4, "displaySize"));
        assertTrue(fileValidation.checkingInt(6, "displaySize"));
        assertTrue(fileValidation.checkingInt(8, "displaySize"));

        assertFalse(fileValidation.checkingInt(3, "displaySize"));
        assertFalse(fileValidation.checkingInt(10, "displaySize"));
    }

    @Test
    void testCheckingIntAge() {
        assertTrue(fileValidation.checkingInt(1, "age"));
        assertTrue(fileValidation.checkingInt(50, "age"));
        assertTrue(fileValidation.checkingInt(122, "age"));

        assertFalse(fileValidation.checkingInt(0, "age"));
        assertFalse(fileValidation.checkingInt(150, "age"));
        assertFalse(fileValidation.checkingInt(-10, "age"));
    }

    @Test
    void testCheckingIntUnknownVariable() {
        assertFalse(fileValidation.checkingInt(100, "unknown"));
    }
}
