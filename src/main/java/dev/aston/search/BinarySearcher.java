package dev.aston.search;

import java.util.Collection;
import java.util.List;

public class BinarySearcher<T extends Comparable<T>> implements Searcher<T> {

    @Override
    public int search(Collection<T> collection, T targetElement) {
        List<T> list = collection.stream().toList();
        int left = 0;
        int right = collection.size() - 1;
        int foundIndex = -1;

        while (left <= right) {
            int midIndex = (left + right) / 2;
            T midElement = list.get(midIndex);
            int compareRes = targetElement.compareTo(midElement);

            if (compareRes == 0) {
                foundIndex = midIndex;
                break;
            }
            else if (compareRes < 0) {
                right = midIndex - 1;
            }
            else {
                left = midIndex + 1;
            }
        }

        return foundIndex;
    }
}
