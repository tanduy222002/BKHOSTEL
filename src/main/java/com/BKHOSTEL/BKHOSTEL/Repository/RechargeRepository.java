package com.BKHOSTEL.BKHOSTEL.Repository;

import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RechargeRepository extends MongoRepository<Recharge, String> {

}
