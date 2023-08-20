package com.daofab.transaction.service;

import com.daofab.transaction.dto.InstallmentResponse;
import com.daofab.transaction.dto.ParentTransactionRequest;
import com.daofab.transaction.dto.ParentTransactionResponse;

import java.util.List;

public interface TransactionService {

    ParentTransactionResponse fetchParentTransactions(ParentTransactionRequest parentTransactionRequest);

    List<InstallmentResponse> fetchInstallments(Long parentId);
}
