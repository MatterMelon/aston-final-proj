package dev.aston.entities;

public class Phone {
    private final String brand;
    private final String model;
    private final int memory;
    private final int displaySize;

    public Phone(Builder b) {
        this.brand = b.brand;
        this.model = b.model;
        this.memory = b.memory;
        this.displaySize = b.displaySize;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getMemory() {
        return memory;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public static final class Builder {
        private String brand;
        private String model;
        private int memory;
        private int displaySize;

        public Builder brand(String b) {
            this.brand = b;
            return this;
        }

        public Builder model(String m) {
            this.model = m;
            return this;
        }

        public Builder memory(int m) {
            this.memory = m;
            return this;
        }

        public Builder displayMemory(int dm) {
            this.displaySize = dm;
            return this;
        }

        public Phone build() {
            return new Phone(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "brand='" + brand + '\'' +
                    ", model='" + model + '\'' +
                    ", memory=" + memory +
                    ", displaySize=" + displaySize +
                    '}';
        }
    }
}
