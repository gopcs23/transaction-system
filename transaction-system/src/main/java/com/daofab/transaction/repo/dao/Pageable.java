package com.daofab.transaction.repo.dao;

import lombok.Builder;
import lombok.Data;

import java.util.Comparator;

@Data
@Builder
public class Pageable<T> {
    Integer offset;
    Integer limit;
    Comparator<T> comparator;
    boolean isAsc;
}
