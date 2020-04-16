package com.example.todoapptask.todoapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = "com.example.todoapptask.todoapp")
@EnableMongoRepositories(basePackages = "com.example.todoapptask.todoapp.repository")
public class TodoAppConfig {

}
