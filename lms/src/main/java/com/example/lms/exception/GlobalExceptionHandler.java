package com.example.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomException> handleResourceNotFoundException(ResourceNotFoundException ex) {
        CustomException errorResponse = new CustomException(
                HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<CustomException> handleBookAlreadyBorrowedException(BookAlreadyBorrowedException ex) {
        CustomException errorResponse = new CustomException(
                HttpStatus.CONFLICT.value(), ex.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomException> handleGlobalException(Exception ex) {
        CustomException errorResponse = new CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
