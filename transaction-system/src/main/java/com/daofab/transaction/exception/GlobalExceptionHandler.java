package com.daofab.transaction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomBusinessException(BusinessException ex) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder().timestamp(ex.getTimestamp()).status(ex.getStatus())
                .message(ex.getMessage()).error(ex.getError()).path(ex.getPath()).build();
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

}
