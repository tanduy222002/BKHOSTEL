package com.BKHOSTEL.BKHOSTEL.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpRequestDto {
    @NotBlank(message = "otp code must not be empty")
    private String code;
    @NotBlank(message = "identifer must not be empty")
    private String identifier;
}
