package com.timetrackerbackend.controllers;

import java.util.List;
import com.timetrackerbackend.models.*;
import com.timetrackerbackend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://frontend-bt37r.ondigitalocean.app")
@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private PasswordEncoder bcryptEncoder;

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
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userService.setUser(user);
    }

    @PatchMapping("/user/{username}")
    public User editUser(@PathVariable String username, @RequestBody List<Task> tasks) {
        return userService.editUser(username, tasks);
    }
}