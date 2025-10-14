package dev.aston.MenuNavigator;

import dev.aston.FileWrite.FileWrite;
import dev.aston.entities.Car;
import dev.aston.entities.Person;
import dev.aston.entities.Phone;
import dev.aston.fill.*;
import dev.aston.fill.FileFounder.JsonParse;
import dev.aston.fill.Validate.FieldNames;
import dev.aston.fill.Validate.ManuallyValidate;
import dev.aston.search.BinarySearcher;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuNavigator {
    InitCollection initCollection = new InitCollection();
    ObjectAddService objectAddService = new ObjectAddService(initCollection);
    FileWrite fileWrite = new FileWrite();
    private String type;

    private final Scanner scanner = new Scanner(System.in);

    public void runOurProject() {
        String message = "Выбрана неверная опция! Попробуйте заново.";
        greatings();
        menu();

        loop:
        while (true) {
            if (scanner.hasNextInt()) {
                int menuInput = scanner.nextInt();
                switch (menuInput) {
                    case 1:
                        addObjectMethod();
                        menu();
                        break;
                    case 2:
                        objectSearch();
                        menu();
                        break;
                    case 3:
                        collectionSort();
                        menu();
                        break;
                    case 4:
                        showAllObjects();
                        menu();
                        break;
                    case 5:
                        writeCollectionToFile();
                        menu();
                        break;
                    case 0:
                        exit();
                        break loop;
                    default:
                        System.out.println(message);
                        break;
                }
            }
        }
    }

    public void greatings() {
        String message = "\nПроект команды №5.\n";
        System.out.println(message);
    }

    public void menu() {
        String[] menu = {
                "1. Создать новый объект\n",
                "2. Поиск объекта\n",
                "3. Сортировать коллекцию\n",
                "4. Просмотр коллекции\n",
                "5. Запись коллекции в файл\n",
                "0. Завершение работы\n"};
        System.out.println("\nМеню:");
        for (int i = 0; i < menu.length; i++) {
            System.out.print(menu[i]);
        }
        System.out.println("\nВведите нужную опцию и нажмите Enter [1..4]: ");
    }

    private Person createPerson(ManuallyValidate validate) {
        System.out.println("Выберете имя: ");
        String name = validate.checkingString(FieldNames.NAME);

        System.out.println("Выберете фамилию: ");
        String surname = validate.checkingString(FieldNames.SURNAME);

        System.out.println("Выберете возраст: ");
        int age = validate.checkingInt(FieldNames.AGE);

        return new Person.Builder().name(name).surname(surname).age(age).build();
    }

    private Phone createPhone(ManuallyValidate validate) {
        System.out.println("Выберете бренд: ");
        String brand = validate.checkingString(FieldNames.BRAND);

        System.out.println("Выберете модель: ");
        String model = validate.checkingString(FieldNames.MODEL);

        System.out.println("Выберете объем памяти: ");
        int memory = validate.checkingInt(FieldNames.MEMORY);

        System.out.println("Выберете размер дисплея: ");
        int displaySize = validate.checkingInt(FieldNames.DISPLAYSIZE);

        return new Phone.Builder().brand(brand).model(model).memory(memory).displayMemory(displaySize).build();
    }

    private Car createCar(ManuallyValidate validate) {
        System.out.println("Выберете бренд: ");
        String brand = validate.checkingString(FieldNames.BRAND);

        System.out.println("Выберете модель: ");
        String model = validate.checkingString(FieldNames.MODEL);

        System.out.println("Выберете год выпуска: ");
        int year = validate.checkingInt(FieldNames.YEAR);

        return new Car.Builder().brand(brand).model(model).year(year).build();
    }

    private int searchPerson(ManuallyValidate validate) {
        List<Person> list = new ArrayList<>();

        // Собираем коллекцию нужного типа
        initCollection.getList().forEach(obj -> {
            try {
                list.add((Person) obj);
            } catch (Exception e) {}
        });

        if (list.isEmpty()) {
            System.out.println("Объекты выбранного типа отсутствуют.");
            return -1;
        }

        Collections.sort(list);
        System.out.println("Коллекция отсортирована:");
        list.forEach(System.out::println);

        Person target = createPerson(validate);
        BinarySearcher<Person> searcher = new BinarySearcher<>();
        return searcher.search(list, target);
    }

    private int searchPhone(ManuallyValidate validate) {
        List<Phone> list = new ArrayList<>();

        // Собираем коллекцию нужного типа
        initCollection.getList().forEach(obj -> {
            try {
                list.add((Phone) obj);
            } catch (Exception e) {}
        });

        if (list.isEmpty()) {
            System.out.println("Объекты выбранного типа отсутствуют.");
            return -1;
        }

        Collections.sort(list);
        System.out.println("Коллекция отсортирована:");
        list.forEach(System.out::println);

        Phone target = createPhone(validate);
        BinarySearcher<Phone> searcher = new BinarySearcher<>();
        return searcher.search(list, target);
    }

    private int searchCar(ManuallyValidate validate) {
        List<Car> list = new ArrayList<>();

        // Собираем коллекцию нужного типа
        initCollection.getList().forEach(obj -> {
            try {
                list.add((Car) obj);
            } catch (Exception e) {}
        });

        if (list.isEmpty()) {
            System.out.println("Объекты выбранного типа отсутствуют.");
            return -1;
        }

        Collections.sort(list);
        System.out.println("Коллекция отсортирована:");
        list.forEach(System.out::println);

        Car target = createCar(validate);
        BinarySearcher<Car> searcher = new BinarySearcher<>();
        return searcher.search(list, target);
    }

    public void objectSearch() {
        ManuallyValidate validate = new ManuallyValidate();

        System.out.println("Коллекция будет автоматически отсортирована!");

        System.out.println(
                """
                        Выберите тип искомого объекта:
                        1.Person
                        2.Phone
                        3.Car""");

        int choice = scanner.nextInt();
        if (choice == 0) return;

        String type = "";

        while (type.isEmpty()) {
            switch (choice) {
                case 1:
                    type = "Person";
                    break;
                case 2:
                    type = "Phone";
                    break;
                case 3:
                    type = "Car";
                    break;
                default:
                    System.out.println("Неверный выбор");

            }
        }

        int foundIndex = -1;

        switch (type) {
            case "Person":
                foundIndex = searchPerson(validate);
                break;
            case "Phone":
                foundIndex = searchPhone(validate);
                break;
            case "Car":
                foundIndex = searchCar(validate);
                break;
        }

        if (foundIndex == -1) {
            System.out.println("Элемент не найден!");
            return;
        }

        System.out.println("Индекс элемента: " + foundIndex);

    }

    public void collectionSort() {
        /*
        Не уверен что он нужен,потому что бианрный поиск лучше работает в отсортированной коллекции,
        поэтому либо сортируем коллекцию сразу после добавления обьекта автоматически,либо принудительно
        вызываем этот метод перед поиском
         */
        System.out.println(3);
    }

    public void showAllObjects() {
        initCollection.print(initCollection.getList());
    }

    public void addObjectMethod() {
        System.out.println(
                "Выберите способ добавления объекта:\n" +
                        "1.Вручную\n" +
                        "2.Рандом\n" +
                        "3.Из файла");
        int choice = scanner.nextInt();
        if (choice == 0) return;
        switch (choice) {
            case 1:
                ManuallyFill manuallyFill = new ManuallyFill(objectAddService);
                addObjectClass(manuallyFill);
                return;
            case 2:
                RandomFill randomFill = new RandomFill(objectAddService);
                addObjectClass(randomFill);
                return;
            case 3:
                choosingFileType();
                return;
            default:
                System.out.println("Неверный выбор");
        }
    }

    public void addObjectClass(CollectionFillObject collectionFill) {
        System.out.println(
                "Выберите тип добавляемого объекта:\n" +
                        "1.Person\n" +
                        "2.Phone\n" +
                        "3.Car");
        int choice = scanner.nextInt();
        if (choice == 0) return;

        switch (choice) {
            case 1:
                type = "Person";
                collectionFill.fillWithObject(type);
                break;
            case 2:
                type = "Phone";
                collectionFill.fillWithObject(type);
                break;
            case 3:
                type = "Car";
                collectionFill.fillWithObject(type);
                break;
            default:
                System.out.println("Неверный выбор");

        }
    }

    public void choosingFileType() {
        System.out.println(
                "Выберите тип файла с данными:\n" +
                        "1.json\n" +
                        "2.txt\n" +
                        "3.csv");
        int choice = scanner.nextInt();
        if (choice == 0) return;
        switch (choice) {
            case 1:
                JsonParse jsonParse = new JsonParse(objectAddService);
                FileFill fileFill = new FileFill(jsonParse);
                fileFill.fillWithAllObjects();
                break;
            case 2, 3:
                System.out.println("Работаем над этим.....");
                break;
            default:
                System.out.println("Неверный выбор");
        }
    }
    public void writeCollectionToFile(){
        System.out.println("Выберите или создайте файл для записи");
        fileWrite.writeAllCollectionToFile(initCollection);
    }

    public void exit() {
        System.out.println("Программа завершена!");
    }
}
