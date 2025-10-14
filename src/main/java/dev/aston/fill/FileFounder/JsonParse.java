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

                if (obj.containsKey("Person")) {
                    JSONObject person = (JSONObject) obj.get("Person");

                    String name = (String) person.get("name");
                    String surname = (String) person.get("surname");
                    int age = ((Long) person.get("age")).intValue();
                    if(fileValidation.checkingString(name,FieldNames.NAME)&&
                            fileValidation.checkingString(surname,FieldNames.SURNAME)&&
                            fileValidation.checkingInt(age,FieldNames.AGE)) {
                        objectAddService.addPerson(name, surname, age);
                    }else  System.out.println("Объект с данными: "+name+" "+surname+" "+age+" не может быть создан");

                } else if (obj.containsKey("Phone")) {
                    JSONObject phone = (JSONObject) obj.get("Phone");

                    String brand = (String) phone.get("brand");
                    String model = (String) phone.get("model");
                    int memory = ((Long) phone.get("memory")).intValue();
                    int displaySize = ((Long) phone.get("displaySize")).intValue();
                    if(fileValidation.checkingString(brand,FieldNames.BRAND)&&
                            fileValidation.checkingString(model,FieldNames.MODEL)&&
                            fileValidation.checkingInt(memory,FieldNames.MEMORY)&&
                            fileValidation.checkingInt(displaySize,FieldNames.DISPLAYSIZE)) {
                        objectAddService.addPhone(brand, model, memory, displaySize);
                    }else  System.out.println("Объект с данными: "+brand+" "+model+" "+memory+" "+displaySize+" не может быть создан");

                } else if (obj.containsKey("Car")) {
                    JSONObject car = (JSONObject) obj.get("Car");

                    String brand = (String) car.get("brand");
                    String model = (String) car.get("model");
                    int year = ((Long) car.get("year")).intValue();
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





