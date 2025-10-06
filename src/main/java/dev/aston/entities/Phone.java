package dev.aston.entities;

import java.util.Comparator;

public class Phone implements Comparable<Phone> {
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
    }

    public static final class Comparators {
        public static final Comparator<Phone> BY_BRAND = Comparator.comparing(Phone::getBrand);
        public static final Comparator<Phone> BY_MODEL = Comparator.comparing(Phone::getModel);
        public static final Comparator<Phone> BY_MEMORY = Comparator.comparingInt(Phone::getMemory);
        public static final Comparator<Phone> BY_DISPLAY_SIZE = Comparator.comparingInt(Phone::getDisplaySize);
    }

    @Override
    public int compareTo(Phone o) {
        int byBrand = this.brand.compareTo(o.brand);
        if (byBrand != 0) return byBrand;

        int byModel = this.model.compareTo(o.model);
        if (byModel != 0) return byBrand;

        int byMemory = Integer.compare(this.memory, o.memory);
        if (byMemory != 0) return byMemory;

        return Integer.compare(this.displaySize, o.displaySize);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", memory=" + memory +
                ", displaySize=" + displaySize +
                '}';
    }
}
