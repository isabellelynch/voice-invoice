package dev.isabellelynch.invoice.parser;

import java.util.List;

/**
 * Finds which customer a spoken name refers to.
 *
 * TODO (Isabelle):
 *  1. Build a Map<String, List<CustomerRef>> keyed by normalised name (lower-case, single spaces).
 *     Exact hit with one customer -> match. More than one -> CUSTOMER_AMBIGUOUS.
 *  2. No exact hit: compute editDistance to every name and keep those within 2.
 *     Exactly one -> propose it (the user still confirms). None -> CUSTOMER_NOT_FOUND.
 *     More than one -> CUSTOMER_AMBIGUOUS (try "Jon Murphy" with the seed data).
 *
 * You decide the return type. A small record such as Match(CustomerRef customer, Issue issue)
 * where one of the two is null works well.
 */
public final class CustomerMatcher {

    private final List<CustomerRef> customers;

    public CustomerMatcher(List<CustomerRef> customers) {
        this.customers = List.copyOf(customers);
    }

    /**
     * TODO (Isabelle): Levenshtein edit distance using two rolling int[] rows instead of a full table.
     * O(m x n) time, O(n) space. A classic interview question, so be able to draw the table on a whiteboard.
     */
    static int editDistance(String a, String b) {
        throw new UnsupportedOperationException("TODO: implement CustomerMatcher.editDistance");
    }
}
