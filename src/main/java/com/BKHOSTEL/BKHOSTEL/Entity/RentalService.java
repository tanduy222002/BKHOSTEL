package com.BKHOSTEL.BKHOSTEL.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("service")
public class RentalService {
    @Id
    private String id;
    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotNull(message = "Daily price is mandatory")
    private double dailyPrice;

    @NotNull(message = "Weekly price is mandatory")
    private double weeklyPrice;

    @NotNull(message = "Monthly price is mandatory")
    private double monthlyPrice;

    @NotBlank(message = "Description is mandatory")
    private String desc;

    @NotBlank(message = "Suitable for is mandatory")
    private String suitableFor;
}
