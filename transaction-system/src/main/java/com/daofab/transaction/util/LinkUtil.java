package com.daofab.transaction.util;

import com.daofab.transaction.dto.Link;
import com.sun.research.ws.wadl.HTTPMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating pagination links based on offset, limit, sorting, and order parameters.
 */
public class LinkUtil {
    private LinkUtil(){
    }

    // Hard coding the uri for now but this needs to be passed in a dynamic way
    private static final String HOST = "http://localhost:8080";
    private static final String URL = "/v1/transaction/parent-transactions?";
    private static final String AMP = "&";

    /**
     * Creates a list of pagination links for a given set of parameters.
     *
     * @param offset           The current offset.
     * @param limit            The limit per page.
     * @param sortBy           The field to sort by.
     * @param order            The sorting order.
     * @param totalTransaction The total number of transactions.
     * @return A list of Link objects representing pagination links.
     */
    public static List<Link> createPaginationLinks(int offset, int limit, String sortBy, String order, int totalTransaction) {
        int totalPages =  (totalTransaction / limit) +1;
        int currentPage = (offset / limit) + 1;
        List<Link> links = new ArrayList<>();
        if (currentPage > 1) {
            links.add(Link.builder().href(createHref(1, limit, sortBy, order)).rel("first").method(HTTPMethods.GET.value()).build());
            links.add(Link.builder().href(createHref(offset-limit, limit, sortBy, order)).rel("prev").method(HTTPMethods.GET.value()).build());
        }
        if (currentPage < totalPages) {
            links.add(Link.builder().href(createHref(offset+limit, limit, sortBy, order)).rel("next").method(HTTPMethods.GET.value()).build());
            links.add(Link.builder().href(createHref(totalPages, limit, sortBy, order)).rel("last").method(HTTPMethods.GET.value()).build());
        }
        return links;
    }
    private static String createHref(int offset, int limit, String sortBy, String order) {
        StringBuilder hrefBuilder = new StringBuilder();
        hrefBuilder.append(HOST).append(URL)
                .append("offset=").append(offset)
                .append(AMP).append("limit=").append(limit)
                .append(AMP).append("sort=").append(sortBy)
                .append(AMP).append("order=").append(order);
        return hrefBuilder.toString();
    }
}
