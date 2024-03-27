package com.BKHOSTEL.BKHOSTEL.Entity;

import jakarta.annotation.sql.DataSourceDefinitions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("payment")
public class Payment {
    @Id
    private String id;

    private double amount;

    Date date;

    private String description;

    private String status;

    @DocumentReference(lazy = true)
    private Post post;
}
