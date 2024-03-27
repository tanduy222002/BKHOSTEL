package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Dto.PaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

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
    public PaginationDto findAllSuccessRechargeOfUser(Map<String, Object> filterProps, int size, int pageIndex ) {
        Pageable pageable = PageRequest.of(pageIndex-1,size);
        Query query = new Query();
        filterProps.entrySet().stream()
                .forEach(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    query.addCriteria(Criteria.where(key).is(value));
                });
        double count=mongoTemplate.count(query, Recharge.class);
        count= Math.ceil((double) count/size);
        PaginationDto dto = new PaginationDto();
        query.with(pageable);
        dto.setData(mongoTemplate.find(query, Recharge.class));
        dto.setTotalPage((long)count);
        dto.setCurrentPage(pageIndex);
        return dto;
    }
}
