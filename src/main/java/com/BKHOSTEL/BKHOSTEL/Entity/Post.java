package com.BKHOSTEL.BKHOSTEL.Entity;

import com.BKHOSTEL.BKHOSTEL.Dto.CreatePostDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("post")
public class Post {
    @Id
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

    @DocumentReference(lazy = true)
    private User createdBy;

    @DocumentReference(lazy = true)
    private User approvedBy;

    @DocumentReference(lazy = true)
    private RentalService serviceType;

    private Date createDate;
    private Date expiredDate;

    public Post(CreatePostDto dto) {
        this.area=dto.getArea();
        this.assets=dto.getAssets();
        this.address=new Address(dto.getStreet(), dto.getWard(), dto.getDistrict(),dto.getCity());
        this.desc=dto.getDesc();
        this.phoneNum=dto.getPhoneNumber();
        this.customerType=dto.getCustomerType();
        this.price=dto.getPrice();
        this.title=dto.getTitle();
    }


}
