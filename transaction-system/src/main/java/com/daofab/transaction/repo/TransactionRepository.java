package com.daofab.transaction.repo;

import com.daofab.transaction.repo.dao.Child;
import com.daofab.transaction.repo.dao.Pageable;
import com.daofab.transaction.repo.dao.Parent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This is the repository class responsible for fetching data.
 * Currently, the data source is a JSON file.
 * In the future, this class can be enhanced to fetch data from a database.
 *
 * Note: The implementation may change to accommodate different data sources.
 */
public interface TransactionRepository {

    List<Parent> findAllParentData();

    List<Parent> findAllParentData(Pageable pageable);

    Map<Integer, Integer> fetchTotalPaidAmountForParents(List<Integer> parentId);

    int fetchTotalParentTransaction();

    Optional<Parent> fetchParentById(Long id);

    List<Child> findChildTransactionForParentId(Long parentId);

}
