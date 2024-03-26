package com.BKHOSTEL.BKHOSTEL.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("recharge")
public class Recharge {
    @Id
    private String id;

    private String amount;

    Date date;

    private String description;

    private String status;

    @DocumentReference(lazy = true)
    @JsonIgnore
    User user;

    public Recharge(Date date, String amount, String description, String status, User user) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.user = user;
    }
}
