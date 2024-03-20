package com.BKHOSTEL.BKHOSTEL.Dto;

import com.BKHOSTEL.BKHOSTEL.Anotation.MatchingPassword;
import com.BKHOSTEL.BKHOSTEL.Anotation.StrongPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MatchingPassword(
        password = "newPassword",
        confirmPassword = "confirmNewPassword"
)
public class PasswordChangeRequestDto {
    @StrongPassword
    @JsonProperty("old_password")
    private String oldPassword;
    @StrongPassword
    @JsonProperty("new_password")
    private String newPassword;
    @StrongPassword
    @JsonProperty("confirm_new_password")
    private String confirmNewPassword;
}
