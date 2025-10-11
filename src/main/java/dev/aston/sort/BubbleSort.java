package dev.aston.sort;

import java.util.*;

public class BubbleSort<T> implements SortStrategy<T> {

    @Override
    public Collection<T> sort(Collection<? extends T> c, Comparator<? super T> comparator) {
        List<T> list = new ArrayList<>(c);
        boolean swapped;
        for (int i = 0; i < list.size() - 1; i++) {
            swapped = false;
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return list;
    }
}
