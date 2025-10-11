package dev.aston.sort;

import java.util.Collection;
import java.util.Comparator;

public interface SortStrategy<T> {
    Collection<T> sort(Collection<? extends T> c, Comparator<? super T> comparator);
}
