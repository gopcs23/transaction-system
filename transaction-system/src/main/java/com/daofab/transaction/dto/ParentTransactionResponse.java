package com.daofab.transaction.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class ParentTransactionResponse {
    List<ParentTransactions> parentTransactions;
    int totalTransactions;
    List<Link> links;


}
