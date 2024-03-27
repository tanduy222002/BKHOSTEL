package com.BKHOSTEL.BKHOSTEL.Dto;

import com.BKHOSTEL.BKHOSTEL.Entity.Address;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String id;
    private String title;
    private Address address;
    private String customerType;
    private double area;
    private double price;
    private String desc;
    private Date expDate;
    private String phoneNum;
    private String status;
    private List<String> assets;

    private String userId;

    private String userName;

    private String approvedId;
    private String approvedName;


    private Date createDate;
    private Date expiredDate;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.address = post.getAddress();
        this.customerType = post.getCustomerType();
        this.area = post.getArea();
        this.price = post.getPrice();
        this.desc = post.getDesc();
        this.expDate = post.getExpDate();
        this.phoneNum = post.getPhoneNum();
        this.status = post.getStatus();
        this.assets = post.getAssets();
        this.userId = post.getCreatedBy().getId();
        this.userName = post.getCreatedBy().getName();
        this.approvedId = post.getApprovedBy().getId();
        this.approvedName = post.getApprovedBy().getName();
        this.createDate = post.getCreateDate();
        this.expiredDate = post.getExpiredDate();
    }
}
