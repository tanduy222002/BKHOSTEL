package com.BKHOSTEL.BKHOSTEL.Repository;

import com.BKHOSTEL.BKHOSTEL.Entity.User2;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User2Repository extends MongoRepository<User2,String> {

}
