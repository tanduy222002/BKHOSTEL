package com.BKHOSTEL.BKHOSTEL.DAO;


import com.BKHOSTEL.BKHOSTEL.Dto.PaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;

import java.util.List;
import java.util.Map;

public interface PostDao {
    public Post save(Post post);

    public Post findPostSumaryById(String id);
    public Post findById(String id);
    public List<Post> getPostOfUserById(String userId);
    public PaginationDto getPost(Map<String, Object> props, int pageSize, int pageIndex);
}
