package dev.aston.fill;

import java.util.Scanner;

public class ManuallyFill implements CollectionFill {
    Scanner scanner = new Scanner(System.in);
    ObjectAddService objectAddService;

    public ManuallyFill(ObjectAddService objectAddService) {
        this.objectAddService = objectAddService;
    }

    @Override
    public void fillWithPhones() {
        System.out.println("Add brand: ");
        String brand = scanner.nextLine();
        System.out.println("Add model: ");
        String model = scanner.nextLine();
        System.out.println("Add memory: ");
        int memory = scanner.nextInt();
        System.out.println("Add displaySize: ");
        int displaySize = scanner.nextInt();

        objectAddService.addPhone(brand,model,memory,displaySize);
    }

    @Override
    public void fillWithCars() {
        System.out.println("Add brand: ");
        String brand = scanner.nextLine();
        System.out.println("Add model: ");
        String model = scanner.nextLine();
        System.out.println("Add year: ");
        int year = scanner.nextInt();

        objectAddService.addCar(brand,model,year);
    }

    @Override
    public void fillWithPersons() {
        System.out.println("Add name: ");
        String name = scanner.nextLine();
        System.out.println("Add surname: ");
        String surname = scanner.nextLine();
        System.out.println("Add age: ");
        int age = scanner.nextInt();

        objectAddService.addPerson(name,surname,age);
    }

    @Override
    public void fillWithAllObjects() {

    }
}
