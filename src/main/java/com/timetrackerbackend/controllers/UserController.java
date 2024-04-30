package com.timetrackerbackend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.DeleteResult;
import com.timetrackerbackend.models.User;
import com.timetrackerbackend.services.UserService;

@CrossOrigin("*")
@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{username}")
    public User getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PostMapping("/user")
    public User setUser(@RequestBody User user) {
        return userService.setUser(user);
    }

    @DeleteMapping("/user/{username}")
    public DeleteResult deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
