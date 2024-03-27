package com.BKHOSTEL.BKHOSTEL.DAO;


import com.BKHOSTEL.BKHOSTEL.Entity.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OtpDaoImpl implements OtpDao {
    private MongoTemplate mongoTemplate;

    @Autowired
    public OtpDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Otp save(Otp otp) {
        return mongoTemplate.save(otp);
    }
}
