package dev.aston.sort;

import java.util.Collection;
import java.util.Comparator;

public interface SortStrategy<T> {
    Collection<T> sort(Collection<? extends T> c, Comparator<? super T> comparator);
    public Collection<T> parallelSort(Collection<? extends T> c, Comparator<? super T> comparator) throws InterruptedException;
}
