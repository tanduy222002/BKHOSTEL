package com.BKHOSTEL.BKHOSTEL.Entity;


import jakarta.validation.constraints.NotNull;
import lombok.Data;


import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Document("user")
public class User {

    @Id
    private String id;


    private String name;

    private String userName;

    private String password;


    private String phone;


    private String email;

    private Double balance;




    private String facebook;

    @DocumentReference(lazy = true)
    private List<Role> roles;


    @DocumentReference(lazy = false)
    private RefreshToken refreshToken;

//    @Version
//    private long version;

    public User() {
    }

    public User(String userName, String password) {
        System.out.println("user role constructor called");
        this.userName = userName;
        this.password = password;
    }


    public void addRole(Role role) {
        if (this.roles == null) {
            System.out.println(this.roles);
            System.out.println("this.roles ==null");
            this.roles = new ArrayList<Role>();
            this.roles.add(role);
        } else {
            this.roles.add(role);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", facebook='" + facebook + '\'' +
                ", roles=" + roles +
                ", refreshToken=" + refreshToken +

                '}';
    }
}
