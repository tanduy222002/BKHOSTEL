package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RechargeDaoImpl implements RechargeDao{
    MongoTemplate mongoTemplate;

    @Autowired
    public RechargeDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }



    @Override
    public Recharge save(Recharge recharge) {
        mongoTemplate.save(recharge);
        return recharge;
    }

    @Override
    public Recharge findById(String id) {
        return mongoTemplate.findById(id, Recharge.class);
    }

    @Override
    public List<Recharge> findAllSuccessRechargeOfUser(String userId) {
        Query query = new Query(Criteria.where("user").is(userId).and("status").is("SUCCESS"));
        return mongoTemplate.find(query, Recharge.class);
    }
}
