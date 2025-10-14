package dev.aston.fill;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InitCollectionTest {

    private InitCollection initCollection;

    @BeforeEach
    void setUp() {
        initCollection = new InitCollection();
    }

    @Test
    void testGetListInitiallyEmpty() {
        List<Object> list = initCollection.getList();
        assertEquals(0, list.size(), "Список должен быть изначально пустым");
    }

    @Test
    void testGetListAfterAddingElements() {
        initCollection.getList().add("Hello");
        initCollection.getList().add(123);

        List<Object> list = initCollection.getList();
        assertEquals(2, list.size());
        assertEquals("Hello", list.get(0));
        assertEquals(123, list.get(1));
    }

    @Test
    void testPrintOutputsElements() {
        initCollection.getList().add("Hello");
        initCollection.getList().add(123);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        initCollection.print(initCollection.getList());

        System.setOut(originalOut);

        String expectedOutput = "Hello\n123\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
