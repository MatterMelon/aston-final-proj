package dev.aston.sort;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class BubbleSort<T extends Comparable<? super T>> implements SortStrategy<T> {

    @Override
    public Collection<T> sort(Collection<? extends T> c, Comparator<? super T> comparator) {
        List<T> list = new ArrayList<>(c);
        boolean swapped;
        for (int i = 0; i < list.size() - 1; i++) {
            swapped = false;
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (compare(list.get(j), list.get(j + 1), comparator) > 0) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return list;
    }

    @Override
    public Collection<T> parallelSort(Collection<? extends T> c, Comparator<? super T> comparator) throws InterruptedException {
        List<T> list = new ArrayList<>(c);
        int n = list.size();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicBoolean swapped = new AtomicBoolean(true);

        while (swapped.get()) {
            swapped.set(false);

            Callable<Void> evenTask = () -> {
                for (int i = 0; i <= n - 2; i += 2) {
                    synchronized (list) {
                        if (compare(list.get(i), list.get(i + 1), comparator) > 0) {
                            Collections.swap(list, i, i + 1);
                            swapped.set(true);
                        }
                    }
                }
                return null;
            };

            Callable<Void> oddTask = () -> {
                for (int i = 1; i <= n - 2; i += 2) {
                    synchronized (list) {
                        if (compare(list.get(i), list.get(i + 1), comparator
                        ) > 0) {
                            Collections.swap(list, i, i + 1);
                            swapped.set(true);
                        }
                    }
                }
                return null;
            };


            List<Callable<Void>> tasks = Arrays.asList(evenTask, oddTask);
            executor.invokeAll(tasks);
        }

        executor.shutdown();

        return list;
    }

    private int compare(T a, T b, Comparator<? super T> comparator) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return a.compareTo(b);
    }
}
