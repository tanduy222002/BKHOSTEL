package com.BKHOSTEL.BKHOSTEL.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentDto {
    @Min(5)
    private int day;
    @NotNull
    private String description;

}

