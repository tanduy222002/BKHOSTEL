package com.BKHOSTEL.BKHOSTEL.Repository;

import com.BKHOSTEL.BKHOSTEL.Entity.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    public Role findByName(String name);

}
