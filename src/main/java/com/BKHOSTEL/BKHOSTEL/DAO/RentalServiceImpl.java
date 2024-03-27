package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Exception.NotFoundException;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RentalServiceImpl implements RentalServiceDao{
    private MongoTemplate mongoTemplate;

    @Autowired
    public RentalServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public long deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query,RentalService.class).getDeletedCount();
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

    @Override
    public RentalService updateValue(String id, Map<String, Object> props) {
        Query query =new Query();
        Update update = new Update();
        query.addCriteria(Criteria.where("_id").is(id));
        props.entrySet().stream()
                .forEach(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    update.set(key, value);
                });
        return mongoTemplate.findAndModify(query,update, new FindAndModifyOptions().returnNew(true),RentalService.class);

    }
}
