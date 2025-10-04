package dev.aston.entities;

public class Car {
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
}
