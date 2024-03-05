package com.BKHOSTEL.BKHOSTEL.Dto;

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
    private List<?> roles;
    private String refreshToken;
    private String message;



}
