package com.timetrackerbackend.controllers;

import java.util.List;
import com.timetrackerbackend.models.*;
import com.timetrackerbackend.services.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://frontend-bt37r.ondigitalocean.app")
@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Hämtar alla användare.
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // Hämtar en användare.
    @GetMapping("/user/{username}/{password}")
    public User getUser(@PathVariable String username, @PathVariable String password) {
        return userService.getUser(username, password);
    }

    // Skapar användare.
    @PostMapping("/user")
    public User setUser(@RequestBody User user) {
        return userService.setUser(user);
    }

    // Skapar uppgift
    @PostMapping("/user/{username}/task/{title}")
    public Task setTask(@PathVariable String username, @PathVariable String title) {
        return userService.setTask(username, title);
    }

    // Tar bort uppgift
    @DeleteMapping("/user/{username}/task/{title}")
    public Task deleteTask(@PathVariable String username, @PathVariable String title) {
        return userService.deleteTask(username, title);
    }

    // Ändrar uppgift
    @PatchMapping("/user/{username}/task")
    public Task editTask(@PathVariable String username, @RequestBody Task changedTask) {
        return userService.editTask(username, changedTask);
    }

    // Hämtar alla uppgifter.
    @GetMapping("/user/{username}/tasks")
    public List<Task> getTasks(@PathVariable String username) {
        System.out.println("hej");
        return userService.getTasks(username);
    }
}