package dev.aston.fill.FileFounder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

public class FileFounder {
    private final String path1 = "src/main/resources/data";
    public List<FilesData> foundFilesList = new ArrayList<>();

    public List<FilesData> parseFiles() throws IOException {

        try (Stream<Path> files = Files.walk(Paths.get(path1), FOLLOW_LINKS)) {
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
}

