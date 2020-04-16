package com.example.todoapptask.todoapp.exception;

public class TodoItemNotFoundException extends RuntimeException {
    public TodoItemNotFoundException(String message) {
        super(message);
    }

    public TodoItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
