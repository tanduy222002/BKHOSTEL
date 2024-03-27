package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.RefreshToken;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    MongoTemplate mongoTemplate;

    @Autowired
    public UserDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public User findById(String id){
        return mongoTemplate.findById(id,User.class);
    }

    @Override
    public User save(User user) {
        mongoTemplate.save(user);
        return user;
    }

    @Override
    public void findAndDeleteUserById(String userId) {
        Query query = Query.query(Criteria.where("_id").is(userId));

        // Execute findAndRemove operation
        User removedUser = mongoTemplate.findAndRemove(query, User.class);

        if (removedUser == null) {
            throw new NotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public User getUserProfileById(String userId) {
        Query query = new Query(Criteria.where("_id").is(userId));
        query.fields().include("name", "userName","email","phone","avatar","fullName","role","status");
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User findByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        query.fields().include("name", "userName","email","phone","avatar","fullName","role","status");
        return mongoTemplate.findOne(query, User.class);
    }
}
