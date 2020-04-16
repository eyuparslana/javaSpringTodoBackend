package com.example.todoapptask.todoapp.service;

import com.example.todoapptask.todoapp.model.TodoItem;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface ITodoItemService {
    List<TodoItem> getTodoItems(String todoListId, String username) throws AccountNotFoundException;
    List<TodoItem> getTodoItemsByStatus(String status, String todoListId, String username) throws AccountNotFoundException;
    TodoItem getTodoItemByName(String todoItemId, String username) throws AccountNotFoundException;
    TodoItem createTodoItem(TodoItem todoItem, String username) throws AccountNotFoundException;
    TodoItem updateTodoItem(String oldTodoItemName, TodoItem todoItem, String username) throws AccountNotFoundException;
    TodoItem deleteTodoItem(String todoItemId, String username) throws AccountNotFoundException;
}
