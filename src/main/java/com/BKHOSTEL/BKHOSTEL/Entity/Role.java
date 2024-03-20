package com.BKHOSTEL.BKHOSTEL.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Document("role")
public class Role {
    @Id
    private String id;

    private String name;

    public Role() { }

    public Role(String name) {
        this.name = name;
    }


    @DocumentReference(lazy = true)
    @JsonBackReference
    private List<User> users;

    public void addUser(User user) {
        if (this.users == null) {
            System.out.println("this.users ==null");
            this.users = new ArrayList<User>();
            this.users.add(user);

        }
        else {
            this.users.add(user);
        }


    }


}
