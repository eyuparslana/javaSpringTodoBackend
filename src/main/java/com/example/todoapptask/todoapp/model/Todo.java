package com.example.todoapptask.todoapp.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todo")
public class Todo {

    @Id
    private String id;
    private String todoListName;
    private String userId;

}
