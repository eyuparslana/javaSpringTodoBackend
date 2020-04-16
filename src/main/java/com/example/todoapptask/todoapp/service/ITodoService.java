package com.example.todoapptask.todoapp.service;

import com.example.todoapptask.todoapp.model.Todo;
import com.example.todoapptask.todoapp.model.User;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface ITodoService {
    List<Todo> getTodoLists(String username);
    Todo createTodoList(Todo todo, String username);
    Todo deleteTodoList(String todoListId);
    Todo updateTodoList(String oldTodoListName, Todo todo, String username);
}
