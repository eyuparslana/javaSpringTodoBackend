package com.example.todoapptask.todoapp.service;

import com.example.todoapptask.todoapp.model.User;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface IUserService {
    List<User> getUsers();
    User getUser(String username) throws AccountNotFoundException;
    User login(String username, String password) throws AccountNotFoundException;
    User register(User user) throws AccountNotFoundException;
    User updateUser(User user) throws AccountNotFoundException;
}
