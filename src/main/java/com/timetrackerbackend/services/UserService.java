package com.timetrackerbackend.services;

import java.util.*;
import com.timetrackerbackend.models.*;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder bcryptEncoder;
    private final MongoOperations mongoOperations;

    public UserService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    private User findUser(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoOperations.findOne(query, User.class);
    }

    private Task findTask(List<Task> tasks, String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null;
    }

    private void updateTasks(User user) {
        Query query = new Query();
        query.addCriteria((Criteria.where("username").is(user.getUsername())));
        mongoOperations.updateFirst(query, Update.update("tasks", user.getTasks()), User.class);
    }

    // Returnerar alla användare.
    public List<User> getUsers() {
        return mongoOperations.findAll(User.class);
    }

    // Returnerar angiven användare. Finns inte en användare med angivet
    // användarnamn och lösenord returnerar vi null. Används när en användare vill
    // logga in.
    public User getUser(String username, String password) {
        return findUser(username) != null
                && bcryptEncoder.matches(password, findUser(username).getPassword())
                        ? findUser(username)
                        : null;
    }

    // Finns det redan en användare med ett visst användarnamn? Då returnerar vi
    // null. Ingen ny användare skapas. Används när en ny användare försöker
    // registrera sig.
    public User setUser(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return findUser(user.getUsername()) != null ? null : mongoOperations.insert(user);
    }

    // Skapar uppgift. Returnerar null om en uppgift med samma titel redan
    // existerar.
    public Task setTask(String username, String title) {
        User user = findUser(username);
        if (findTask(user.getTasks(), title) == null) {
            user.getTasks().add(new Task(title));
            updateTasks(user);
            return new Task(title);
        } else {
            return null;
        }
    }

    // Tar bort uppgift. Returnerar null om en uppgift med samma titel inte
    // existerar.
    public Task deleteTask(String username, String title) {
        User user = findUser(username);
        Task task = findTask(user.getTasks(), title);
        if (task != null) {
            user.getTasks().remove(task);
            updateTasks(user);
            return task;
        } else {
            return null;
        }
    }

    // Används för att uppdatera påbörjad och avslutad tid hos en uppgift.
    public Task editTask(String username, String title) {
        User user = findUser(username);
        Task task = findTask(user.getTasks(), title);
        if (task != null) {
            user.getTasks().removeIf(t -> (t.getTitle() == title));
            if (task.getStartTime() == "") {
                System.out.println("Sätter starttid");
                task.setStartTime(LocalDateTime.now().toString());
            } else {
                System.out.println("Sätter sluttid");
                task.setEndTime(LocalDateTime.now().toString());
            }
            updateTasks(user);
            return task;
        } else {
            return null;
        }
    }

    // Returnerar alla uppgifter för en användare.
    public List<Task> getTasks(String username) {
        return findUser(username).getTasks();
    }
}
