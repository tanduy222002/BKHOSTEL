package com.BKHOSTEL.BKHOSTEL.Dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalServiceDto {


    private String category;

    @Min(0)
    private Double dailyPrice=null;


    @Min(0)
    private Double weeklyPrice=null;


    @Min(0)
    private Double monthlyPrice=null;


    private String desc;

    private String suitableFor;
}
