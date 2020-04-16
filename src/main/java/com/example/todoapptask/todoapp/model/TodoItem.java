package com.example.todoapptask.todoapp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todo_item")
public class TodoItem {

    @Id
    private String id;
    private String name;
    private String description;
    private Date deadline;
    private String status;
    private String todoListId;
    private String userId;
    private String dependedTodoItem;

}
