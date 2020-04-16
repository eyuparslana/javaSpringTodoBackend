package com.example.todoapptask.todoapp.exception;

public class TodoListNotFoundException extends RuntimeException {
    public TodoListNotFoundException(String message) {
        super(message);
    }

    public TodoListNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
