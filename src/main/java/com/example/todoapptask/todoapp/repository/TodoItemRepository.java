package com.example.todoapptask.todoapp.repository;

import com.example.todoapptask.todoapp.model.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoItemRepository extends MongoRepository<TodoItem, String> {
    Optional<TodoItem> findByNameAndUserId(String todoItemName, String username);
    List<TodoItem> findByStatusAndUserIdAndTodoListId(String status, String username, String todoListId);
    List<TodoItem> findByTodoListIdAndUserId(String todoListId, String username);
    List<TodoItem> findByDependedTodoItem(String todoItemId);
}
