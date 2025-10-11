package dev.aston.fill.Validate;

public class FileValidation {

    public boolean checkingString(String str, String variable) {
        String letterRegex = "^[A-Za-zА-Яа-я\\s]+$";
        String abbreviationRegex = "^[A-Za-zА-Яа-я0-9 '\\-]+$";


        if (str == null || str.isEmpty()) {
            return false;
        }

        switch (variable) {
            case "name", "surname", "brand":
                return str.matches(letterRegex);
            case "model":
                return str.matches(abbreviationRegex);
            default:
                return false;
        }
    }

    public boolean checkingInt(int number, String variable) {
        switch (variable) {
            case "memory":
                return number > 0 && number <= 1024;
            case "year":
                return number >= 1885 && number <= 2025;
            case "displaySize":
                return number >= 4 && number <= 8;
            case "age":
                return number > 0 && number <= 122;
            default:
                return false;
        }
    }
}
