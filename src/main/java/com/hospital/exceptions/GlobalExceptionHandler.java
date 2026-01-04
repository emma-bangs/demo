package com.hospital.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePasswordException.class)
    public ResponseEntity<String> handleDuplicatePassword(DuplicatePasswordException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


}