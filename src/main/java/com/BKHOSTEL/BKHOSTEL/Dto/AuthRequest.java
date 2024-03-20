package com.BKHOSTEL.BKHOSTEL.Dto;

import com.BKHOSTEL.BKHOSTEL.Anotation.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthRequest {
    @NotBlank(message = "user name is mandatory")
    @Schema( example = "tanduy222002@gmail.com", required = true)
    private String username;
    @NotBlank(message = "password is mandatory")
    @Schema( example = "Tdt@01263655736", required = true)
    @StrongPassword
    private String password;


}
