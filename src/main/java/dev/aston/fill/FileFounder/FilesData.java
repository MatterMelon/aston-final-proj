package dev.aston.fill.FileFounder;

public class FilesData {
    private final String name;
    private final String path;

    public FilesData(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Название файла: " + name +", путь: " + path;
    }
}
