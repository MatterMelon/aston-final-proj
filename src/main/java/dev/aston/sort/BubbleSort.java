package dev.aston.sort;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

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
    @Override
    public Collection<T> parallelSort(Collection<? extends T> c, Comparator<? super T> comparator) throws InterruptedException{
        List<T> list = new ArrayList<>(c);
        int n = list.size();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        AtomicBoolean swapped = new AtomicBoolean(true);

        while (swapped.get()) {
            swapped.set(false);

            // Чётные индексы
            List<Callable<Void>> tasksEven = new ArrayList<>();
            for (int i = 0; i <= n - 2; i += 2) {
                final int idx = i;
                tasksEven.add(() -> {
                    synchronized (list) { // синхронизация для безопасного обмена
                        if (comparator.compare(list.get(idx), list.get(idx + 1)) > 0) {
                            Collections.swap(list, idx, idx + 1);
                            swapped.set(true);
                        }
                    }
                    return null;
                });
            }
            executor.invokeAll(tasksEven);

            // Нечётные индексы
            List<Callable<Void>> tasksOdd = new ArrayList<>();
            for (int i = 1; i <= n - 2; i += 2) {
                final int idx = i;
                tasksOdd.add(() -> {
                    synchronized (list) {
                        if (comparator.compare(list.get(idx), list.get(idx + 1)) > 0) {
                            Collections.swap(list, idx, idx + 1);
                            swapped.set(true);
                        }
                    }
                    return null;
                });
            }
            executor.invokeAll(tasksOdd);
        }

        executor.shutdown();

        return list;
    }
}
