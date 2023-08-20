package com.daofab.payment.repo.dao;

import lombok.Data;
import java.util.List;

/**
 * Represents a child installment transaction DTO.
 * This class encapsulates information about an installment payment, including its ID,
 * parent ID, paid amount.
 */
@Data
public class Child {
    private List<Child> data;
    private int id;
    private int parentId;
    private int paidAmount;
}
