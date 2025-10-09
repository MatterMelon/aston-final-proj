package dev.aston.fill;

public class RandomFill implements CollectionFillObject {

    ObjectAddService objectAddService;

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
    public void fillWithPhones() {
        String brand = somePhoneBrands[(int) (Math.random() * 5)];
        String model = somePhoneModels[(int) (Math.random() * 5)];
        int memory = someMemorySizes[(int) (Math.random() * 5)];
        int displaySize = someDisplaySizes[(int) (Math.random() * 5)];

        objectAddService.addPhone(brand,model,memory,displaySize);
    }

    @Override
    public void fillWithCars() {
        String brand = someCarsBrands[(int) (Math.random() * 5)];
        String model = someCarsModels[(int) (Math.random() * 5)];
        int year = someYearsOfRelease[(int) (Math.random() * 5)];

        objectAddService.addCar(brand,model,year);
    }

    @Override
    public void fillWithPersons() {
        String name = someNames[(int) (Math.random() * 5)];
        String surname = someSurnames[(int) (Math.random() * 5)];
        int age = someAges[(int) (Math.random() * 5)];

        objectAddService.addPerson(name,surname,age);
    }
}
