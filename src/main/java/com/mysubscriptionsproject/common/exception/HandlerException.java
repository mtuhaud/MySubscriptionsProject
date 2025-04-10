package com.mysubscriptionsproject.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorBuilder> handleEntityNotFoundException(EntityNotFoundException exception) {
        ErrorBuilder error = ErrorBuilder.builder()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(SubscriptionException.class)
    public ResponseEntity<ErrorBuilder> handleSubscriptionException(SubscriptionException exception) {
        ErrorBuilder error = ErrorBuilder.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }


}
