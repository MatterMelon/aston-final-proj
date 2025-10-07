package dev.aston;

import dev.aston.fill.*;
import dev.aston.fill.FileFounder.JsonParse;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InitCollection initCollection = new InitCollection();
        ObjectAddService objectAddService = new ObjectAddService(initCollection);

//        ManuallyFill manuallyFill= new ManuallyFill(objectAddService);
//        RandomFill randomFill = new RandomFill(objectAddService);
        JsonParse jsonParse = new JsonParse(objectAddService);
        FileFill fileFill = new FileFill(jsonParse);

//        manuallyFill.fillWithPhones();
//        randomFill.fillWithPhones();
//        randomFill.fillWithCars();
//        randomFill.fillWithPersons();

        fileFill.fillWithAllObjects();

        initCollection.print(initCollection.getList());
    }
}