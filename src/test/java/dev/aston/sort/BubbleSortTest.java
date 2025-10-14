package dev.aston.sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BubbleSortTest {

    private BubbleSort<Integer> bubbleSort;

    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort<>();
    }

    @Test
    void testSortWithNaturalOrder() {
        List<Integer> numbers = Arrays.asList(5, 2, 9, 1, 3);
        Collection<Integer> result = bubbleSort.sort(numbers, null);

        assertEquals(Arrays.asList(1, 2, 3, 5, 9), new ArrayList<>(result));
    }

    @Test
    void testSortWithComparator() {
        Comparator<Integer> reversed = Comparator.reverseOrder();
        List<Integer> numbers = Arrays.asList(1, 3, 2, 5, 4);

        Collection<Integer> result = bubbleSort.sort(numbers, reversed);

        assertEquals(Arrays.asList(5, 4, 3, 2, 1), new ArrayList<>(result));
    }

    @Test
    void testSortEmptyList() {
        List<Integer> empty = Collections.emptyList();
        Collection<Integer> result = bubbleSort.sort(empty, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void testParallelSort() throws InterruptedException {
        List<Integer> numbers = Arrays.asList(10, 4, 8, 1, 7);
        Collection<Integer> result = bubbleSort.parallelSort(numbers, null);

        assertEquals(Arrays.asList(1, 4, 7, 8, 10), new ArrayList<>(result));
    }

    @Test
    void testComparatorIsUsed() {
        Comparator<Integer> mockComparator = Mockito.mock(Comparator.class);
        when(mockComparator.compare(anyInt(), anyInt())).thenReturn(0);

        bubbleSort.sort(Arrays.asList(1, 2, 3), mockComparator);

        verify(mockComparator, atLeastOnce()).compare(anyInt(), anyInt());
    }
}
