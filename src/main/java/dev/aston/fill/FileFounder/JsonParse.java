package dev.aston.fill.FileFounder;


import dev.aston.fill.ObjectAddService;
import dev.aston.fill.Validate.FieldNames;
import dev.aston.fill.Validate.FileValidation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonParse {

    FileFounder fileFounder = new FileFounder();
    ObjectAddService objectAddService;
    FileValidation fileValidation= new FileValidation();

    public JsonParse(ObjectAddService objectAddService) {
        this.objectAddService = objectAddService;
    }

    public void collectAllInfoJson() {
        try {
            //fileFounder.parseFiles();
            for (FilesData foundFile : fileFounder.parseFiles()) {
                if (foundFile.getName().endsWith(".json")) {
                    parseFile(foundFile.getPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJSONFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> fileLines = Files.readAllLines(Paths.get(path));
            fileLines.forEach(fileLine -> sb.append(fileLine + "\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void parseFile(String path) {

        try {
            JSONParser jParse = new JSONParser();
            JSONArray jsonArray = (JSONArray) jParse.parse(getJSONFile(path));
            jsonArray.forEach(jsonObject -> {
                JSONObject obj = (JSONObject) jsonObject;

                if (obj.containsKey("name") && obj.containsKey("surname") && obj.containsKey("age")) {
                    String name = (String) obj.get("name");
                    String surname = (String) obj.get("surname");
                    int age = ((Long) obj.get("age")).intValue();
                    if(fileValidation.checkingString(name,FieldNames.NAME)&&
                            fileValidation.checkingString(surname,FieldNames.SURNAME)&&
                            fileValidation.checkingInt(age,FieldNames.AGE)) {
                        objectAddService.addPerson(name, surname, age);
                    }else  System.out.println("Объект с данными: "+name+" "+surname+" "+age+" не может быть создан");

                } else if (obj.containsKey("brand") && obj.containsKey("model") && obj.containsKey("memory") && obj.containsKey("displaySize")) {
                    String brand = (String) obj.get("brand");
                    String model = (String) obj.get("model");
                    int memory = ((Long) obj.get("memory")).intValue();
                    int displaySize = ((Long) obj.get("displaySize")).intValue();
                    if(fileValidation.checkingString(brand,FieldNames.BRAND)&&
                            fileValidation.checkingString(model,FieldNames.MODEL)&&
                            fileValidation.checkingInt(memory,FieldNames.MEMORY)&&
                            fileValidation.checkingInt(displaySize,FieldNames.DISPLAYSIZE)) {
                        objectAddService.addPhone(brand, model, memory, displaySize);
                    }else  System.out.println("Объект с данными: "+brand+" "+model+" "+memory+" "+displaySize+" не может быть создан");

                } else if (obj.containsKey("brand") && obj.containsKey("model") && obj.containsKey("year")) {
                    String brand = (String) obj.get("brand");
                    String model = (String) obj.get("model");
                    int year = ((Long) obj.get("year")).intValue();
                    if(fileValidation.checkingString(brand,FieldNames.BRAND)&&
                            fileValidation.checkingString(model,FieldNames.MODEL)&&
                            fileValidation.checkingInt(year,FieldNames.YEAR)) {
                        objectAddService.addCar(brand, model, year);
                    }else  System.out.println("Объект с данными: "+brand+" "+model+" "+" "+year+" не может быть создан");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





