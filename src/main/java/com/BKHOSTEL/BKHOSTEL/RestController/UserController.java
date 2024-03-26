package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Dto.PasswordChangeRequestDto;
import com.BKHOSTEL.BKHOSTEL.Dto.RechargePaginationDto;
import com.BKHOSTEL.BKHOSTEL.Dto.UserBalanceDto;
import com.BKHOSTEL.BKHOSTEL.Dto.UserProfileDto;
import com.BKHOSTEL.BKHOSTEL.Service.RechargeService;
import com.BKHOSTEL.BKHOSTEL.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;



@RestController
@RequestMapping(value = "/users")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class UserController {

    private UserService userService;
    private RechargeService rechargeService;

    @Autowired
    public UserController(UserService userService, RechargeService rechargeService) {
        this.userService = userService;
        this.rechargeService = rechargeService;
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) {
        UserProfileDto profile = new UserProfileDto(userService.getUserProfileById(userId));
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<?> getUserBalance(@PathVariable String userId) {
        UserBalanceDto balanceDto = new UserBalanceDto(userService.getUserBalance(userId));
        return ResponseEntity.ok(balanceDto);
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

    @GetMapping("/{userId}/recharges")
    public ResponseEntity<?> getRechargesByUserId(@RequestParam(required = true) String userId,@RequestParam(required = false) String status,
                                             @Min(5) @RequestParam(defaultValue = "5", required = false) int size,
                                             @Min(1) @RequestParam(defaultValue = "1",required = false) int pageIndex) {
        RechargePaginationDto recharges= rechargeService.getRechargeByUserId(userId,status,size,pageIndex);
        return ResponseEntity.ok(recharges);
    }
    @PutMapping("/{userId}/profile/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable String userId, @Valid @RequestBody PasswordChangeRequestDto req) {
        String message = userService.updateUserPassword(userId,req.getOldPassword(),req.getNewPassword());
        return ResponseEntity.ok(message);
    }

}
