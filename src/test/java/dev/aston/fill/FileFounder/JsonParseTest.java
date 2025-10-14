package dev.aston.fill.FileFounder;

import dev.aston.fill.ObjectAddService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

class JsonParseTest {

    ObjectAddService mockService;
    JsonParse jsonParse;

    @TempDir
    Path tempDir;

    Path jsonFile;

    @BeforeEach
    void setUp() throws IOException {
        mockService = Mockito.mock(ObjectAddService.class);
        jsonParse = new JsonParse(mockService);

        jsonFile = tempDir.resolve("test.json");
        Files.createFile(jsonFile);
    }

    @Test
    void testParseValidPersonJson() throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject personObj = new JSONObject();
        JSONObject person = new JSONObject();
        person.put("name", "John");
        person.put("surname", "Doe");
        person.put("age", 30L);
        personObj.put("Person", person);
        jsonArray.add(personObj);

        Files.writeString(jsonFile, jsonArray.toJSONString());

        jsonParse.parseFile(jsonFile.toString());

        verify(mockService, times(1)).addPerson("John", "Doe", 30);
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void testParseInvalidPersonJson() throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject personObj = new JSONObject();
        JSONObject person = new JSONObject();
        person.put("name", "");
        person.put("surname", "Doe");
        person.put("age", 30L);
        personObj.put("Person", person);
        jsonArray.add(personObj);

        Files.writeString(jsonFile, jsonArray.toJSONString());

        jsonParse.parseFile(jsonFile.toString());

        verify(mockService, never()).addPerson(anyString(), anyString(), anyInt());
    }

    @Test
    void testParsePhoneJson() throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject phoneObj = new JSONObject();
        JSONObject phone = new JSONObject();
        phone.put("brand", "Samsung");
        phone.put("model", "Galaxy");
        phone.put("memory", 128L);
        phone.put("displaySize", 6L);
        phoneObj.put("Phone", phone);
        jsonArray.add(phoneObj);

        Files.writeString(jsonFile, jsonArray.toJSONString());

        jsonParse.parseFile(jsonFile.toString());

        verify(mockService, times(1)).addPhone("Samsung", "Galaxy", 128, 6);
    }

    @Test
    void testParseCarJson() throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject carObj = new JSONObject();
        JSONObject car = new JSONObject();
        car.put("brand", "Toyota");
        car.put("model", "Camry");
        car.put("year", 2020L);
        carObj.put("Car", car);
        jsonArray.add(carObj);

        Files.writeString(jsonFile, jsonArray.toJSONString());

        jsonParse.parseFile(jsonFile.toString());

        verify(mockService, times(1)).addCar("Toyota", "Camry", 2020);
    }

    @Test
    void testParseMultipleObjects() throws IOException {
        JSONArray jsonArray = new JSONArray();

        JSONObject personObj = new JSONObject();
        JSONObject person = new JSONObject();
        person.put("name", "Alice");
        person.put("surname", "Smith");
        person.put("age", 25L);
        personObj.put("Person", person);

        JSONObject carObj = new JSONObject();
        JSONObject car = new JSONObject();
        car.put("brand", "Ford");
        car.put("model", "Focus");
        car.put("year", 2018L);
        carObj.put("Car", car);

        jsonArray.add(personObj);
        jsonArray.add(carObj);

        Files.writeString(jsonFile, jsonArray.toJSONString());

        jsonParse.parseFile(jsonFile.toString());

        verify(mockService, times(1)).addPerson("Alice", "Smith", 25);
        verify(mockService, times(1)).addCar("Ford", "Focus", 2018);
        verifyNoMoreInteractions(mockService);
    }
}
