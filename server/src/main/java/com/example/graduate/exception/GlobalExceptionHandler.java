package com.example.graduate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý InvalidCredentialsException
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        // Trả về mã lỗi HTTP 401 (Unauthorized) kèm thông báo lỗi
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED); // Mã lỗi 401
    }

    // Xử lý các lỗi không xác định khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR); // Mã lỗi 500
    }
}
