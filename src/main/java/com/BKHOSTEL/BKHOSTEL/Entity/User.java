package com.BKHOSTEL.BKHOSTEL.Entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
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

    private Double balance=0.0;




    private String facebook;

    private String fullName;

    private String avatar;

    @DocumentReference(lazy = false)
    @JsonManagedReference
    private Role role;

    @JsonManagedReference
    @DocumentReference(lazy = false)
    private RefreshToken refreshToken;

//    @Version
//    private long version;

    public User() {
    }

    public User(String userName, String password, String email, String phone) {
        System.out.println("user role constructor called");
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }


    public void addRole(Role role) {
       this.role=role;
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
                ", roles=" + role +
                ", refreshToken=" + refreshToken +

                '}';
    }
}
