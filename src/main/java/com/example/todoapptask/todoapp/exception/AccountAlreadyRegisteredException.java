package com.example.todoapptask.todoapp.exception;

public class AccountAlreadyRegisteredException extends RuntimeException {

    public AccountAlreadyRegisteredException(String message) {
        super(message);
    }

    public AccountAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
