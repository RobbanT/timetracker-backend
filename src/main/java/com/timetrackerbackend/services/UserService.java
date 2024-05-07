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
    private final MongoOperations mongoOperations;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public UserService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    // Returnerar alla användare.
    public List<User> getUsers() {
        return mongoOperations.findAll(User.class);
    }

    // Returnerar angiven användare. Finns inte en användare med angivet
    // användarnamn och lösenord returnerar vi null. Används när en användare vill
    // logga in.
    public User getUser(String username, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        return mongoOperations.findOne(query, User.class) != null
                && bcryptEncoder.matches(password, mongoOperations.findOne(query, User.class).getPassword())
                        ? mongoOperations.findOne(query, User.class)
                        : null;
    }

    // Finns det redan en användare med ett visst användarnamn? Då returnerar vi
    // null. Ingen ny användare skapas. Används när en ny användare vill registrera
    // sig.
    public User setUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        user.setPassword(bcryptEncoder.encode(user.getPassword()));

        return mongoOperations.findOne(query, User.class) != null ? null : mongoOperations.insert(user);
    }

    // Skapar uppgift. Returnerar null om en uppgift med samma titel redan
    // existerar.
    public Task setTask(String username, String title) {
        System.out.println(username + " " + title);
        /*
         * Query query = new Query();
         * query.addCriteria(Criteria.where("username").is(username));
         * User user = mongoOperations.findOne(query, User.class);
         * 
         * for (Task task : user.getTasks()) {
         * if (task.getTitle() == title) {
         * return null;
         * }
         * }
         * 
         * user.getTasks().add(new Task(title));
         * mongoOperations.updateFirst(query, Update.update("tasks", user.getTasks()),
         * User.class);
         */
        return new Task(title);
    }

    // Spara ändringar för en användares uppgifter. Används när en användare vill
    // göra någon form av förändringar på sina uppgifter.
    public User editUser(User user) {
        System.out.println(user.getTasks());
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        // Kontrollerar så att det inte finns tasks med samma titel. Är det så så
        // returnerar vi null.
        for (Task task1 : user.getTasks()) {
            int count = 0;
            for (Task task2 : user.getTasks()) {
                if (task1.getTitle().equals(task2.getTitle())) {
                    if (++count > 1) {
                        return null;
                    }
                }
            }
        }

        mongoOperations.updateFirst(query, Update.update("tasks", user.getTasks()), User.class);
        return mongoOperations.findOne(query, User.class);
    }
}