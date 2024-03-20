package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
}
