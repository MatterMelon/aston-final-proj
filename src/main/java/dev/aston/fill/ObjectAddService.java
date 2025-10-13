package dev.aston.fill;

import dev.aston.entities.Car;
import dev.aston.entities.Person;
import dev.aston.entities.Phone;

public class ObjectAddService {
    private InitCollection initCollection;

    public ObjectAddService(InitCollection initCollection) {
        this.initCollection = initCollection;
    }

    public Phone addPhone(String brand, String model, int memory, int displaySize) {
        Phone phone = new Phone.Builder()
                .brand(brand)
                .model(model)
                .memory(memory)
                .displayMemory(displaySize)
                .build();
        initCollection.getList().add(phone);
        return phone;
    }

    public Car addCar(String brand, String model, int year) {
        Car car = new Car.Builder()
                .brand(brand)
                .model(model)
                .year(year)
                .build();
        initCollection.getList().add(car);
        return car;
    }

    public Person addPerson(String name, String surname, int age) {
        Person person = new Person.Builder()
                .name(name)
                .surname(surname)
                .age(age)
                .build();
        initCollection.getList().add(person);
        return person;
    }
}
