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
import dev.aston.sort.BubbleSort;
import dev.aston.sort.QuickSort;
import dev.aston.sort.Sorter;

import javax.swing.plaf.synth.SynthUI;
import javax.swing.text.Style;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MenuNavigator {
    InitCollection initCollection = new InitCollection();
    ObjectAddService objectAddService = new ObjectAddService(initCollection);
    FileWrite fileWrite = new FileWrite();
    private String type;

    private Scanner scanner = new Scanner(System.in);

    void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

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

    private List<Person> getPersonList() {
        List<Person> list = new ArrayList<>();
        initCollection.getList().forEach(obj -> {
            try {
                list.add((Person) obj);
            } catch (Exception e) {}
        });
        return list;
    }

    private List<Phone> getPhoneList() {
        List<Phone> list = new ArrayList<>();
        initCollection.getList().forEach(obj -> {
            try {
                list.add((Phone) obj);
            } catch (Exception e) {}
        });
        return list;
    }

    private List<Car> getCarList() {
        List<Car> list = new ArrayList<>();
        initCollection.getList().forEach(obj -> {
            try {
                list.add((Car) obj);
            } catch (Exception e) {}
        });
        return list;
    }

    private void searchPerson(ManuallyValidate validate) {
        // Собираем коллекцию нужного типа
        List<Person> list = getPersonList();

        if (list.isEmpty()) {
            System.out.println("Объекты выбранного типа отсутствуют.");
            return;
        }

        Collections.sort(list);
        System.out.println("Коллекция отсортирована:");
        list.forEach(System.out::println);

        Person target = createPerson(validate);
        BinarySearcher<Person> searcher = new BinarySearcher<>();
        int foundIndex = searcher.search(list, target);

        if (foundIndex == -1) {
            System.out.println("Элемент не найден!");
            return;
        }

        System.out.println("Индекс элемента: " + foundIndex);
        Person foundObj = list.get(foundIndex);
        System.out.println(foundObj);

        System.out.println(
                """
                        Записать в файл?:
                        1. Да
                        2. Нет""");

        int choice = scanner.nextInt();

        while (true) {
            switch (choice) {
                case 1:
                    fileWrite.writeObjectToFile(foundObj);
                    return;
                case 2:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void searchPhone(ManuallyValidate validate) {
        // Собираем коллекцию нужного типа
        List<Phone> list = getPhoneList();

        if (list.isEmpty()) {
            System.out.println("Объекты выбранного типа отсутствуют.");
            return;
        }

        Collections.sort(list);
        System.out.println("Коллекция отсортирована:");
        list.forEach(System.out::println);

        Phone target = createPhone(validate);
        BinarySearcher<Phone> searcher = new BinarySearcher<>();
        int foundIndex = searcher.search(list, target);

        if (foundIndex == -1) {
            System.out.println("Элемент не найден!");
            return;
        }

        System.out.println("Индекс элемента: " + foundIndex);
        Phone foundObj = list.get(foundIndex);
        System.out.println(foundObj);

        System.out.println(
                """
                        Записать в файл?:
                        1. Да
                        2. Нет""");

        int choice = scanner.nextInt();

        while (true) {
            switch (choice) {
                case 1:
                    fileWrite.writeObjectToFile(foundObj);
                    return;
                case 2:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void searchCar(ManuallyValidate validate) {
        // Собираем коллекцию нужного типа
        List<Car> list = getCarList();

        if (list.isEmpty()) {
            System.out.println("Объекты выбранного типа отсутствуют.");
            return;
        }

        Collections.sort(list);
        System.out.println("Коллекция отсортирована:");
        list.forEach(System.out::println);

        Car target = createCar(validate);
        BinarySearcher<Car> searcher = new BinarySearcher<>();
        int foundIndex = searcher.search(list, target);

        if (foundIndex == -1) {
            System.out.println("Элемент не найден!");
            return;
        }

        System.out.println("Индекс элемента: " + foundIndex);
        Car foundObj = list.get(foundIndex);
        System.out.println(foundObj);

        System.out.println(
                """
                        Записать в файл?:
                        1. Да
                        2. Нет""");

        int choice = scanner.nextInt();

        while (true) {
            switch (choice) {
                case 1:
                    fileWrite.writeObjectToFile(foundObj);
                    return;
                case 2:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    public String chooseObjectType() {
        System.out.println(
                """
                        Выберите тип объекта:
                        1.Person
                        2.Phone
                        3.Car""");

        int choice = scanner.nextInt();
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
        return type;
    }

    public String chooseSortType() {
        System.out.println(
                """
                        Выберите тип сортировки:
                        1.Quick
                        2.Merge""");

        int choice = scanner.nextInt();
        String type = "";

        while (type.isEmpty()) {
            switch (choice) {
                case 1:
                    type = "Quick";
                    break;
                case 2:
                    type = "Bubble";
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
        return type;
    }

    public void objectSearch() {
        ManuallyValidate validate = new ManuallyValidate();

        System.out.println("Коллекция будет автоматически отсортирована!");

        String type = chooseObjectType();

        switch (type) {
            case "Person" -> searchPerson(validate);
            case "Phone" -> searchPhone(validate);
            case "Car" -> searchCar(validate);
        };
    }

    private Comparator<Person> choosePersonComparator() {
        System.out.println(
                """
                        Выберите поле для сортировки:
                        1. Name
                        2. Surname
                        3. Age
                        4. Все поля""");

        int choice = scanner.nextInt();
        Comparator<Person> comparator = null;

        while (comparator == null) {
            switch (choice) {
                case 1:
                    comparator = Person.Comparators.BY_NAME;
                    break;
                case 2:
                    comparator = Person.Comparators.BY_SURNAME;
                    break;
                case 3:
                    comparator = Person.Comparators.BY_AGE;
                    break;
                case 4:
                    return null;
                default:
                    System.out.println("Неверный выбор");
            }
        }
        return comparator;
    }

    private Comparator<Phone> choosePhoneComparator() {
        System.out.println(
                """
                        Выберите поле для сортировки:
                        1. Brand
                        2. Model
                        3. Memory
                        4. displaySize
                        5. Все поля""");

        int choice = scanner.nextInt();
        Comparator<Phone> comparator = null;

        while (comparator == null) {
            switch (choice) {
                case 1:
                    comparator = Phone.Comparators.BY_BRAND;
                    break;
                case 2:
                    comparator = Phone.Comparators.BY_MODEL;
                    break;
                case 3:
                    comparator = Phone.Comparators.BY_MEMORY;
                    break;
                case 4:
                    comparator = Phone.Comparators.BY_DISPLAY_SIZE;
                    break;
                case 5:
                    return null;
                default:
                    System.out.println("Неверный выбор");
            }
        }
        return comparator;
    }

    private Comparator<Car> chooseCarComparator() {
        System.out.println(
                """
                        Выберите поле для сортировки:
                        1. Brand
                        2. Model
                        3. Year
                        4. Все поля""");

        int choice = scanner.nextInt();
        Comparator<Car> comparator = null;

        while (comparator == null) {
            switch (choice) {
                case 1:
                    comparator = Car.Comparators.BY_BRAND;
                    break;
                case 2:
                    comparator = Car.Comparators.BY_MODEL;
                    break;
                case 3:
                    comparator = Car.Comparators.BY_YEAR;
                    break;
                case 4:
                    return null;
                default:
                    System.out.println("Неверный выбор");
            }
        }
        return comparator;
    }

    private boolean useMultiThread() {
        System.out.println(
                """
                        Использовать многопоточность?:
                        1. Да
                        2. Нет""");

        int choice = scanner.nextInt();

        while (true) {
            switch (choice) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private Collection<Person> sortPerson() throws InterruptedException {
        List<Person> list = getPersonList();

        Sorter<Person> sorter = new Sorter<>();
        String sortType = chooseSortType();

        if (sortType.equals("Quick")) {
            sorter.setStrategy(new QuickSort<>());
        } else {
            sorter.setStrategy(new BubbleSort<>());
        }

        Comparator<Person> comparator = choosePersonComparator();

        boolean isMultiThread = useMultiThread();

        if (isMultiThread) {
            return sorter.sortParallel(list, comparator);
        }

        return sorter.sort(list, comparator);
    }

    private Collection<Phone> sortPhone() throws InterruptedException {
        List<Phone> list = getPhoneList();

        Sorter<Phone> sorter = new Sorter<>();
        String sortType = chooseSortType();

        if (sortType.equals("Quick")) {
            sorter.setStrategy(new QuickSort<>());
        } else {
            sorter.setStrategy(new BubbleSort<>());
        }

        Comparator<Phone> comparator = choosePhoneComparator();

        boolean isMultiThread = useMultiThread();

        if (isMultiThread) {
            return sorter.sortParallel(list, comparator);
        }

        return sorter.sort(list, comparator);
    }

    private Collection<Car> sortCar() throws InterruptedException {
        List<Car> list = getCarList();

        Sorter<Car> sorter = new Sorter<>();
        String sortType = chooseSortType();

        if (sortType.equals("Quick")) {
            sorter.setStrategy(new QuickSort<>());
        } else {
            sorter.setStrategy(new BubbleSort<>());
        }

        Comparator<Car> comparator = chooseCarComparator();

        boolean isMultiThread = useMultiThread();

        if (isMultiThread) {
            return sorter.sortParallel(list, comparator);
        }

        return sorter.sort(list, comparator);
    }

    public void collectionSort() {
        String type = chooseObjectType();
        String sortType;
        switch (type) {
            case "Person":
                Collection<Person> people = new ArrayList<>();
                try {
                    people = sortPerson();
                } catch (InterruptedException e) {
                    System.out.println("Ошибка: ");
                    e.printStackTrace();
                }
                if (people.isEmpty()) {
                    System.out.println("Коллекция пуста!");
                    return;
                }
                System.out.println("Отсортированная коллекция:");
                people.forEach(System.out::println);
                System.out.println(
                        """
                                Записать в файл?:
                                1. Да
                                2. Нет""");

                int choice = scanner.nextInt();

                while (true) {
                    switch (choice) {
                        case 1:
                            InitCollection c = new InitCollection();
                            c.setList(people.stream()
                                    .map(obj -> (Object) obj)
                                    .collect(Collectors.toList()));
                            fileWrite.writeAllCollectionToFile(c);
                            return;
                        case 2:
                            return;
                        default:
                            System.out.println("Неверный выбор");
                    }
                }

            case "Phone":
                Collection<Phone> phones = new ArrayList<>();
                try {
                    phones = sortPhone();
                } catch (InterruptedException e) {
                    System.out.println("Ошибка: ");
                    e.printStackTrace();
                }
                if (phones.isEmpty()) {
                    System.out.println("Коллекция пуста!");
                    return;
                }
                System.out.println("Отсортированная коллекция:");
                phones.forEach(System.out::println);

                System.out.println(
                        """
                                Записать в файл?:
                                1. Да
                                2. Нет""");

                choice = scanner.nextInt();

                while (true) {
                    switch (choice) {
                        case 1:
                            InitCollection c = new InitCollection();
                            c.setList(phones.stream()
                                    .map(obj -> (Object) obj)
                                    .collect(Collectors.toList()));
                            fileWrite.writeAllCollectionToFile(c);
                            return;
                        case 2:
                            return;
                        default:
                            System.out.println("Неверный выбор");
                    }
                }
            case "Car":
                Collection<Car> cars = new ArrayList<>();
                try {
                    cars = sortCar();
                } catch (InterruptedException e) {
                    System.out.println("Ошибка: ");
                    e.printStackTrace();
                }

                if (cars.isEmpty()) {
                    System.out.println("Коллекция пуста!");
                    return;
                }
                System.out.println("Отсортированная коллекция:");
                cars.forEach(System.out::println);

                System.out.println(
                        """
                                Записать в файл?:
                                1. Да
                                2. Нет""");

                choice = scanner.nextInt();

                while (true) {
                    switch (choice) {
                        case 1:
                            InitCollection c = new InitCollection();
                            c.setList(cars.stream()
                                    .map(car -> (Object) car)
                                    .collect(Collectors.toList()));
                            fileWrite.writeAllCollectionToFile(c);
                            return;
                        case 2:
                            return;
                        default:
                            System.out.println("Неверный выбор");
                    }
                }
            default:
                break;
        }
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
