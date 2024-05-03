package com.timetrackerbackend.services;

import java.util.List;
import com.timetrackerbackend.models.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final MongoOperations mongoOperations;

    public UserService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    // Returnerar alla användare.
    public List<User> getUsers() {
        return mongoOperations.findAll(User.class);
    }

    // Returnerar angiven användare. Finns ingen användare returnerar vi null.
    public User getUser(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoOperations.findOne(query, User.class);
    }

    // Finns det redan en användare med ett visst användarnamn? Då returnerar vi
    // null. Ingen ny användare skapas.
    public User setUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.username));
        return mongoOperations.findOne(query, User.class) != null ? null : mongoOperations.insert(user);
    }

    // Spara ändringar för en användares uppgifter.
    public User editUser(String username, List<Task> tasks) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        // Kontrollerar så att det inte finns tasks med samma titel.
        for (Task task1 : tasks) {
            int count = 0;
            for (Task task2 : tasks) {
                if (task1.getTitle().equals(task2.getTitle())) {
                    if (++count > 1) {
                        return null;
                    }
                }
            }
        }

        mongoOperations.updateFirst(query, Update.update("tasks", tasks), User.class);
        return mongoOperations.findOne(query, User.class);
    }
}