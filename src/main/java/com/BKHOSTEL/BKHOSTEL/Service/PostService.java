package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.PostDao;
import com.BKHOSTEL.BKHOSTEL.DAO.RentalServiceDao;
import com.BKHOSTEL.BKHOSTEL.Dto.PaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Entity.UserDetail;
import com.BKHOSTEL.BKHOSTEL.Exception.RentalServiceNotFoundException;
import com.BKHOSTEL.BKHOSTEL.Service.Client.ImageService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private UserService userService;
    private ImageService imageService;

    private PostDao postDaoImpl;

    private RentalServiceDao serviceDaoImpl;

    @Autowired
    public PostService(RentalServiceDao serviceDaoImpl,PostDao postDaoImpl,UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
        this.postDaoImpl = postDaoImpl;
        this.serviceDaoImpl =serviceDaoImpl;
    }

    public Post getPostById(String id) {
        return postDaoImpl.findById(id);
    }
    @Transactional
    public String createPost(Post post, String serviceTypeId ) {
        System.out.println("createPost");
        User user = userService.getUserByAuthContext();
        post.setCreatedBy(user);
        post.setCreateDate(new Date());
        post.setAssets(post.getAssets().stream().map(base64Img->{
            try{
                return imageService.upLoadImageWithBase64(base64Img);}
            catch(Exception e){
                throw new RuntimeException(("Error when saving image"));
            }
        }).collect(Collectors.toList())
        );
        post.setStatus("PENDING");
        RentalService service = serviceDaoImpl.findById(serviceTypeId);
        if(service == null){
            throw new RentalServiceNotFoundException();
        }
        post.setServiceType(service);

        postDaoImpl.save(post);

        return "save post successfully";

    }
    public PaginationDto getAllPost(String area, String minPrice, String maxPrice, String status, String userId,
                                    String ward, String district, String city, String customerType, int pageSize, int pageIndex){
        Map<String,Object> props = new HashMap<>();
        if (area != null && !area.isEmpty()) {
            props.put("area", area);
        }
        if (minPrice != null && !minPrice.isEmpty()) {
            props.put("minPrice", minPrice);
        }
        if (maxPrice != null && !maxPrice.isEmpty()) {
            props.put("maxPrice", maxPrice);
        }
        if (status != null && !status.isEmpty()) {
            props.put("status", status);
        }
        if (userId != null && !userId.isEmpty()) {
            props.put("createdBy", new ObjectId(userId));
        }
        if (ward != null && !ward.isEmpty()) {
            props.put("address.ward", ward);
        }
        if (district != null && !district.isEmpty()) {
            props.put("address.district", district);
        }
        if (city != null && !city.isEmpty()) {
            props.put("address.city", city);
        }
        if (customerType != null && !customerType.isEmpty()) {
            props.put("customerType", customerType);
        }
        return postDaoImpl.getPost(props,pageSize,pageIndex);
    }
    public List<Post>getAllUserPost(String userId){
        User user = UserService.getCurrentUserByAuthContextWithId(userId);
        return postDaoImpl.getPostOfUserById(userId);
    }


}
