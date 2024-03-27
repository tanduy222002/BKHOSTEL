package com.BKHOSTEL.BKHOSTEL.DAO;


import com.BKHOSTEL.BKHOSTEL.Entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDaoImpl implements PaymentDao{
    private MongoTemplate mongoTemplate;

    @Autowired
    public PaymentDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public Payment save(Payment payment) {
        return mongoTemplate.save(payment);
    }
}
