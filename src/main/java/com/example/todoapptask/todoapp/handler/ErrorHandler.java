package com.example.todoapptask.todoapp.handler;

import com.example.todoapptask.todoapp.exception.*;
import com.example.todoapptask.todoapp.model.ApiResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(AccountAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleAccountAlreadyRegisteredException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .description("User already created!").build();
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .description("User not found!").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handleWrongPasswordException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .description("Invalid password").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(TodoListAlreadyExistsException.class)
    public ResponseEntity<Object> handleTodoListAlreadyExistsException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .description("Todo list already exists.").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(TodoListNotFoundException.class)
    public ResponseEntity<Object> handleTodoListNotFoundException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .description("Todo list not found.").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(TodoItemNotFoundException.class)
    public ResponseEntity<Object> handleTodoItemNotFoundException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .description("Todo item not found.").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(TodoItemAlreadyExistsException.class)
    public ResponseEntity<Object> handleTodoItemAlreadyExistsException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .description("Todo item already exists.").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}

