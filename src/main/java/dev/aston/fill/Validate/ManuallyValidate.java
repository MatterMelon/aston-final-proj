package dev.aston.fill.Validate;

import java.util.Scanner;

public class ManuallyValidate {
    Scanner scanner = new Scanner(System.in);
    public String checkingString(String variable) {
        String errorMessage = "Неверный формат ввода,попробуйте еще раз!";
        String emptyStringError = "Строка не содержит информации!";
        String letterRegex = "^[A-Za-zА-Яа-я\\s]+$";
        String abbreviationRegex = "^[A-Za-zА-Яа-я0-9 '\\-]+$";
        while (true) {
            String str = scanner.nextLine();
            if (!str.isEmpty()) {
                switch (variable) {
                    case "name", "surname", "brand":
                        if (str.matches(letterRegex)) return str;
                        else System.out.println(errorMessage);
                        break;
                    case "model":
                        if (str.matches(abbreviationRegex)) return str;
                        else System.out.println(errorMessage);
                        break;
                }
            } else System.out.println(emptyStringError);
        }
    }
    public int checkingInt(String variable) {
        String errorMessage = "Неверный формат ввода,попробуйте еще раз!";
        String wrongData = "Неверное значение,уточните информацию!";

        while (true) {
            String str = scanner.nextLine();
            int number;

            if (!str.isEmpty()) {
                try {
                    number = Integer.parseInt(str);
                    switch (variable) {
                        case "memory":
                            if (number > 0 && number <= 1024) return number;
                            else System.out.println(wrongData);
                            break;
                        case "year":
                            if (number >= 1885 && number <= 2025) return number;
                            else System.out.println(wrongData);
                            break;
                        case "displaySize":
                            if (number >= 4 && number <= 8) return number;
                            else System.out.println(wrongData);
                            break;
                        case "age":
                            if (number > 0 && number <= 122) return number;
                            else System.out.println(wrongData);
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(errorMessage);
                }
            } else
                System.out.println(errorMessage);
        }
    }
}


