package com.daofab.transaction;

import com.daofab.transaction.constant.PaginationConstant;
import com.daofab.transaction.dto.InstallmentResponse;
import com.daofab.transaction.dto.ParentTransactionRequest;
import com.daofab.transaction.dto.ParentTransactionResponse;
import com.daofab.transaction.dto.ParentTransactions;
import com.daofab.transaction.exception.BusinessException;
import com.daofab.transaction.repo.TransactionRepository;
import com.daofab.transaction.repo.TransactionRepositoryService;
import com.daofab.transaction.service.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TestTransactionServiceImpl {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
        TransactionRepository transactionRepository = new TransactionRepositoryService();
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    void testFetchParentTransactions() {
        // Perform test without passing pagination param
        ParentTransactionRequest parentTransactionRequest = new ParentTransactionRequest();
        ParentTransactionResponse parentTransactionResponse = transactionService.fetchParentTransactions(parentTransactionRequest);

        // Assert the size of the returned list
        Assertions.assertEquals(2, parentTransactionResponse.getParentTransactions().size());

        // Assert the total number of transactions
        Assertions.assertEquals(7, parentTransactionResponse.getTotalTransactions());

        // Check if IDs are ordered in ascending order
        int previousId = Integer.MIN_VALUE;
        for (ParentTransactions transaction : parentTransactionResponse.getParentTransactions()) {
            int currentId = transaction.getParentId();
            Assertions.assertTrue(currentId >= previousId, "IDs are not in ascending order");
            previousId = currentId;
        }
    }

    @Test
    void testFetchParentTransactionsDescending() {
        ParentTransactionRequest parentTransactionRequest = mockparentTransactionRequest(1,2,PaginationConstant.ID, PaginationConstant.ORDER_DESC);
        ParentTransactionResponse parentTransactionResponse = transactionService.fetchParentTransactions(parentTransactionRequest);

        // Assert the size of the returned list
        Assertions.assertEquals(2, parentTransactionResponse.getParentTransactions().size());
        // Assert the total number of transactions
        Assertions.assertEquals(7, parentTransactionResponse.getTotalTransactions());
        // Check if IDs are ordered in ascending order
        int previousId = Integer.MAX_VALUE;
        for (ParentTransactions transaction : parentTransactionResponse.getParentTransactions()) {
            int currentId = transaction.getParentId();
            Assertions.assertTrue(currentId <= previousId, "IDs are not in descending order");
            previousId = currentId;
        }
    }

    @Test
    void testFetchPaginatedParentTransactions() {
        // Perform pagination with page size 2 and offset 2
        ParentTransactionRequest parentTransactionRequest = mockparentTransactionRequest(3,2,PaginationConstant.ID, PaginationConstant.ORDER_ASC);
        ParentTransactionResponse response = transactionService.fetchParentTransactions(parentTransactionRequest);
        Assertions.assertEquals(mockParentTransactions(), response.getParentTransactions());
    }

    @Test
    void testPaginationLastPageWithEmptyResults() {
        // Perform pagination with page size 2 and offset 8
        ParentTransactionRequest parentTransactionRequest = mockparentTransactionRequest(8,2, PaginationConstant.ID, PaginationConstant.ORDER_DESC);

        ParentTransactionResponse response = transactionService.fetchParentTransactions(parentTransactionRequest);
        Assertions.assertEquals(Collections.emptyList(), response.getParentTransactions());
    }


    @Test
    void testFetchInstallmentWithValidParentId() {
        List<InstallmentResponse> installmentResponses = transactionService.fetchInstallments(1L);
        Assertions.assertEquals(3, installmentResponses.size());
        Assertions.assertEquals(mockInstallmentResponse(), installmentResponses);
    }

    @Test
    void testFetchInstallmentWithInValidParentId() {
        //List<InstallmentResponse> installmentResponses = ;
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> transactionService.fetchInstallments(9L));
        Assertions.assertEquals("Parent does not exist with id: 9", exception.getMessage());
        Assertions.assertEquals("Not Found", exception.getError());
        Assertions.assertEquals(404, exception.getStatus());
    }

    private List<ParentTransactions> mockParentTransactions() {
        List<ParentTransactions> mockTransactions = new ArrayList<>();
        mockTransactions.add(ParentTransactions.builder().parentId(3).sender("XYZ").receiver("MNP").totalAmount(300).totalAmountPaid(260).build());
        mockTransactions.add(ParentTransactions.builder().parentId(4).sender("ABC").receiver("MNP").totalAmount(1000).totalAmountPaid(900).build());
        return mockTransactions;
    }

    private List<InstallmentResponse> mockInstallmentResponse() {
        List<InstallmentResponse> mockTransactions = new ArrayList<>();
        mockTransactions.add(InstallmentResponse.builder().childId(1).sender("ABC").receiver("XYZ").totalAmount(200).paidAmount(10).build());
        mockTransactions.add(InstallmentResponse.builder().childId(2).sender("ABC").receiver("XYZ").totalAmount(200).paidAmount(50).build());
        mockTransactions.add(InstallmentResponse.builder().childId(3).sender("ABC").receiver("XYZ").totalAmount(200).paidAmount(40).build());
        return mockTransactions;
    }

    private ParentTransactionRequest mockparentTransactionRequest(int offset, int limit, String sort, String order) {
        ParentTransactionRequest parentTransactionRequest = new ParentTransactionRequest();
        parentTransactionRequest.setOffset(offset);
        parentTransactionRequest.setLimit(limit);
        parentTransactionRequest.setSort(sort);
        parentTransactionRequest.setOrder(order);
        return parentTransactionRequest;
    }
}
