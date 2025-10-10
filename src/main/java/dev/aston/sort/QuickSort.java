package dev.aston.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuickSort<T extends Comparable<? super T>> implements SortStrategy<T> {
    @Override
    public Collection<T> sort(Collection<? extends T> c) {
        List<T> list = new ArrayList<>(c);
        sort(list, 0, list.size() - 1);
        return list;
    }
    private void sort(List<T> list, int low, int high) {
        if (low > high) return;
        T pivot = list.get(low + (high - low) / 2);

        int left = low;
        int right = high;

        while (left <= right) {
            while (list.get(left).compareTo(pivot) < 0) left++;
            while (list.get(right).compareTo(pivot) > 0) right--;
            if (left <= right) {
                Collections.swap(list, left, right);
                left++;
                right--;
            }
        }
        sort(list, low, right);
        sort(list, left, high);
    }
}
