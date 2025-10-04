package dev.aston.entities;

import java.util.Comparator;

public class Person implements Comparable<Person>{
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

    public static final class Comparators {
        public static final Comparator<Person> BY_NAME = Comparator.comparing(Person::getName);
        public static final Comparator<Person> BY_SURNAME = Comparator.comparing(Person::getSurname);
        public static final Comparator<Person> BY_AGE = Comparator.comparingInt(Person::getAge);
    }

    @Override
    public int compareTo(Person o) {
        int byName = this.name.compareTo(o.name);
        if (byName != 0) return byName;

        int bySurname = this.surname.compareTo(o.surname);
        if (bySurname != 0) return bySurname;

        return Integer.compare(this.age, o.age);
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
