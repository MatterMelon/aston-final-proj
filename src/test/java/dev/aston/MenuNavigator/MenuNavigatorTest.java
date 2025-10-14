package dev.aston.MenuNavigator;

import dev.aston.FileWrite.FileWrite;
import dev.aston.entities.Car;
import dev.aston.entities.Person;
import dev.aston.entities.Phone;
import dev.aston.fill.ObjectAddService;
import dev.aston.fill.InitCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuNavigatorTest {

    private MenuNavigator menuNavigator;
    private InitCollection initCollection;
    private ObjectAddService objectAddService;
    private FileWrite fileWriteMock;

    @BeforeEach
    void setUp() {
        menuNavigator = new MenuNavigator();
        initCollection = menuNavigator.initCollection;
        objectAddService = menuNavigator.objectAddService;

        // Подменяем FileWrite на мок
        fileWriteMock = mock(FileWrite.class);
        menuNavigator.fileWrite = fileWriteMock;
    }

    @Test
    void testAddObjectClassPerson() {
        String input = "1\n";
        menuNavigator.setScanner(new java.util.Scanner(new ByteArrayInputStream(input.getBytes())));

        dev.aston.fill.CollectionFillObject collectionFillMock = mock(dev.aston.fill.CollectionFillObject.class);
        menuNavigator.addObjectClass(collectionFillMock);

        verify(collectionFillMock).fillWithObject("Person");
    }

    @Test
    void testAddObjectClassPhone() {
        String input = "2\n";
        menuNavigator.setScanner(new java.util.Scanner(new ByteArrayInputStream(input.getBytes())));
        dev.aston.fill.CollectionFillObject collectionFillMock = mock(dev.aston.fill.CollectionFillObject.class);

        menuNavigator.addObjectClass(collectionFillMock);

        verify(collectionFillMock).fillWithObject("Phone");
    }

    @Test
    void testAddObjectClassCar() {
        String input = "3\n";
        menuNavigator.setScanner(new java.util.Scanner(new ByteArrayInputStream(input.getBytes())));
        dev.aston.fill.CollectionFillObject collectionFillMock = mock(dev.aston.fill.CollectionFillObject.class);

        menuNavigator.addObjectClass(collectionFillMock);

        verify(collectionFillMock).fillWithObject("Car");
    }

    @Test
    void testShowAllObjectsPrintsCollection() {
        initCollection.getList().add(new Person.Builder().name("John").surname("Doe").age(30).build());

        assertFalse(initCollection.getList().isEmpty());

        menuNavigator.showAllObjects();
    }

    @Test
    void testWriteCollectionToFileCallsFileWrite() {
        initCollection.getList().add(new Person.Builder().name("John").surname("Doe").age(30).build());

        menuNavigator.writeCollectionToFile();

        verify(fileWriteMock).writeAllCollectionToFile(initCollection);
    }

    @Test
    void testExitPrintsMessage() {
        menuNavigator.exit();
    }

    @Test
    void testChooseObjectType() {
        String input = "2\n";
        menuNavigator.setScanner(new java.util.Scanner(new ByteArrayInputStream(input.getBytes())));
        String type = menuNavigator.chooseObjectType();

        assertEquals("Phone", type);
    }

    @Test
    void testChooseSortType() {
        String input = "1\n";
        menuNavigator.setScanner(new java.util.Scanner(new ByteArrayInputStream(input.getBytes())));
        String sortType = menuNavigator.chooseSortType();

        assertEquals("Quick", sortType);
    }

}
