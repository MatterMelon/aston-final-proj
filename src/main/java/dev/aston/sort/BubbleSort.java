package dev.aston.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BubbleSort<T extends Comparable<? super T>> implements SortStrategy<T> {
    @Override
    public Collection<T> sort(Collection<? extends T> c) {
        List<T> list = new ArrayList<>(c);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    Collections.swap(list, j, j + 1);
                }
            }
        }

        return list;
    }
}
