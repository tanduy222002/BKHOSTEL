package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.RefreshToken;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
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
    public User save(User user) {
        mongoTemplate.save(user);
        return user;
    }

    public User getUserProfileById(String userId) {
        Query query = new Query(Criteria.where("_id").is(userId));
        query.fields().include("name", "userName","email","phone","avatar","fullName","role","status");
        return mongoTemplate.findOne(query, User.class);
    }
}
