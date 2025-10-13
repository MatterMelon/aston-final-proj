package dev.aston.fill;

import dev.aston.FileWrite.FileWrite;
import dev.aston.fill.Validate.FieldNames;
import dev.aston.fill.Validate.ManuallyValidate;

public class ManuallyFill implements CollectionFillObject {
    ObjectAddService objectAddService;
    ManuallyValidate validate = new ManuallyValidate();
    FileWrite fileWrite = new FileWrite();

    public ManuallyFill(ObjectAddService objectAddService) {
        this.objectAddService = objectAddService;
    }

    @Override
    public void fillWithObject(String type) {
        String name, surname, brand, model;
        int age, memory, displaySize, year;
        switch (type) {
            case "Person":
                System.out.println("Add name: ");
                name = validate.checkingString(FieldNames.NAME);
                System.out.println("Add surname: ");
                surname = validate.checkingString(FieldNames.SURNAME);
                System.out.println("Add age: ");
                age = validate.checkingInt(FieldNames.AGE);
                fileWrite.writeObjectToFile(objectAddService.addPerson(name, surname, age));
                break;
            case "Phone":
                System.out.println("Add brand: ");
                brand = validate.checkingString(FieldNames.BRAND);
                System.out.println("Add model: ");
                model = validate.checkingString(FieldNames.MODEL);
                System.out.println("Add memory: ");
                memory = validate.checkingInt(FieldNames.MEMORY);
                System.out.println("Add displaySize: ");
                displaySize = validate.checkingInt(FieldNames.DISPLAYSIZE);
                fileWrite.writeObjectToFile(objectAddService.addPhone(brand, model, memory, displaySize));
                break;
            case "Car":
                System.out.println("Add brand: ");
                brand = validate.checkingString(FieldNames.BRAND);
                System.out.println("Add model: ");
                model = validate.checkingString(FieldNames.MODEL);
                System.out.println("Add year: ");
                year = validate.checkingInt(FieldNames.YEAR);
                fileWrite.writeObjectToFile(objectAddService.addCar(brand, model, year));
                break;
        }
    }
}
