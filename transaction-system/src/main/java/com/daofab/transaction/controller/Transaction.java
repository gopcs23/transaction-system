package com.daofab.transaction.controller;

import com.daofab.transaction.dto.InstallmentResponse;
import com.daofab.transaction.dto.ParentTransactionRequest;
import com.daofab.transaction.dto.ParentTransactionResponse;
import com.daofab.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/transactions")
public class Transaction {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/parent-transactions")
    public ResponseEntity<ParentTransactionResponse> fetchParentTransactions(@ModelAttribute ParentTransactionRequest parentTransactionRequest) {
        return new ResponseEntity<>(transactionService.fetchParentTransactions(parentTransactionRequest),
                HttpStatus.OK);
    }

    @GetMapping("/parent-transactions/{parentId}/installments")
    public ResponseEntity<List<InstallmentResponse>> fetchInstallments(@PathVariable("parentId") Long parentId){
        return new ResponseEntity<>(transactionService.fetchInstallments(parentId),
                HttpStatus.OK);
    }

}
