package dev.aston.FileWrite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.aston.fill.InitCollection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Scanner;

public class FileWrite {
    private final Scanner scanner;

    public FileWrite() {
        this.scanner = new Scanner(System.in);
    }

    public FileWrite(Scanner scanner) {
        this.scanner = scanner;
    }

    public String createNewJsonFile() {
        System.out.println("Введите название дериктории в основном пакете: ");

        String path = scanner.nextLine();
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Создана новая директория!");
            } else {
                System.out.println("Директория существует!");
            }

            System.out.println("Введите название файла и расширение: ");
            String fileName = scanner.nextLine();

            File file = new File(directory, fileName);
            if (file.exists()) {
                System.out.println("Файл существует!");
            } else {
                file.createNewFile();
                System.out.println("Файл создан!");
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

    public void writeAllCollectionToFile(InitCollection initCollection) {

        File file = new File(createNewJsonFile());
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            JSONArray jsonArray;

            if (file.exists() && file.length() > 0) {
                jsonArray = mapper.readValue(file, JSONArray.class);
                System.out.println("Добавляем данные в существующий файл...");
            } else {
                jsonArray = new JSONArray();
                System.out.println("Создаем новый файл с данными...");
            }

            initCollection.getList().forEach(obj -> {
                JSONObject item = new JSONObject();
                item.put(obj.getClass().getSimpleName(), obj);
                jsonArray.add(item);
            });

            mapper.writeValue(file, jsonArray);
            System.out.println("Файл сохранён: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public void writeObjectToFile(Object obj){
        File file = new File(createNewJsonFile());
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try{
            JSONArray jsonArray;
            if (file.exists() && file.length() > 0) {
                jsonArray = mapper.readValue(file, JSONArray.class);
                System.out.println("Добавляем данные в существующий файл...");
            } else {
                jsonArray = new JSONArray();
                System.out.println("Создаем новый файл с данными...");
            }
            JSONObject item = new JSONObject();
            item.put(obj.getClass().getSimpleName(), obj);
            jsonArray.add(item);

            mapper.writeValue(file, jsonArray);
            System.out.println("Файл сохранён: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}