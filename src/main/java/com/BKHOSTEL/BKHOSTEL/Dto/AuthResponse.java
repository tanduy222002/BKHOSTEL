package com.BKHOSTEL.BKHOSTEL.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String id;
    private String token;
    private String role;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String message;



}
