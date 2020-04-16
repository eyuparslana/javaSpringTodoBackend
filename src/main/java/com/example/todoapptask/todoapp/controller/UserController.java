package com.example.todoapptask.todoapp.controller;

import com.example.todoapptask.todoapp.model.ApiResponseBody;
import com.example.todoapptask.todoapp.model.User;
import com.example.todoapptask.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseBody> login(@RequestBody User user) throws AccountNotFoundException {

        String username = user.getUsername();
        String password = user.getPassword();

        User loginedUser = userService.login(username, password);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .description("Login Success")
                .data(loginedUser)
                .build();

        return ResponseEntity.ok(body);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseBody> register(@RequestBody User user) {

        User registeredUser = userService.register(user);
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .description("Registration Success")
                .data(registeredUser)
                .build();
        return ResponseEntity.ok(body);
    }

    @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN')") // for role-based auth
    public ResponseEntity<ApiResponseBody> getUsers() {
        List<User> users = userService.getUsers();
        ApiResponseBody body = ApiResponseBody.builder()
                .status(HttpStatus.OK.value())
                .data(users)
                .build();

        return ResponseEntity.ok(body);
    }
}
