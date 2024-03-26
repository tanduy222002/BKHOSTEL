package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Dto.RechargePaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public RechargePaginationDto findAllSuccessRechargeOfUser(Map<String, Object> filterProps, int size, int pageIndex ) {
        Pageable pageable = PageRequest.of(pageIndex,size);
        Query query = new Query().with(pageable);
        filterProps.entrySet().stream()
                .forEach(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    query.addCriteria(Criteria.where(key).is(value));
                });
        long count=mongoTemplate.count(query, Recharge.class);
        count=(long)Math.ceil(count/size);
        RechargePaginationDto dto = new RechargePaginationDto();
        dto.setRecharges(mongoTemplate.find(query, Recharge.class));
        dto.setTotalPage(count);
        dto.setCurrentPage(pageIndex);
        return dto;
    }
}
