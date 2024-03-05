package com.BKHOSTEL.BKHOSTEL.Dto;

import com.BKHOSTEL.BKHOSTEL.Entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor

public class SignUpRequest {
    @NotBlank(message = "user name is mandatory")
    private String userName;
    @NotBlank(message = "password is mandatory")
    private String password;
    private List<String> roles;
}
