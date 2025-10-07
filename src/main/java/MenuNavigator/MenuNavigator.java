package MenuNavigator;

import java.util.Scanner;

public class MenuNavigator {
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
        System.out.println(1);
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
        System.out.println(4);
    }

    public void exit() {
        System.out.println("Программа завершена!");
    }
}


