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

    private String status="Active";


    private String facebook;

    private String fullName;

    private String avatar;

    @DocumentReference(lazy = false)
    private Role role;

    @DocumentReference(lazy = false)
    private RefreshToken refreshToken;

    @DocumentReference(lazy = false)
    private Otp otp;

//    @Version
//    private long version;

    public User() {
    }

    public User(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.balance=null;
        this.status=null;
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
