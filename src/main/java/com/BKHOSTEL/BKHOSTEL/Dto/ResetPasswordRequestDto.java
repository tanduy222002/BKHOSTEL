package com.BKHOSTEL.BKHOSTEL.Dto;

import com.BKHOSTEL.BKHOSTEL.Anotation.MatchingPassword;
import com.BKHOSTEL.BKHOSTEL.Anotation.StrongPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MatchingPassword(
        password = "newPassword",
        confirmPassword = "confirmNewPassword"
)
public class ResetPasswordRequestDto {
    @NotBlank(message = "Please enter new password")
    @JsonProperty("new_password")
    @StrongPassword
    private String newPassword;
    @JsonProperty("confirm_new_password")
    @NotBlank(message ="Please confirm new password")
    @StrongPassword
    private String confirmNewPassword;

}
