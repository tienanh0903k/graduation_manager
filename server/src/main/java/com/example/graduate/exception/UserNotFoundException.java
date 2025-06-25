package com.example.graduate.exception;

public class UserNotFoundException extends RuntimeException {
      public UserNotFoundException(String message) {
        super(message);
    }
}
