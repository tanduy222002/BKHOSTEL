package com.BKHOSTEL.BKHOSTEL.Dto;

import com.BKHOSTEL.BKHOSTEL.Anotation.MatchingPassword;
import com.BKHOSTEL.BKHOSTEL.Anotation.StrongPassword;
import com.BKHOSTEL.BKHOSTEL.Entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
@MatchingPassword(
        password = "password",
        confirmPassword = "confirm_password"
)
public class SignUpRequest {
    @NotBlank(message = "user name is mandatory")
    @JsonProperty("user_name")
    private String userName;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "phone number is mandatory")
    private String phone;
    @StrongPassword
    @NotBlank(message = "password is mandatory")
    private String password;
    @StrongPassword
    @NotBlank(message = "confirm password is mandatory")
    private String confirm_password;

}
