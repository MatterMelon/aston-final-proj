package dev.aston.fill;

import dev.aston.entities.Car;
import dev.aston.entities.Person;
import dev.aston.entities.Phone;

public class ObjectAddService {
    private InitCollection initCollection;

    public ObjectAddService(InitCollection initCollection) {
        this.initCollection = initCollection;
    }

    public void addPhone(String brand, String model, int memory, int displaySize) {
        Phone phone = new Phone.Builder()
                .brand(brand)
                .model(model)
                .memory(memory)
                .displayMemory(displaySize)
                .build();
        initCollection.getList().add(phone);
    }

    public void addCar(String brand, String model, int year) {
        Car car = new Car.Builder()
                .brand(brand)
                .model(model)
                .year(year)
                .build();
        initCollection.getList().add(car);
    }

    public void addPerson(String name, String surname, int age) {
        Person person = new Person.Builder()
                .name(name)
                .surname(surname)
                .age(age)
                .build();
        initCollection.getList().add(person);
    }
}
