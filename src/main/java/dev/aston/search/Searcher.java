package dev.aston.search;

import java.util.Collection;

public interface Searcher<T extends Comparable<T>> {
    int search(Collection<T> collection, T targetElement);
}
