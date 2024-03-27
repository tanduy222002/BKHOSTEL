package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Dto.PaginationDto;
import com.BKHOSTEL.BKHOSTEL.Dto.PostDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Exception.NotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PostDaoImpl implements PostDao {
    private MongoTemplate mongoTemplate;

    @Autowired
    public PostDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Post save(Post post) {
        return mongoTemplate.save(post);
    }

    @Override
    public List<Post> getPostOfUserById(String userId) {
        ObjectId id= new ObjectId(userId);
        Query query = new Query(Criteria.where("createdBy").is(id));
        List<Post> posts = mongoTemplate.find(query, Post.class);
        return posts;
    }

    @Override
    public PaginationDto getPost(Map<String, Object> props, int pageSize, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex-1,pageSize);
        Query query = new Query();
        Object min = props.remove("minPrice");
        Object max = props.remove("maxPrice");
        Object status=props.get("status");
        if (status!=null&&status.equals("ACTIVE")){
            Date now=new Date();
            System.out.println(now);
            query.addCriteria(Criteria.where("expiredDate").gte(now));
        }
        if(min!=null) {
            query.addCriteria(Criteria.where("price").gte(min));
        }
        if(max!=null) {
            query.addCriteria(Criteria.where("price").lte(max));
        }
        props.entrySet().stream()
                .forEach(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    query.addCriteria(Criteria.where(key).is(value));
                });

        double count=mongoTemplate.count(query, Post.class);
        count= Math.ceil((double) count/pageSize);

        PaginationDto dto = new PaginationDto();
        query.with(pageable);
        Fields fields = Fields.from();

        query.fields().include("title","address","area","price","createdBy","assets").slice("assets",1);

        List<Post> posts=mongoTemplate.find(query, Post.class);
        posts.stream().forEach(post ->{
            User createdByUser = post.getCreatedBy();
            post.setCreatedBy(new User(createdByUser.getId(), createdByUser.getName(), createdByUser.getPhone(), createdByUser.getEmail()));
        });
        dto.setData(posts);
        dto.setTotalPage((long)count);
        dto.setCurrentPage(pageIndex);
        return dto;
    }

    @Override
    public Post findPostSumaryById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        query.fields().exclude("serviceType");
        Post post=mongoTemplate.findOne(query, Post.class);
        if(post==null){
            throw new NotFoundException("Post is not found");
        }
        User createdByUser = post.getCreatedBy();
        User approvedByUser = post.getApprovedBy();
        post.setCreatedBy(new User(createdByUser.getId(), createdByUser.getName(), createdByUser.getPhone(), createdByUser.getEmail()));
        post.setApprovedBy(new User(approvedByUser.getId(), approvedByUser.getName(), approvedByUser.getPhone(),approvedByUser.getEmail()));
        return post;

    }
    @Override
    public Post findById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Post post=mongoTemplate.findOne(query, Post.class);
        if(post==null){
            throw new NotFoundException("Post is not found");
        }
        return post;

    }
}
