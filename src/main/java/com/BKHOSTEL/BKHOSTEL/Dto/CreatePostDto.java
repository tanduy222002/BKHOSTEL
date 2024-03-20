package com.BKHOSTEL.BKHOSTEL.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {
        @NotBlank(message ="service type is mandatory ")
        @JsonProperty("service_type")
        private String serviceTypeId;
        @NotBlank(message ="post title is mandatory ")
        private String title;
        @NotBlank(message ="Description is mandatory ")
        private String desc;
        @NotNull(message = "lease price is mandatory ")
        private double price;
        @NotBlank(message ="accommodation type is mandatory ")
        @JsonProperty("type")
        private String accommodationCategory;
        @JsonProperty("customer_type")
        private String customerType;
        @NotNull(message ="area is mandatory ")
        private double area;
        @NotBlank(message ="contact number is mandatory ")
        @JsonProperty("phone_num")
        private String phoneNumber;
        @NotEmpty(message ="Room photos are mandatory ")
        @JsonProperty("images")
        private List<String> assets;
        @NotBlank(message ="street address is mandatory")
        private String street;
        @NotBlank(message ="ward is mandatory ")
        private String ward;
        @NotBlank(message ="district is mandatory ")
        private String district;
        @NotBlank(message ="city is mandatory ")
        private String city;



}
