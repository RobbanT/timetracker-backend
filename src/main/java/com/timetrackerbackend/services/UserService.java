package com.timetrackerbackend.services;

import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.mongodb.client.result.DeleteResult;

import com.timetrackerbackend.models.User;

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
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        return mongoOperations.findOne(query, User.class) != null ? null : mongoOperations.insert(user);
    }

    // Tar bort angiven användare. Försöker man ta bort admin så returneras null.
    // Samma sak om man förösker ta bort en användare som inte existerar.
    public DeleteResult deleteUser(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        System.out.println(username);
        return username.equals("admin") ? null : mongoOperations.remove(query, User.class);
    }
}
