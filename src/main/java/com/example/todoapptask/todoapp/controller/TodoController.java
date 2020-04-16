package com.example.todoapptask.todoapp.controller;

import com.example.todoapptask.todoapp.helper.UserUtility;
import com.example.todoapptask.todoapp.model.ApiResponseBody;
import com.example.todoapptask.todoapp.model.Todo;
import com.example.todoapptask.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/todo")
@RestController
public class TodoController {


    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseBody> getAllTodoList(HttpServletRequest request) {
        String token = request.getHeaders("authorization").nextElement();
        String username = UserUtility.getUsernameFromToken(token);
        List<Todo> todoLists = todoService.getTodoLists(username);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .data(todoLists)
                .build();
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody> createNewTodo(@RequestBody Todo todo, HttpServletRequest request) {
        String token = request.getHeaders("authorization").nextElement();
        String username = UserUtility.getUsernameFromToken(token);
        Todo todoList = todoService.createTodoList(todo, username);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.CREATED.value())
                .description("Todo Created.")
                .data(todoList)
                .build();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponseBody> updateTodoList(@RequestParam String oldTodoListName,
                                                          @RequestBody Todo todo,
                                                          HttpServletRequest request) {
        String token = request.getHeaders("authorization").nextElement();
        String username = UserUtility.getUsernameFromToken(token);
        Todo todoList = todoService.updateTodoList(oldTodoListName, todo, username);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .description("Todo updated.")
                .data(todoList)
                .build();

        return ResponseEntity.ok(body);
    }

    @DeleteMapping("{todoListId}")
    public ResponseEntity<ApiResponseBody> deleteTodoList(@PathVariable String todoListId) {
        Todo todo = todoService.deleteTodoList(todoListId);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .description("Todo list deleted.")
                .data(todo)
                .build();
        return ResponseEntity.ok(body);
    }
}
