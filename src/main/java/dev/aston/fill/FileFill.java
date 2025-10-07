package dev.aston.fill;

import dev.aston.fill.FileFounder.JsonParse;

public class FileFill implements CollectionFill{
    private JsonParse jsonParse;

    public FileFill(JsonParse jsonParse) {
        this.jsonParse = jsonParse;
    }

    @Override
    public void fillWithPhones() {
    }
    @Override
    public void fillWithCars() {
    }
    @Override
    public void fillWithPersons() {
    }

    @Override
    public void fillWithAllObjects() {
        jsonParse.collectAllInfoJson();
    }
}
