package com.example.todoapptask.todoapp.exception;

public class TodoListAlreadyExistsException extends RuntimeException {
    public TodoListAlreadyExistsException(String message) {
        super(message);
    }

    public TodoListAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
