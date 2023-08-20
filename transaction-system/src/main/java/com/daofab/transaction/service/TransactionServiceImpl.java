package com.daofab.transaction.service;

import com.daofab.transaction.util.LinkUtil;
import com.daofab.transaction.constant.PaginationConstant;
import com.daofab.transaction.dto.InstallmentResponse;
import com.daofab.transaction.dto.Link;
import com.daofab.transaction.dto.ParentTransactionRequest;
import com.daofab.transaction.dto.ParentTransactionResponse;
import com.daofab.transaction.dto.ParentTransactions;
import com.daofab.transaction.exception.BusinessException;
import com.daofab.transaction.repo.TransactionRepository;
import com.daofab.transaction.repo.dao.Child;
import com.daofab.transaction.repo.dao.Pageable;
import com.daofab.transaction.repo.dao.Parent;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ParentTransactionResponse fetchParentTransactions(ParentTransactionRequest parentTransactionRequest) {
        // Step 1: Define sorting parameters default sort by is based on ID and can be extended for sender and other fields
        Comparator<Parent> sortBy = Comparator.comparing(Parent::getId);
        if(parentTransactionRequest.getSort().equals(PaginationConstant.SENDER)) {
            sortBy =  Comparator.comparing(Parent::getSender);
        }
        boolean sortOrder = parentTransactionRequest.getOrder().equalsIgnoreCase(PaginationConstant.ORDER_ASC);

        // Step 2: Create a pageable object for sorting and pagination
        Pageable<Parent> pageable = Pageable.<Parent>builder()
                .limit(parentTransactionRequest.getLimit())
                .offset(parentTransactionRequest.getOffset())
                .comparator(sortBy)
                .isAsc(sortOrder)
                .build();

        // Step 3: Fetch parent data with sorting and pagination
        List<Parent> parentList = transactionRepository.findAllParentData(pageable);

        // Step 4: Fetch total paid amount for each parent by passing the relevant parent IDs
        List<Integer> parentIds = parentList.parallelStream().map(Parent::getId).collect(Collectors.toList());
        Map<Integer, Integer> parentIdToTotalPaidAmount = transactionRepository.fetchTotalPaidAmountForParents(parentIds);

        // Step 5: Create ParentTransactions objects using collected data
        List<ParentTransactions> parentTransactionList = parentList.stream().map(parent->
                ParentTransactions.builder()
                    .parentId(parent.getId())
                    .sender(parent.getSender())
                    .receiver(parent.getReceiver())
                    .totalAmount(parent.getTotalAmount())
                    .totalAmountPaid(Optional.ofNullable(parentIdToTotalPaidAmount.get(parent.getId())).orElse(0))
                    .build())
                .collect(Collectors.toList());

        // Step 6: Fetch total no of transaction
        int totalTransaction = transactionRepository.fetchTotalParentTransaction();

        // Step 7: Generate href for page navigation
        List<Link> links = LinkUtil.createPaginationLinks(parentTransactionRequest.getOffset(), parentTransactionRequest.getLimit(),
                parentTransactionRequest.getSort(), parentTransactionRequest.getOrder(),totalTransaction);

        // Step 8: Return a response object with the created ParentTransactions list with total count
        return ParentTransactionResponse.builder().parentTransactions(parentTransactionList).totalTransactions(totalTransaction).links(links).build();
    }

    @Override
    public List<InstallmentResponse> fetchInstallments(Long parentId) {
        // Step 1: Retrieve the parent using the provided parent ID
        Optional<Parent> parentOptional = transactionRepository.fetchParentById(parentId);
        // If parent is not found, throw an exception
        if (!parentOptional.isPresent()) {
            throw BusinessException.builder().status(HttpStatus.NOT_FOUND.value())
                    .message("Parent does not exist with id: " + parentId)
                    .error("Not Found")
                    .build();
        }
        // Step 2: Retrieve child transactions for the parent
        List<Child> childList = transactionRepository.findChildTransactionForParentId(parentId);

        // Step 3: Map child transactions and parent data to InstallmentResponse
        return childList.stream().map(child ->
                InstallmentResponse.builder().childId(child.getId())
                    .paidAmount(child.getPaidAmount())
                    .sender(parentOptional.get().getSender())
                    .receiver(parentOptional.get().getReceiver())
                    .totalAmount(parentOptional.get().getTotalAmount())
                    .build())
                .collect(Collectors.toList());
    }
}
