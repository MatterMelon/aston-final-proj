package dev.aston.FileWrite;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aston.fill.InitCollection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

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
        Path tempDir = Files.createTempDirectory("jsonTest");

        // имитируем ввод: сначала путь до папки, потом имя файла
        String simulatedInput = tempDir.toString() + "\n" + "test.json\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        FileWrite fileWrite = new FileWrite();
        String filePath = fileWrite.createNewJsonFile();

        assertNotNull(filePath);
        File createdFile = new File(filePath);
        assertTrue(createdFile.exists());
        assertEquals("test.json", createdFile.getName());
    }


    @Test
    void testWriteObjectToFileCreatesValidJson() throws IOException {
        Path dir = tempDir.resolve("jsonDir");
        Files.createDirectories(dir);

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn(dir.toString())
                .thenReturn("object.json");

        fileWrite = new FileWrite(mockScanner);

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

        fileWrite = new FileWrite(mockScanner);

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
                .thenReturn("data/data.json");

        fileWrite = new FileWrite(mockScanner);

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
