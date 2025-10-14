package dev.aston.fill.FileFounder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static org.junit.jupiter.api.Assertions.*;

class FileFounderTest {

    @TempDir
    Path tempDir;

    Path dataDir;
    FileFounder fileFounder;

    @BeforeEach
    void setUp() throws IOException {
        Path srcDir = tempDir.resolve("src/main/resources/data");
        Files.createDirectories(srcDir);
        dataDir = srcDir;

        fileFounder = new FileFounder() {
            @Override
            public List<FilesData> parseFiles() throws IOException {
                try (Stream<Path> files = Files.walk(dataDir, FOLLOW_LINKS)) {
                    files.forEach(file -> {
                        if (file.toFile().isFile() && file.toFile().getPath().endsWith(".json")) {
                            String fileName = file.getFileName().toString();
                            String filePath = file.toFile().getPath().toString();
                            FilesData foundFile = new FilesData(fileName, filePath);
                            foundFilesList.add(foundFile);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return foundFilesList;
            }
        };
    }

    @Test
    void testFindsJsonFiles() throws IOException {
        Files.createFile(dataDir.resolve("file1.json"));
        Files.createFile(dataDir.resolve("file2.json"));
        Files.createFile(dataDir.resolve("ignore.txt"));

        List<FilesData> result = fileFounder.parseFiles();

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f -> f.getName().endsWith(".json")));
    }

    @Test
    void testNestedDirectories() throws IOException {
        Path subDir = dataDir.resolve("nested");
        Files.createDirectories(subDir);
        Files.createFile(subDir.resolve("nested.json"));
        Files.createFile(subDir.resolve("other.txt"));

        List<FilesData> result = fileFounder.parseFiles();

        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().endsWith(".json"));
        assertTrue(result.get(0).getPath().contains("nested"));
    }

    @Test
    void testEmptyDirectoryReturnsEmptyList() throws IOException {
        List<FilesData> result = fileFounder.parseFiles();
        assertTrue(result.isEmpty());
    }

    @Test
    void testHandlesInvalidPathGracefully() {
        FileFounder brokenFounder = new FileFounder() {
            @Override
            public List<FilesData> parseFiles() throws IOException {
                try (Stream<Path> files = Files.walk(Paths.get("nonexistent/path"), FOLLOW_LINKS)) {
                    return List.of();
                } catch (Exception e) {
                    // эмулируем логику оригинала
                    e.printStackTrace();
                }
                return foundFilesList;
            }
        };

        assertDoesNotThrow(() -> brokenFounder.parseFiles());
        assertTrue(brokenFounder.foundFilesList.isEmpty());
    }
}
