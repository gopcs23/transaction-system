package com.daofab.transaction.repo.dao;

import lombok.Data;

import java.util.List;

/**
 * Represents a parent transaction DTO.
 * This class encapsulates information about a parent transaction, including its ID,
 * sender, receiver, total amount.
 */
@Data
public class Parent {
    private List<Parent> data;
    private int id;
    private String sender;
    private String receiver;
    private int totalAmount;
}
