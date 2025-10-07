package dev.aston.MenuNavigator;

import dev.aston.fill.*;
import dev.aston.fill.FileFounder.JsonParse;

import java.util.Scanner;

public class MenuNavigator {
    InitCollection initCollection = new InitCollection();
    ObjectAddService objectAddService = new ObjectAddService(initCollection);

    private final Scanner scanner = new Scanner(System.in);

    public void runOurProject() {
        String message = "Выбрана неверная опция! Попробуйте заново.";
        greatings();
        menu();

        loop:
        while (true) {
            if (scanner.hasNextInt()) {
                int menuInput = scanner.nextInt();
                //scanner.next();
                switch (menuInput) {
                    case 1:
                        addNewObject();
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
                "0. Завершение работы\n"};
        System.out.println("\nМеню:");
        for (int i = 0; i < menu.length; i++) {
            System.out.print(menu[i]);
        }
        System.out.println("\nВведите нужную опцию и нажмите Enter [1..4]: ");
    }

    public void addNewObject() {
        /*
        Внутри метода реализован вызов подменю:
        Каждый пункт характеризует выбор создания обьекта(из файла,рандом,руками)
        В зависимости от выбора вызывается соответствующий метод
         */
        addObjectMethod();
    }

    public void objectSearch() {
        /*
        Вызывает метод бинарного поиска обьекта в коллекции
         */
        System.out.println(2);
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
        /*
        Просто итерация по коллекции с выводом в консоль
         */
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

    public void addObjectClass(CollectionFill collectionFill) {
        System.out.println(
                "Выберите тип добавляемого объекта:\n" +
                        "1.Person\n" +
                        "2.Phone\n" +
                        "3.Car");
        int choice = scanner.nextInt();
        if (choice == 0) return;
        switch (choice) {
            case 1:
                collectionFill.fillWithPersons();
                break;
            case 2:
                collectionFill.fillWithPhones();
                break;
            case 3:
                collectionFill.fillWithCars();
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

    public void exit() {
        System.out.println("Программа завершена!");
    }
}
