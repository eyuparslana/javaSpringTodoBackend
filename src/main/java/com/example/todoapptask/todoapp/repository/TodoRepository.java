package com.example.todoapptask.todoapp.repository;

import com.example.todoapptask.todoapp.model.Todo;
import com.example.todoapptask.todoapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
    List<Todo> findByUserId(String userId);
    Optional<Todo> findByTodoListName(String todoListName);
    Optional<Todo> findByUserIdAndTodoListName(String userId, String todoListName);
}
