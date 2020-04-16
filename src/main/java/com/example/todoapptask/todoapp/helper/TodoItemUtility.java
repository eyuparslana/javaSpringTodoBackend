package com.example.todoapptask.todoapp.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItemUtility {

    public static Date convertStringtoDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return formatter.parse(dateString);
    }
}
