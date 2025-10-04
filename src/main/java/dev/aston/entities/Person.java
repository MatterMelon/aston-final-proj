package dev.aston.entities;

public class Person {
    private final String name;
    private final String surname;
    private final int age;

    private Person(Builder b) {
        this.name = b.name;
        this.surname = b.surname;
        this.age = b.age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public static class Builder {
        private String name;
        private String surname;
        private int age;

        public Builder name(String n) {
            this.name = n;
            return this;
        }

        public Builder surname(String s) {
            this.surname = s;
            return this;
        }

        public Builder age(int a) {
            this.age = a;
            return  this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
