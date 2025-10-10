package dev.aston.sort;

import java.util.Collection;

public interface SortStrategy<T extends Comparable<? super T>> {
    Collection<T> sort(Collection<? extends T> c);
}
