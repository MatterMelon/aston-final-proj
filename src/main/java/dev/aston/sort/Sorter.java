package dev.aston.sort;

import java.util.Collection;

public class Sorter<T extends Comparable<? super T>> {
    private SortStrategy<T> strategy;

    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public Collection<T> sort(Collection<T> collection) {
        if (strategy == null) {
            throw new IllegalStateException("Стратегия не установлена");
        }
        return strategy.sort(collection);
    }
}
