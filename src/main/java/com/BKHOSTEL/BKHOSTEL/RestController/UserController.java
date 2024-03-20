package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Dto.PasswordChangeRequestDto;
import com.BKHOSTEL.BKHOSTEL.Dto.UserProfileDto;
import com.BKHOSTEL.BKHOSTEL.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin
@RestController
@RequestMapping(value = "/users")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) {
        UserProfileDto profile = new UserProfileDto(userService.getUserProfileById(userId));
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/{userId}/profile")
    public ResponseEntity<?> updateUserProfile(@PathVariable String userId, @Valid @RequestBody UserProfileDto profile) throws IOException {
        String message = userService.updateUserProfile(userId,profile.getEmail(),
                profile.getPhone(),
                profile.getName(),
                profile.getFullName(),
                profile.getAvatar());
        return ResponseEntity.ok(message);
    }
    @PutMapping("/{userId}/profile/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable String userId, @Valid @RequestBody PasswordChangeRequestDto req) {
        String message = userService.updateUserPassword(userId,req.getOldPassword(),req.getNewPassword());
        return ResponseEntity.ok(message);
    }

}
