package dev.aston.fill;

import dev.aston.FileWrite.FileWrite;

public class RandomFill implements CollectionFillObject {

    ObjectAddService objectAddService;
    FileWrite fileWrite = new FileWrite();

    public RandomFill(ObjectAddService objectAddService) {
        this.objectAddService = objectAddService;
    }

    private final String[] somePhoneBrands = {"Samsung", "Apple", "Xiaomi", "Huawei", "Google"};
    private final String[] somePhoneModels = {"iPhone 15 Pro", "Samsung Galaxy S24", "Google Pixel 8", "Xiaomi 14", "OnePlus 12"
    };
    private final int[] someMemorySizes = {64, 128, 256, 512, 1024};
    private final int[] someDisplaySizes = {6, 7, 8, 9, 10};

    private final String[] someCarsBrands = {"Toyota", "BMW", "Mercedes", "Audi", "Ford"};
    private final String[] someCarsModels = {"Camry", "Corolla", "RAV4", "Highlander", "Prius"};
    private final int[] someYearsOfRelease = {2018, 2019, 2020, 2021, 2022};

    private final String[] someNames= {"John", "Emma", "Michael", "Sophia", "William"};
    private final String[] someSurnames= {"Smith", "Johnson", "Williams", "Brown", "Jones"};
    private final int[] someAges= {18, 25, 32, 45, 60};

    @Override
    public void fillWithObject(String type) {
        String name,surname,brand,model;
        int age,memory,displaySize,year;
        switch (type){
            case "Person":
                name = someNames[(int) (Math.random() * 5)];
                surname = someSurnames[(int) (Math.random() * 5)];
                age = someAges[(int) (Math.random() * 5)];

                fileWrite.writeObjectToFile(objectAddService.addPerson(name, surname, age));
                break;
            case "Phone":
                brand = somePhoneBrands[(int) (Math.random() * 5)];
                model = somePhoneModels[(int) (Math.random() * 5)];
                memory = someMemorySizes[(int) (Math.random() * 5)];
                displaySize = someDisplaySizes[(int) (Math.random() * 5)];

                fileWrite.writeObjectToFile(objectAddService.addPhone(brand, model, memory, displaySize));
                break;
            case "Car":
                brand = someCarsBrands[(int) (Math.random() * 5)];
                model = someCarsModels[(int) (Math.random() * 5)];
                year = someYearsOfRelease[(int) (Math.random() * 5)];

                fileWrite.writeObjectToFile(objectAddService.addCar(brand, model, year));
                break;
        }
    }
}
