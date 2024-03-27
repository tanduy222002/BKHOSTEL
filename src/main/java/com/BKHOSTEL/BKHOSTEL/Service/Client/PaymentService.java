package com.BKHOSTEL.BKHOSTEL.Service.Client;

import com.BKHOSTEL.BKHOSTEL.DAO.*;
import com.BKHOSTEL.BKHOSTEL.Entity.Payment;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Exception.InsufficientBalanceException;
import com.BKHOSTEL.BKHOSTEL.Exception.InvalidRequestException;
import com.BKHOSTEL.BKHOSTEL.Exception.NotFoundException;
import com.BKHOSTEL.BKHOSTEL.Service.RentalServiceService;
import com.BKHOSTEL.BKHOSTEL.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PaymentService {
    private PostDao postDaoImpl;
    private UserDao userDaoImpl;

    private PaymentDao paymentDaoImpl;

    @Autowired
    public PaymentService(PostDao postDaoImpl, UserDao userDaoImpl, PaymentDao paymentDaoImpl) {
        this.postDaoImpl = postDaoImpl;
        this.userDaoImpl = userDaoImpl;
        this.paymentDaoImpl = paymentDaoImpl;
    }

    @Transactional
    public String createPaymentForUserPost(String userId, String postId, String description, int day){
        User user = UserService.getCurrentUserByAuthContextWithId(userId);
        Post post = postDaoImpl.findById(postId);
        if(post == null){
            throw new NotFoundException("No post found with id " + postId);
        }
        if(!post.getCreatedBy().getId().equals(userId)){
            throw new InvalidRequestException("Post is not belong to user");
        }
        Payment payment = new Payment();
        RentalService service = post.getServiceType();

        double totalAmount = RentalServiceService.calculateServiceCost(service,day);

        if(user.getBalance()<=totalAmount){
            throw new InsufficientBalanceException();
        }

        payment.setAmount(totalAmount);
        payment.setPost(post);
        payment.setDate(new Date());
        payment.setDescription(description);

        user.setBalance(user.getBalance()-totalAmount);

        userDaoImpl.save(user);

        post.setStatus("ACTIVE");
        if(post.getExpiredDate()==null){
             post.setExpiredDate(new Date(
                    System.currentTimeMillis() + day * 86400000
        ));
        }
        else{
            post.setExpiredDate(new Date(
                  day * 86400000 + post.getExpiredDate().getTime()
            ));
        }
        postDaoImpl.save(post);

        payment.setStatus("SUCCESS");

        paymentDaoImpl.save(payment);

        return "Payment is created successfully";

    }
}
