package com.example.todoapptask.todoapp.service;

import com.example.todoapptask.todoapp.exception.TodoItemAlreadyExistsException;
import com.example.todoapptask.todoapp.exception.TodoItemNotFoundException;
import com.example.todoapptask.todoapp.helper.TodoItemUtility;
import com.example.todoapptask.todoapp.model.TodoItem;
import com.example.todoapptask.todoapp.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService implements ITodoItemService {


    private TodoItemRepository todoItemRepository;
    private UserService userService;

    @Autowired
    public TodoItemService(TodoItemRepository todoItemRepository, UserService userService) {
        this.todoItemRepository = todoItemRepository;
        this.userService = userService;
    }

    @Override
    public List<TodoItem> getTodoItems(String todoListId, String username) throws AccountNotFoundException {
        validateTodoUser(username);
        return todoItemRepository.findByTodoListIdAndUserId(todoListId, username);
    }

    @Override
    public List<TodoItem> getTodoItemsByStatus(String status,String todoListId, String username) throws AccountNotFoundException {
        validateTodoUser(username);
        return todoItemRepository.findByStatusAndUserIdAndTodoListId(status, username, todoListId);
    }

    @Override
    public TodoItem getTodoItemByName(String todoItemName, String username) throws AccountNotFoundException {
        validateTodoUser(username);
        Optional<TodoItem> optionalTodoItem = todoItemRepository
                .findByNameAndUserId(todoItemName, username);
        if (!optionalTodoItem.isPresent()) {
            throw new TodoItemNotFoundException("Todo item not found!");
        }

        return optionalTodoItem.get();
    }

    @Override
    public TodoItem createTodoItem(TodoItem todoItem, String username) throws AccountNotFoundException {
        validateTodoUser(username);
        String todoItemName = todoItem.getName();
        Optional<TodoItem> optionalTodoItem = todoItemRepository
                .findByNameAndUserId(todoItemName, username);
        if (optionalTodoItem.isPresent()) {
            throw new TodoItemAlreadyExistsException("Todo item already exists!");
        }

        todoItem.setUserId(username);
        todoItemRepository.save(todoItem);
        return todoItem;
    }

    @Override
    public TodoItem updateTodoItem(String oldTodoItemId, TodoItem todoItem, String username) throws AccountNotFoundException {
        validateTodoUser(username);
        Optional<TodoItem> optionalOldTodoItem = todoItemRepository.findById(oldTodoItemId);
        if (!optionalOldTodoItem.isPresent()) {
            throw new TodoItemNotFoundException("Todo item not found!");
        }
        String newTodoItemName = todoItem.getName();

        Optional<TodoItem> optionalNewTodoItem = todoItemRepository
                .findByNameAndUserId(newTodoItemName, username);
        if (!optionalNewTodoItem.isPresent()) {
            throw new TodoItemAlreadyExistsException("Todo item already exists!");
        }
        if (newTodoItemName.isEmpty()) {
            todoItem.setName(optionalOldTodoItem.get().getName());
        }

        TodoItem oldTodoItem = optionalOldTodoItem.get();
        oldTodoItem.setName(todoItem.getName());
        oldTodoItem.setDescription(todoItem.getDescription());
        oldTodoItem.setDeadline(todoItem.getDeadline());
        oldTodoItem.setStatus(todoItem.getStatus());
        oldTodoItem.setTodoListId(todoItem.getTodoListId());
        oldTodoItem.setUserId(todoItem.getUserId());
        oldTodoItem.setDependedTodoItem(todoItem.getDependedTodoItem());
        return todoItemRepository.save(oldTodoItem);
    }

    @Override
    public TodoItem deleteTodoItem(String todoItemId, String username) throws AccountNotFoundException {
        validateTodoUser(username);

        Optional<TodoItem> optionalOldTodoItem = todoItemRepository.findById(todoItemId);

        if (!optionalOldTodoItem.isPresent()) {
            throw new TodoItemNotFoundException("Todo item not found!");
        }

        TodoItem storedTodoItem = optionalOldTodoItem.get();

        List<TodoItem> dependedTodoItems = todoItemRepository
                .findByDependedTodoItem(storedTodoItem.getId());

        for (TodoItem dependedTodoItem : dependedTodoItems) {
            todoItemRepository.delete(dependedTodoItem);
        }

        todoItemRepository.delete(storedTodoItem);
        return storedTodoItem;
    }

    private void validateTodoUser(String username) throws AccountNotFoundException {
        userService.getUser(username);
    }
}
