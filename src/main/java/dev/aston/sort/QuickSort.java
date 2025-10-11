package dev.aston.sort;

import java.util.*;

public class QuickSort<T> implements SortStrategy<T> {
    @Override
    public Collection<T> sort(Collection<? extends T> c, Comparator<? super T> comparator) {
        List<T> list = new ArrayList<>(c);
        sort(list, 0, list.size() - 1, comparator);
        return list;
    }

    private void sort(List<T> list, int low, int high, Comparator<? super T> comparator) {
        if (low > high) return;
        T pivot = list.get(low + (high - low) / 2);

        int left = low;
        int right = high;

        while (left <= right) {

            while (comparator.compare(list.get(left), pivot) < 0) left++;
            while (comparator.compare(list.get(right), pivot) > 0) right--;
            if (left <= right) {
                Collections.swap(list, left, right);
                left++;
                right--;
            }
        }
        sort(list, low, right, comparator);
        sort(list, left, high, comparator);
    }
}
