package com.BKHOSTEL.BKHOSTEL.Dto;

import com.BKHOSTEL.BKHOSTEL.Anotation.ValidEmail;
import com.BKHOSTEL.BKHOSTEL.Entity.Role;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String name;

    private String phone;

    @ValidEmail
    private String email;

    private String avatar;

    @JsonProperty("full_name")
    private String fullName;

    private String role;

    private String status;

    public UserProfileDto(User user) {
        this.email=user.getEmail();
        this.name=user.getName();
        this.role=user.getRole().getName();
        this.phone=user.getPhone();
        this.fullName=user.getFullName();
        this.avatar=user.getAvatar();
        this.status=user.getStatus();
    }
}
