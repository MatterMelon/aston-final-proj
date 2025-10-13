package dev.aston.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class SortData<T> {
    public interface SortStrategy<T> {
        void sort(List<T> list, Comparator<T> comparator);
    }
    
    /*
     * Merge sort
     */
    private class Mergesort implements SortStrategy<T> {
        @Override
        public void sort(List<T> list, Comparator<T> comparator) {
            if (list == null || list.size() <= 1) return;
            mergeSort(list, 0, list.size()-1, comparator);
        }

        private void mergeSort(List<T> list, int left, int right, Comparator<T> comparator) {
            if (left < right) {
                int mid = left + (right - left) / 2;
                
                mergeSort(list, left, mid, comparator);
                mergeSort(list, mid + 1, right, comparator);
                merge(list, left, mid, right, comparator);
            }
        }

        private void merge(List<T> list, int left, int mid, int right, Comparator<T> comparator) {
            List<T> leftList = new ArrayList<>(list.subList(left, mid + 1));
            List<T> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));
            
            int i = 0, j = 0, k = left;
            
            while (i < leftList.size() && j < rightList.size()) {
                if (comparator.compare(leftList.get(i), rightList.get(j)) <= 0) {
                    list.set(k++, leftList.get(i++));
                } else {
                    list.set(k++, rightList.get(j++));
                }
            }
            
            while (i < leftList.size()) {
                list.set(k++, leftList.get(i++));
            }
            
            while (j < rightList.size()) {
                list.set(k++, rightList.get(j++));
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
        @Override
        public void sort(List<T> list, Comparator<T> comparator) {
            quickSort(list, 0, list.size()-1, comparator);
        }

        private void quickSort(List<T> list, int low, int high, Comparator<T> comparator) {
            if (low < high) {
                int pivotIndex = partition(list, low, high, comparator);
                quickSort(list, low, pivotIndex-1, comparator);
                quickSort(list, pivotIndex+1, high, comparator);
            }
        }

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

    /*
     * Tim sort (standard)
     */
    private class SortStandard implements SortStrategy<T> {
        @Override
        public void sort(List<T> list, Comparator<T> comparator) {
            list.sort(comparator);
        }
    }

    public void useSortStandard() {
        this.strategy = new SortStandard();
    }

    // ======================================================================

    /*
     * Menu of sorts
     */
    public enum PickSort {
        STANDARD("TimSort"),
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
            case STANDARD -> {
                useSortStandard();
                return sortByMultiply(field);
            }
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
                useSortStandard();
                return sortByEven(field[0]);
            }
            default -> {
                useSortStandard();
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
        this.strategy = new SortStandard();
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
            System.out.println(elem.getSortName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose sorting: [1-" + pickSorts.length + "] ");

        int scPick = scanner.nextInt() -1;
        if (0 < scPick || scPick > pickSorts.length) {
            System.out.println("Wrong number!");
            scanner.close();
            return;
        }

        /*
         * Single sort
         */
        System.out.println("Single");
        System.out.println(sortData.usePickSort(pickSorts[scPick], Person::name));

        /*
         * Single + Integer specific modify with even
         */
        System.out.println("Single + Integer spec");
        System.out.println(sortData.usePickSort(pickSorts[scPick], Person::age));

        /*
         * Multiply sort
         */
        System.out.println("Multiply");
        System.out.println(sortData.usePickSort(pickSorts[scPick], Person::name, Person::age));
    }
}