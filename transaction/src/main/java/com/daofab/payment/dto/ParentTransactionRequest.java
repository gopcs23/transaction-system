package com.daofab.payment.dto;

import com.daofab.payment.constant.PaginationConstant;
import lombok.Data;

@Data
public class ParentTransactionRequest {
    private int offset;

    private int limit;

    private String sort;

    private String order;

    public ParentTransactionRequest() {
        this.offset = 1;
        this.limit = 2;
        this.sort = PaginationConstant.ID;
        this.order = PaginationConstant.ORDER_ASC;
    }
}
