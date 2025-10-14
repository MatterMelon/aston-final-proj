package dev.aston.fill;

import dev.aston.entities.Car;
import dev.aston.entities.Person;
import dev.aston.entities.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectAddServiceTest {

    private InitCollection initCollection;
    private ObjectAddService objectAddService;

    @BeforeEach
    void setUp() {
        initCollection = new InitCollection();
        objectAddService = new ObjectAddService(initCollection);
    }

    @Test
    void testAddPhone() {
        Phone phone = objectAddService.addPhone("Samsung", "Galaxy S23", 256, 6);

        assertEquals("Samsung", phone.getBrand());
        assertEquals("Galaxy S23", phone.getModel());
        assertEquals(256, phone.getMemory());
        assertEquals(6, phone.getDisplaySize());

        List<Object> list = initCollection.getList();
        assertTrue(list.contains(phone));
    }

    @Test
    void testAddCar() {
        Car car = objectAddService.addCar("Toyota", "Camry", 2020);

        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getModel());
        assertEquals(2020, car.getYear());

        List<Object> list = initCollection.getList();
        assertTrue(list.contains(car));
    }

    @Test
    void testAddPerson() {
        Person person = objectAddService.addPerson("John", "Doe", 30);

        assertEquals("John", person.getName());
        assertEquals("Doe", person.getSurname());
        assertEquals(30, person.getAge());

        List<Object> list = initCollection.getList();
        assertTrue(list.contains(person));
    }
}
