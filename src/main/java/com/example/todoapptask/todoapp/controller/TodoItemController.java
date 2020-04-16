package com.example.todoapptask.todoapp.controller;

import com.example.todoapptask.todoapp.helper.UserUtility;
import com.example.todoapptask.todoapp.model.ApiResponseBody;
import com.example.todoapptask.todoapp.model.TodoItem;
import com.example.todoapptask.todoapp.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/todoItems")
public class TodoItemController {

    private TodoItemService todoItemService;

    @Autowired
    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GetMapping("/{todoListId}")
    public ResponseEntity<ApiResponseBody> getTodoItems(HttpServletRequest request, @PathVariable String todoListId) throws AccountNotFoundException {

        String token = request.getHeaders("authorization").nextElement();
        String username = UserUtility.getUsernameFromToken(token);
        List<TodoItem> todoItems = todoItemService.getTodoItems(todoListId, username);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .data(todoItems)
                .build();

        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody> createTodoItem(HttpServletRequest request, @RequestBody TodoItem todoItem) throws AccountNotFoundException {
        String token = request.getHeaders("authorization").nextElement();
        String username = UserUtility.getUsernameFromToken(token);
        TodoItem newTodoItem = todoItemService.createTodoItem(todoItem, username);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.CREATED.value())
                .description("Todo Item Created!")
                .data(newTodoItem)
                .build();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PutMapping("/{oldTodoItemId}")
    public ResponseEntity<ApiResponseBody> updateTodoItem(@PathVariable String oldTodoItemId,
                                                          HttpServletRequest request,
                                                          @RequestBody TodoItem todoItem) throws AccountNotFoundException {
        String token = request.getHeaders("authorization").nextElement();
        String username = UserUtility.getUsernameFromToken(token);
        TodoItem updatedTodoItem = todoItemService.updateTodoItem(oldTodoItemId, todoItem, username);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .description("Todo item updated.")
                .data(updatedTodoItem)
                .build();
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{todoItemId}")
    public ResponseEntity<ApiResponseBody> deleteTodoItem(HttpServletRequest request,
                                                          @PathVariable String todoItemId) throws AccountNotFoundException {
        String token = request.getHeaders("authorization").nextElement();
        String username = UserUtility.getUsernameFromToken(token);
        TodoItem deletedTodoItem = todoItemService.deleteTodoItem(todoItemId, username);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .description("Todo item deleted.")
                .data(deletedTodoItem)
                .build();
        return ResponseEntity.ok(body);
    }
}
