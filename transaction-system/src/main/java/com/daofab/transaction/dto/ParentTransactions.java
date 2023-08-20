package com.daofab.transaction.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class ParentTransactions {
    private int parentId;
    private String sender;
    private String receiver;
    private int totalAmount;
    private int totalAmountPaid;
}
