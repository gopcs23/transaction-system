package com.daofab.payment.repo;

import com.daofab.payment.util.JsonParserUtil;
import com.daofab.payment.util.PaginationUtil;
import com.daofab.payment.util.SortingUtil;
import com.daofab.payment.repo.dao.Child;
import com.daofab.payment.repo.dao.Pageable;
import com.daofab.payment.repo.dao.Parent;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation that interacts with data repositories for transaction-related operations.
 */
@Component
public class TransactionRepositoryService implements TransactionRepository {
    /**
     * Retrieves a list of all parent data.
     *
     * @return A list of Parent objects representing parent data.
     */
    @Override
    public List<Parent> findAllParentData() {
        return JsonParserUtil.getParentData();
    }

    /**
     * Retrieves a paginated and sorted list of parent data based on the provided Pageable.
     *
     * @param pageable The Pageable object specifying pagination and sorting parameters.
     * @return A paginated and sorted list of Parent objects.
     */
    @Override
    public List<Parent> findAllParentData(Pageable pageable) {
        List<Parent> parents = PaginationUtil.getPage(findAllParentData(), pageable.getOffset(), pageable.getLimit());
        return SortingUtil.sortBy(parents, pageable.getComparator(), pageable.isAsc());
    }

    /**
     * Retrieves a mapping of parent IDs to their total paid amounts.
     *
     * @param parentId The list of parent IDs for which to calculate total paid amounts.
     * @return A map containing parent IDs as keys and their corresponding total paid amounts as values.
     */
    @Override
    public Map<Integer, Integer> fetchTotalPaidAmountForParents(List<Integer> parentId) {
        return JsonParserUtil.getChildData().stream()
                .filter(child-> parentId.contains(child.getParentId()))
                .collect(Collectors.groupingBy(Child::getParentId,
                        Collectors.summingInt(Child::getPaidAmount)));
    }

    /**
     * Retrieves the total number of parent transactions.
     *
     * @return The count of parent transactions.
     */
    @Override
    public int fetchTotalParentTransaction() {
        return findAllParentData().size();
    }

    /**
     * Retrieves a parent by its ID.
     *
     * @param id The ID of the parent to retrieve.
     * @return An Optional containing the retrieved Parent object, if found.
     */
    @Override
    public Optional<Parent> fetchParentById(Long id) {
        return findAllParentData().stream()
                .filter(parent-> parent.getId() == id)
                .findAny();
    }

    /**
     * Retrieves a list of child transactions for a given parent ID.
     *
     * @param parentId The ID of the parent for which to retrieve child transactions.
     * @return A list of Child objects representing child transactions for the parent.
     */
    @Override
    public List<Child> findChildTransactionForParentId(Long parentId) {
        List<Child> childTransactions = JsonParserUtil.getChildData();
        return childTransactions.parallelStream()
                .filter(x-> x.getParentId() == parentId)
                .sorted(Comparator.comparing(Child::getId))
                .collect(Collectors.toList());

    }
}
