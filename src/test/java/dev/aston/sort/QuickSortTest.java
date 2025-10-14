package dev.aston.sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuickSortTest {

    private QuickSort<Integer> quickSort;

    @BeforeEach
    void setUp() {
        quickSort = new QuickSort<>();
    }

    @Test
    void testSortNaturalOrder() {
        List<Integer> input = Arrays.asList(3, 1, 4, 1, 5, 9);
        Collection<Integer> result = quickSort.sort(input, null);

        assertEquals(Arrays.asList(1, 1, 3, 4, 5, 9), new ArrayList<>(result));
    }

    @Test
    void testSortWithComparator() {
        Comparator<Integer> reversed = Comparator.reverseOrder();
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        Collection<Integer> result = quickSort.sort(input, reversed);

        assertEquals(Arrays.asList(5, 4, 3, 2, 1), new ArrayList<>(result));
    }

    @Test
    void testParallelSort() throws InterruptedException {
        List<Integer> input = Arrays.asList(10, 8, 6, 4, 2);
        Collection<Integer> result = quickSort.parallelSort(input, null);

        assertEquals(Arrays.asList(2, 4, 6, 8, 10), new ArrayList<>(result));
    }

    @Test
    void testEmptyList() {
        List<Integer> empty = Collections.emptyList();
        Collection<Integer> result = quickSort.sort(empty, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void testAlreadySortedList() {
        List<Integer> sorted = Arrays.asList(1, 2, 3, 4, 5);
        Collection<Integer> result = quickSort.sort(sorted, null);

        assertEquals(sorted, new ArrayList<>(result));
    }

    @Test
    void testComparatorIsUsed() {
        Comparator<Integer> mockComparator = Mockito.mock(Comparator.class);
        when(mockComparator.compare(anyInt(), anyInt())).thenReturn(0);

        quickSort.sort(Arrays.asList(1, 2, 3), mockComparator);

        verify(mockComparator, atLeastOnce()).compare(anyInt(), anyInt());
    }
}
