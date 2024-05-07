package com.timetrackerbackend.services;

import java.util.List;
import com.timetrackerbackend.models.*;
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
        if (findTask(findUser(username).getTasks(), title) == null) {
            findUser(username).getTasks().add(new Task(title));
            Query query = new Query();
            query.addCriteria((Criteria.where("username").is(username)));
            mongoOperations.updateFirst(query, Update.update("tasks",
                    findUser(username).getTasks()), User.class);
            return new Task(title);
        } else {
            return null;
        }
    }

    public Task deleteTask(String username, String title) {
        User user = findUser(username);

        for (Task task : user.getTasks()) {
            if (task.getTitle().equals(title)) {
                return null;
            }
        }

        user.getTasks().removeIf((task) -> task.getTitle().equals(title));
        return new Task(username);
    }

    public Task editTask(Task task) {

        return task;
    }
}
/*
 * // Spara ändringar för en användares uppgifter. Används när en användare vill
 * // göra någon form av förändringar på sina uppgifter.
 * public User editUser(User user) {
 * System.out.println(user.getTasks());
 * Query query = new Query();
 * query.addCriteria(Criteria.where("username").is(user.getUsername()));
 * // Kontrollerar så att det inte finns tasks med samma titel. Är det så så
 * // returnerar vi null.
 * for (Task task1 : user.getTasks()) {
 * int count = 0;
 * for (Task task2 : user.getTasks()) {
 * if (task1.getTitle().equals(task2.getTitle())) {
 * if (++count > 1) {
 * return null;
 * }
 * }
 * }
 * }
 * 
 * mongoOperations.updateFirst(query, Update.update("tasks", user.getTasks()),
 * User.class);
 * return mongoOperations.findOne(query, User.class);
 * }
 */
