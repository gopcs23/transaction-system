package com.daofab.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstallmentResponse {
    private int childId;
    private String sender;
    private String receiver;
    private double totalAmount;
    private double paidAmount;
}
