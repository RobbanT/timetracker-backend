package com.timetrackerbackend.models;

import java.util.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {
    public final String username, password;
    private List<Task> tasks;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
