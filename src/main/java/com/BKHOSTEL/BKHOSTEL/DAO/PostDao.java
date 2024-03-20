package com.BKHOSTEL.BKHOSTEL.DAO;


import com.BKHOSTEL.BKHOSTEL.Entity.Post;

import java.util.List;

public interface PostDao {
    public Post save(Post post);

    public List<Post> getPostOfUserById(String userId);
}
