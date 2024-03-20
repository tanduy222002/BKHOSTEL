package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.PostDao;
import com.BKHOSTEL.BKHOSTEL.DAO.RentalServiceDao;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Entity.UserDetail;
import com.BKHOSTEL.BKHOSTEL.Exception.RentalServiceNotFoundException;
import com.BKHOSTEL.BKHOSTEL.Service.Client.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
    public List<Post> getAllUserPost(String id){
        return postDaoImpl.getPostOfUserById(id);
    }
}
