package dev.aston.sort;

import java.util.*;
import java.util.concurrent.*;

public class QuickSort<T extends Comparable<? super T>> implements SortStrategy<T> {

    private static final int MAX_PARALLEL_DEPTH = 1;

    @Override
    public Collection<T> sort(Collection<? extends T> c, Comparator<? super T> comparator) {
        List<T> list = new ArrayList<>(c);
        sort(list, 0, list.size() - 1, comparator);
        return list;
    }


    private void sort(List<T> list, int low, int high, Comparator<? super T> comparator) {
        if (low >= high) return;
        T pivot = list.get(low + (high - low) / 2);

        int left = low;
        int right = high;

        while (left <= right) {

            while (compare(list.get(left), pivot, comparator) < 0) left++;
            while (compare(list.get(right), pivot, comparator) > 0) right--;
            if (left <= right) {
                Collections.swap(list, left, right);
                left++;
                right--;
            }
        }
        sort(list, low, right, comparator);
        sort(list, left, high, comparator);
    }


    @Override
    public Collection<T> parallelSort(Collection<? extends T> c, Comparator<? super T> comparator) throws InterruptedException {
        List<T> list = new ArrayList<>(c);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            parallelQuickSort(list, 0, list.size() - 1, comparator, executor, 0);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);
        }


        return list;
    }

    private void parallelQuickSort(List<T> list, int low, int high, Comparator<? super T> comparator,
                                   ExecutorService executor, int depth) throws ExecutionException, InterruptedException {
        if (low >= high) return;


        T pivot = list.get(low + (high - low) / 2);
        int left = low;
        int right = high;

        while (left <= right) {
            while (compare(list.get(left), pivot, comparator) < 0) left++;
            while (compare(list.get(right), pivot, comparator) > 0) right--;
            if (left <= right) {
                Collections.swap(list, left, right);
                left++;
                right--;
            }
        }

        if (depth < MAX_PARALLEL_DEPTH) {
            int finalRight = right;
            Future<?> leftFuture = executor.submit(() -> {
                try {
                    parallelQuickSort(list, low, finalRight, comparator, executor, depth + 1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            int finalLeft = left;
            Future<?> rightFuture = executor.submit(() -> {
                try {
                    parallelQuickSort(list, finalLeft, high, comparator, executor, depth + 1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            leftFuture.get();
            rightFuture.get();
        } else {
            // Все последующие уровни - последовательно
            parallelQuickSort(list, low, right, comparator, executor, depth + 1);
            parallelQuickSort(list, left, high, comparator, executor, depth + 1);
        }
    }

    private int compare(T a, T b, Comparator<? super T> comparator) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return a.compareTo(b);
    }
}
