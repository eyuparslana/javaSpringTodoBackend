package com.example.todoapptask.todoapp.service;

import com.example.todoapptask.todoapp.exception.TodoListAlreadyExistsException;
import com.example.todoapptask.todoapp.exception.TodoListNotFoundException;
import com.example.todoapptask.todoapp.model.Todo;
import com.example.todoapptask.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService implements ITodoService {


    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getTodoLists(String username) {
        return todoRepository.findByUserId(username);
    }

    @Override
    public Todo createTodoList(Todo todo, String username) {
        String todoListName = todo.getTodoListName();
        Optional<Todo> optionalTodo = todoRepository.findByTodoListName(todoListName);
        if (optionalTodo.isPresent()) {
            throw new TodoListAlreadyExistsException("Todo list already exists.");
        }

        todo.setUserId(username);
        todoRepository.save(todo);
        return todo;
    }

    @Override
    public Todo deleteTodoList(String todoListId) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoListId);
        if (!optionalTodo.isPresent()) {
            throw new TodoListNotFoundException("Todo list not found.");
        }
        Todo deletedTodo = optionalTodo.get();

        todoRepository.delete(deletedTodo);
        return deletedTodo;
    }

    @Override
    public Todo updateTodoList(String oldTodoListId, Todo todo, String username) {
        Optional<Todo> optionalOldTodo = todoRepository.findById(oldTodoListId);
        if (!optionalOldTodo.isPresent()) {
            throw new TodoListNotFoundException("Todo list not found.");
        }

        String newTodoListName = todo.getTodoListName();
        Optional<Todo> optionalNewTodo = todoRepository.findByUserIdAndTodoListName(username, newTodoListName);
        if (optionalNewTodo.isPresent()) {
            throw new TodoListAlreadyExistsException("Todo list already exists.");
        }

        Todo storedTodo = optionalOldTodo.get();
        if (newTodoListName.isEmpty()) {
            newTodoListName = storedTodo.getTodoListName();
        }

        storedTodo.setTodoListName(newTodoListName);

        todoRepository.save(storedTodo);
        return storedTodo;
    }
}
