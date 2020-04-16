package com.example.todoapptask.todoapp.exception;

public class TodoItemAlreadyExistsException extends RuntimeException {
    public TodoItemAlreadyExistsException(String message) {
        super(message);
    }

    public TodoItemAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
