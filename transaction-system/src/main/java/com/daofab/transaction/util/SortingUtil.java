package com.daofab.transaction.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * Utility class for performing sorting on a list of data.
 */
public class SortingUtil {

    private SortingUtil(){
    }

    /**
     * Sorts a list of data using the provided comparator and sorting order.
     *
     * @param data       The list of data to be sorted.
     * @param comparator The comparator used for sorting.
     * @param isAsc      A flag indicating whether the sorting order is ascending (true) or descending (false).
     * @param <T>        The type of data in the list.
     * @return The sorted list of data.
     */
    public static <T> List<T> sortBy(List<T> data, Comparator<T> comparator, boolean isAsc) {
        if (isAsc) {
            Collections.sort(data, comparator);
        } else {
            Collections.sort(data, comparator.reversed());
        }
        return data;
    }
}
