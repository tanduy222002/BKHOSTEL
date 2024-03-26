package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RentalServiceImpl implements RentalServiceDao{
    private MongoTemplate mongoTemplate;

    @Autowired
    public RentalServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public RentalService save(RentalService rentalService) {
        mongoTemplate.save(rentalService);
        return rentalService;
    }

    @Override
    public RentalService findById(String id) {
        return mongoTemplate.findById(id,RentalService.class);
    }

    public List<RentalService> getAllServices(){
        return mongoTemplate.findAll(RentalService.class,"service");
    }
}
