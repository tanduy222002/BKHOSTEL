package com.BKHOSTEL.BKHOSTEL.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentResponseDto {
    @JsonProperty("payment_url")
    private String paymentUrl;
}
