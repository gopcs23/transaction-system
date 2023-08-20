package com.daofab.payment.service;

import com.daofab.payment.dto.InstallmentResponse;
import com.daofab.payment.dto.ParentTransactionRequest;
import com.daofab.payment.dto.ParentTransactionResponse;

import java.util.List;

public interface TransactionService {

    ParentTransactionResponse fetchParentTransactions(ParentTransactionRequest parentTransactionRequest);

    List<InstallmentResponse> fetchInstallments(Long parentId);
}
