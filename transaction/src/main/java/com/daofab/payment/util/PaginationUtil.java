package com.daofab.payment.util;


import java.util.Collections;
import java.util.List;

/**
 * Utility class for performing pagination on a list of data.
 */
public class PaginationUtil {

    private PaginationUtil(){
    }

    /**
     * Retrieves a paginated subset of data from a list.
     *
     * @param data   The list of data to be paginated.
     * @param offset The starting index of the pagination.
     * @param limit  The maximum number of items per page.
     * @param <T>    The type of data in the list.
     * @return A paginated sublist of the data, or an empty list if no data is available for the requested page.
     */
    public static <T> List<T> getPage(List<T> data, int offset, int limit) {
        int startIndex =offset-1;
        int endIndex = Math.min(startIndex + limit, data.size());
        if (startIndex >= endIndex) {
            return Collections.emptyList(); // Return an empty list when no data is available
        }
        return data.subList(startIndex, endIndex);
    }
}
