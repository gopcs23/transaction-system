package com.daofab.transaction.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class BusinessException extends RuntimeException{
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String message;
    private final String error;
    private final String path;
}
