package dev.aston.FileWrite;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aston.fill.InitCollection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileWriteTest {

    private FileWrite fileWrite;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileWrite = new FileWrite();
    }

    @Test
    void testCreateNewJsonFileCreatesDirectoryAndFile() throws IOException {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn(tempDir.resolve("testDir").toString())
                .thenReturn("data.json");

        fileWrite.scanner = mockScanner;

        String filePath = fileWrite.createNewJsonFile();

        File createdFile = new File(filePath);

        assertTrue(createdFile.exists());
        assertTrue(createdFile.getParentFile().exists());
        assertEquals("data.json", createdFile.getName());
    }

    @Test
    void testWriteObjectToFileCreatesValidJson() throws IOException {
        Path dir = tempDir.resolve("jsonDir");
        Files.createDirectories(dir);

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn(dir.toString())
                .thenReturn("object.json");

        fileWrite.scanner = mockScanner;

        TestPerson person = new TestPerson("John", 25);

        fileWrite.writeObjectToFile(person);

        File file = dir.resolve("object.json").toFile();
        assertTrue(file.exists());

        ObjectMapper mapper = new ObjectMapper();
        List<?> array = mapper.readValue(file, List.class);

        assertEquals(1, array.size());
        Map<?, ?> obj = (Map<?, ?>) array.get(0);
        assertTrue(obj.containsKey("TestPerson"));
    }


    @Test
    void testWriteAllCollectionToFile() throws IOException {
        Path dir = tempDir.resolve("collDir");
        Files.createDirectories(dir);

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn(dir.toString())
                .thenReturn("collection.json");

        fileWrite.scanner = mockScanner;

        InitCollection mockCollection = mock(InitCollection.class);
        List<Object> mockObjects = List.of(new TestPerson("Alice", 30), new TestPerson("Bob", 40));
        when(mockCollection.getList()).thenReturn(mockObjects);

        fileWrite.writeAllCollectionToFile(mockCollection);

        File file = dir.resolve("collection.json").toFile();
        assertTrue(file.exists());

        ObjectMapper mapper = new ObjectMapper();
        List<?> array = mapper.readValue(file, List.class);

        assertEquals(2, array.size());

        Map<?, ?> obj1 = (Map<?, ?>) array.get(0);
        Map<?, ?> obj2 = (Map<?, ?>) array.get(1);

        assertTrue(obj1.containsKey("TestPerson"));
        assertTrue(obj2.containsKey("TestPerson"));
    }

    @Test
    void testCreateNewJsonFileHandlesException() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn("\0invalid")
                .thenReturn("data.json");

        fileWrite.scanner = mockScanner;

        String path = fileWrite.createNewJsonFile();

        assertNull(path);
    }

    static class TestPerson {
        private String name;
        private int age;

        public TestPerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
    }
}
