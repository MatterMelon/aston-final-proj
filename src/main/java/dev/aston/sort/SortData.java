package dev.aston.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class SortData<T> {
    public interface SortStrategy<T> {
        void sort(List<T> list, Comparator<T> comparator);
    }
    
    /*
     * Merge sort
     */
    private class Mergesort implements SortStrategy<T> {
        private ExecutorService executor;
        final int depth = 1;
        final int maxDepthPapths = 4;

        @Override
        public void sort(List<T> list, Comparator<T> comparator) {
            /*
             * Executor чтобы не использовать common-pool JVM
             */
            try {
                if (list == null || list.size() <= 1) return;
                this.executor = Executors.newFixedThreadPool(maxThreads(depth));
                mergeSort(list, 0, list.size()-1, comparator, depth);
            } finally {
                if (executor != null) {
                    executor.shutdown();
                }
            }
        }

        private int maxThreads(int depth) {
            if (depth > maxDepthPapths) {
                return maxDepthPapths;
            } else {
                return Math.max(1, depth);
            }
        }

        private static int depthSubArrayCalc(int depth) {
            int lengthDepth = 3;
            int depthMultiplier = 10;
            return lengthDepth + (depth * depthMultiplier);
        }

        private void mergeSort(List<T> list, int left, int right, Comparator<T> comparator, int depth) {
            if (left < right) {
                int size = right - left + 1; // Размер подмассива
                int massDepth = depthSubArrayCalc(depth);

                /*
                 * Для избегания избыточности использования параллельности
                 */
                if (size < massDepth) {
                    int mid = left + (right - left) / 2;
                    mergeSort(list, left, mid, comparator, depth+1);
                    mergeSort(list, mid + 1, right, comparator, depth+1);
                    merge(list, left, mid, right, comparator);
                } else if ((depth >= 2 && depth <= 3) || (depth < 4 && size>massDepth)) {
                    int mid = left + (right - left) / 2;
                    /*
                     * CompletableFuture.runAsync - запускает поток асинхронно
                     */
                    CompletableFuture<?> leftFuture = CompletableFuture.runAsync(() ->
                        mergeSort(list, left, mid, comparator, depth+1), executor);
                    CompletableFuture<?> rightFuture = CompletableFuture.runAsync(() ->
                        mergeSort(list, mid + 1, right, comparator, depth+1), executor);
                    /*
                     * join() ожидает конца работы future потока
                     * и не требует обработки исключений
                     */
                    leftFuture.join();
                    rightFuture.join();
                    merge(list, left, mid, right, comparator);
                } else {
                    int bigSize = right - left + 1;
                    int pathSize = bigSize / 4;
                    int leftPathSize = bigSize % 4;

                    int mid1 = left + pathSize - 1 + (leftPathSize > 0 ? 1 : 0);
                    int mid2 = mid1 + pathSize + (leftPathSize > 1 ? 1 : 0);
                    int mid3 = mid2 + pathSize + (leftPathSize > 2 ? 1 : 0);

                    CompletableFuture<?> firstPathFuture = CompletableFuture.runAsync(() ->
                        mergeSort(list, left, mid1, comparator, depth+1), executor);
                    CompletableFuture<?> secondPathFuture = CompletableFuture.runAsync(() ->
                        mergeSort(list, mid1+1, mid2, comparator, depth+1), executor);
                    CompletableFuture<?> thirdPathFuture = CompletableFuture.runAsync(() ->
                        mergeSort(list, mid2+1, mid3, comparator, depth+1), executor);
                    CompletableFuture<?> fourthPathFuture = CompletableFuture.runAsync(() ->
                        mergeSort(list, mid3+1, right, comparator, depth+1), executor);
                    CompletableFuture.allOf(firstPathFuture, secondPathFuture, thirdPathFuture, fourthPathFuture).join();
                    merge(list, left, mid1, mid2, comparator);
                    merge(list, left, mid2, mid3, comparator);
                    merge(list, left, mid3, right, comparator);
                }
            }
        }

        private void merge(List<T> list, int left, int mid, int right, Comparator<T> comparator) {
            Object[] temp = new Object[right - left + 1];
            int i = left, j = mid + 1, k = 0;
            
            /*
             * Цикл сортировки:
             * добавляет элементы во временный массив temp
             * k - индекс временного массива temp
             */
            while (i <= mid && j <= right) {
                if (comparator.compare(list.get(i), list.get(j)) <= 0) {
                    temp[k++] = list.get(i++);
                } else {
                    temp[k++] = list.get(j++);
                }
            }

            /*
             * Оставшиеся элементы
             */
            while (i <= mid) temp[k++] = list.get(i++);
            while (j <= right) temp[k++] = list.get(j++);

            /*
             * Возвращение результатов в исходный список
             */
            for (int index = 0; index < temp.length; index++) {
                list.set(left + index, (T) temp[index]); // принудительное приведение типа
            }
        }
    }

    public void useMergeSort() {
        this.strategy = new Mergesort();
    }

    /*
     * Quick sort
     */
    private class QuickSort implements SortStrategy<T> {
        private final Random random = new Random(); // случайный опорный элемент (pivot) сортировки
        private ExecutorService executor;
        final int depth = 1;
        final int maxDepthPaths = 4;
        /*
         * semaphore для лучшей работы с неудачными pivotIndex
         */
        private Semaphore semaphore = new Semaphore(maxDepthPaths);


        @Override
        public void sort(List<T> list, Comparator<T> comparator) {
            try {
                if (list == null || list.size() <= 1) return;
                this.executor = Executors.newFixedThreadPool(maxThreads(depth));
                quickSort(list, 0, list.size()-1, comparator, depth);
            } finally {
                if (executor != null) {
                    executor.shutdown();
                }
            }
        }

        private int maxThreads(int depth) {
            if (depth > maxDepthPaths) {
                return maxDepthPaths;
            } else {
                return Math.max(1, depth);
            }
        }

        private static int depthSubArrayCalc(int depth) {
            int lengthDepth = 3;
            int depthMultiplier = 10;
            return lengthDepth + (depth * depthMultiplier);
        }

        private void quickSort(List<T> list, int low, int high, Comparator<T> comparator, int depth) {
            if (low >= high) return;

            int size = high - low + 1;
            int massDepth = depthSubArrayCalc(depth);
            int pivotIndex = randomPartition(list, low, high, comparator);
            semaphore.acquireUninterruptibly();
            
            try {
                if (size < massDepth || depth >= maxDepthPaths) {
                    quickSort(list, low, pivotIndex-1, comparator, depth+1);
                    quickSort(list, pivotIndex+1, high, comparator, depth+1);
                } else {
                    CompletableFuture<Void> leftFuture = CompletableFuture.runAsync(() -> {
                        quickSort(list, low, pivotIndex-1, comparator, depth+1);
                        semaphore.release();
                    }, executor);
                    CompletableFuture<Void> rightFuture = CompletableFuture.runAsync(() -> {
                        quickSort(list, pivotIndex+1, high, comparator, depth+1);
                        semaphore.release();
                    }, executor);
                    CompletableFuture.allOf(leftFuture, rightFuture).join();
                }
            } finally {
                semaphore.release();
            }
        }

        private int randomPartition(List<T> list, int low, int high, Comparator<T> comparator) {
            int randomIndex = low + random.nextInt(high - low + 1);
            
            Collections.swap(list, randomIndex, high); // размещение опорного элемента в конец
            return partition(list, low, high, comparator);
        }

        /*
         * размещение элементов левее или правее от опорного элемента
         */
        private int partition(List<T> list, int low, int high, Comparator<T> comparator) {
            T pivot = list.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (comparator.compare(list.get(j), pivot) <= 0) {
                    i++;
                    Collections.swap(list, i, j);
                }
            }
            Collections.swap(list, i + 1, high);
            return i + 1;
        }
    }

    public void useQuickSort() {
        this.strategy = new QuickSort();
    }

    // ======================================================================

    /*
     * Menu of sorts
     */
    public enum PickSort {
        MERGE("MergeSort"),
        QUICK("QuickSort"),
        SORTEVEN("EvenSort");

        private final String sortName;

        PickSort(String sortName) {
            this.sortName = sortName;
        }

        public String getSortName() {
            return sortName;
        }
    }

    public List<T> usePickSort(PickSort pickSort, Function<T, ? extends Comparable>... field) {
        switch (pickSort) {
            case MERGE -> {
                useMergeSort();
                return sortByMultiply(field);
            }
            case QUICK -> {
                useQuickSort();
                return sortByMultiply(field);
            }
            case SORTEVEN -> {
                if (field.length != 1) {
                    throw new IllegalArgumentException("Picked more than one field");
                }
                useQuickSort();
                return sortByEven(field[0]);
            }
            default -> {
                useQuickSort();
                return sortByMultiply(field);
            }
        }
    }

    public static PickSort[] getSorts() {
        return PickSort.values();
    }

    // ======================================================================

    private List<T> data;
    private SortStrategy<T> strategy;

    public SortData(List<T> data) {
        // try {
        //     this.data = new ArrayList<>(data);
        // } catch (NullPointerException e) {
        //     System.out.println("List is null!");
        // }

        this.data = data != null ? new ArrayList<>(data) : new ArrayList<>();
        this.strategy = new QuickSort();
    }

    public void chooseStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public <U extends Comparable<U>> List<T> sortByEven(Function<T, U> field) {
        // Comparator<T> comparator = Comparator.comparing(field);
        Comparator<T> comparator = new Comparator<T>() {
            @Override
            public int compare(T a, T b) {
                U valueA = field.apply(a);
                U valueB = field.apply(b);

                /*
                 * Специфичное условие если одно из значений Integer
                 */
                if (valueA instanceof Integer) {
                    Integer valueAA = (Integer) valueA;
                    if (valueB instanceof Integer) {
                        Integer valueBB = (Integer) valueB;
                        if (valueAA % 2 != 0 && valueBB % 2 != 0) {
                            return 0;
                        } else if (valueAA % 2 != 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }

                    /*
                     * Если "b" не Integer, то проверяем A на нечетность
                     */
                    if (valueAA % 2 != 0) {
                        return 1;
                    } else {
                        return valueA.compareTo(valueB);
                    }
                }
                /*
                 * Если "a" не Integer, то проверяем B на нечетность
                 */
                if (valueB instanceof Integer) {
                    Integer valueBB = (Integer) valueB;
                    if (valueBB % 2 != 0) {
                        return 1;
                    } else {
                        return valueA.compareTo(valueB);
                    }
                }
                /*
                 * Базовый исход
                 */
                return valueA.compareTo(valueB);
            }
        };
        strategy.sort(data, comparator);
        return data;
    }

    /*
     * <U extends Comparable<U>> - гарантирует что fields можно сравнивать друг с другом;
     * Function<T, U> field - T объект и U тип поля
     */
    public final List<T> sortByMultiply(Function<T, ? extends Comparable>... fields) {
        Comparator<T> comparator = Arrays.stream(fields)
            .map(Comparator::comparing)
            .reduce(Comparator::thenComparing)
            .orElseThrow(() -> new IllegalArgumentException("Fields are empty!"));

            strategy.sort(data, comparator);
            return data;
    }

    public static void main(String[] args) {
        /*
         * Init example
         */
        record Person(String name, int age) {}

        List<Person> people = List.of(
            new Person("Alice", 25),
            new Person("Bob", 30), 
            new Person("Alice", 20),
            new Person("Bob", 15)
        );

        SortData<Person> sortData = new SortData<>(people);

        /*
         * Menu
         */
        PickSort[] pickSorts = SortData.getSorts();
        for (PickSort elem : pickSorts) {
            System.out.println(elem.ordinal() + ". " + elem.getSortName());
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Choose sorting: [1-" + pickSorts.length + "] ");
            int scPick = scanner.nextInt() -1;

            if (scPick < 0 || scPick >= pickSorts.length) {
                System.out.println("Wrong number!");
                return;
            }

            /*
             * Single sort
             */
            System.out.println("\nSingle");
            sortData.usePickSort(pickSorts[scPick], Person::name).forEach(System.out::println);
    
            /*
             * Single + Integer specific modify with even
             */
            System.out.println("\nSingle + Integer spec");
            sortData.usePickSort(pickSorts[scPick], Person::age).forEach(System.out::println);
    
            /*
             * Multiply sort
             */
            System.out.println("\nMultiply");
            sortData.usePickSort(pickSorts[scPick], Person::name, Person::age).forEach(System.out::println);
        }
    }
}