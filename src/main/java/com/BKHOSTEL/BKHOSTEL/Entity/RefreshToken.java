package com.BKHOSTEL.BKHOSTEL.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.Instant;

@Getter
@Setter
@Document("refreshToken")
public class RefreshToken {

    @Id
    private String id;
    @JsonBackReference
    @DocumentReference(lazy = true)
    private User user;
    private String token;
    private Instant expiryDate;

    public RefreshToken() {
    }

    public RefreshToken( User user, String token, Instant expiryDate) {
        this.user = user;
        this.token = token;
        this.expiryDate = expiryDate;
    }

}
