package dev.aston.fill;

import dev.aston.fill.FileFounder.JsonParse;

public class FileFill implements CollectionFillObject,CollectionFillObjects {
    private JsonParse jsonParse;

    public FileFill(JsonParse jsonParse) {
        this.jsonParse = jsonParse;
    }

    @Override
    public void fillWithObject(String type) {
    }

    @Override
    public void fillWithAllObjects() {
        jsonParse.collectAllInfoJson();
    }
}
