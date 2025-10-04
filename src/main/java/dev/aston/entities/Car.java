package dev.aston.entities;

import java.util.Comparator;

public class Car implements Comparable<Car> {
    private final String brand;
    private final String model;
    private final int year;

    public Car(Builder b) {
        this.brand = b.brand;
        this.model = b.model;
        this.year = b.year;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public static class Builder {
        private String brand;
        private String model;
        private int year;

        public Builder brand(String b) {
            this.brand = b;
            return this;
        }

        public Builder model(String m) {
            this.model = m;
            return this;
        }

        public Builder year(int y) {
            this.year = y;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    public static final class Comparators {
        Comparator<Car> BY_BRAND = Comparator.comparing(Car::getBrand);
        Comparator<Car> BY_MODEL = Comparator.comparing(Car::getModel);
        Comparator<Car> BY_YEAR = Comparator.comparingInt(Car::getYear);
    }

    @Override
    public int compareTo(Car o) {
        int byBrand = this.brand.compareTo(o.brand);
        if (byBrand != 0) return byBrand;

        int byModel = this.model.compareTo(o.model);
        if (byModel != 0) return byModel;

        return Integer.compare(this.year, o.year);
    }
}
