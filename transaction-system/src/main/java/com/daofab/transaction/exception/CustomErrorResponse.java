package com.daofab.transaction.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class CustomErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String error;
    private final String path;

}
