package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.UserDao;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Entity.UserDetail;
import com.BKHOSTEL.BKHOSTEL.Exception.IncorrectPasswordException;
import com.BKHOSTEL.BKHOSTEL.Exception.UserIdMisMatchException;
import com.BKHOSTEL.BKHOSTEL.Exception.UserNotFoundException;
import com.BKHOSTEL.BKHOSTEL.Service.Client.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
    private UserDao userDaoImpl;

    PasswordEncoder encoder;

    private ImageService imageService;



    @Autowired

    public UserService(UserDao userDaoImpl, PasswordEncoder encoder, ImageService imageService) {
        this.userDaoImpl = userDaoImpl;
        this.encoder = encoder;
        this.imageService = imageService;
    }
    public void updateUser(User user){
        userDaoImpl.save(user);
    }

    public void deleteUserById(String id){
        userDaoImpl.findAndDeleteUserById(id);
    }
    public static User getCurrentUserByAuthContextWithId(String Id){

        User user= getUserByAuthContext();
        if(!user.getId().equals(Id)){
            throw new UserIdMisMatchException();
        }
        return user;
    }

    public void changeUserStatus(String userId, String newStatus){
        User user =userDaoImpl.findById(userId);
        if(user == null)
            throw new UserNotFoundException("User not found: "  + userId);
        user.setStatus(newStatus);
        userDaoImpl.save(user);

    }

    public double getUserBalance(String userId){
        User user= getUserByAuthContext();
        return user.getBalance();
    }
    public static User getUserByAuthContext(){
        // Retrieve the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the principal from the authentication object
        Object principal= authentication.getPrincipal();

        if (! (principal instanceof UserDetails)) {
            throw new RuntimeException("Internal error");
        }
        User user= ((UserDetail) principal).getUser();
        return user;
    }
    public String updateUserProfile(String userId,String email, String phone, String name, String fullName, String avatar) throws IOException {
        User user= getCurrentUserByAuthContextWithId(userId);
        user.setEmail(email);
        user.setPhone(phone);
        user.setName(name);
        user.setFullName(fullName);
        if(avatar != null){
            String url = updateUserProfileAvatar(avatar);
            user.setAvatar(url);
        }
        userDaoImpl.save(user);
        return "Update successfully";
    }

    public String updateUserProfileAvatar(String base64Img) throws IOException {
        System.out.println(base64Img);
       return imageService.upLoadImageWithBase64(base64Img);
    }

    public String updateUserPassword(String userId,String oldPassword,String newPassword){
        User user= getCurrentUserByAuthContextWithId(userId);
        if(!user.getPassword().equals(encoder.encode(oldPassword))){
            throw new IncorrectPasswordException();
        }
        user.setPassword(encoder.encode(newPassword));
        userDaoImpl.save(user);
        return "Update password successfully";
    }


    public User getUserProfileById(String userId) {
        User user = userDaoImpl.getUserProfileById(userId);
        if(user==null){
            throw new  UserNotFoundException();
        }
        return user;
    }
}
