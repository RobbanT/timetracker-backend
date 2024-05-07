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
        return setTask(username, title);
    }

    // Tar bort uppgift
    @DeleteMapping("/user/{username}/task/{title}")
    public Task deleteTask(@PathVariable String username, @PathVariable String title) {
        return new Task("title");
    }

    // Ändrar uppgift
    @DeleteMapping("/user/{username}/task")
    public Task deleteTask(@RequestBody User user) {
        return new Task("title");
    }

    @PatchMapping("/user")
    public User editUser(@RequestBody User user) {
        return userService.editUser(user);
    }
}