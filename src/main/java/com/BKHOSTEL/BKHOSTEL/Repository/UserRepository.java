package com.BKHOSTEL.BKHOSTEL.Repository;

import com.BKHOSTEL.BKHOSTEL.Entity.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public User findByUserName(String userName);
    public boolean existsByUserName(String userName);



}
