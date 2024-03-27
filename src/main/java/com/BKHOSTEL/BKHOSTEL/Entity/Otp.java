package com.BKHOSTEL.BKHOSTEL.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Document("Otp")
@Data
public class Otp {
    @Id

    private String id;

    @NotNull
    private String code;


    @NotNull
    private Date expiredDate;

    public Otp(String code, Date expiryDate) {
        this.code = code;
        this.expiredDate = expiredDate;
    }




}
