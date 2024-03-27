package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Anotation.ValidAccountStatus;
import com.BKHOSTEL.BKHOSTEL.Anotation.ValidPostStatus;
import com.BKHOSTEL.BKHOSTEL.Dto.*;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Service.Client.PaymentService;
import com.BKHOSTEL.BKHOSTEL.Service.PostService;
import com.BKHOSTEL.BKHOSTEL.Service.RechargeService;
import com.BKHOSTEL.BKHOSTEL.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/users")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class UserController {

    private UserService userService;
    private RechargeService rechargeService;

    private PaymentService paymentService;

    private PostService postService;

    @Autowired
    public UserController(UserService userService, RechargeService rechargeService, PaymentService paymentService, PostService postService) {
        this.userService = userService;
        this.rechargeService = rechargeService;
        this.paymentService = paymentService;
        this.postService = postService;
    }

    @GetMapping("/{userId}/profile")
    @Operation(summary = "Get user profile")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) {
        UserProfileDto profile = new UserProfileDto(userService.getUserProfileById(userId));
        return ResponseEntity.ok(profile);
    }

    @Secured("ROLE_MANAGER")
    @PatchMapping("/{userId}/status")
    @Operation(summary = "Change the status of user account/ Require role: \"MANAGER\"")
    public ResponseEntity<?> changeUserStatus(@PathVariable String userId, @ValidAccountStatus  @RequestParam String status) {
        userService.changeUserStatus(userId, status);
        return ResponseEntity.ok("Set new user status successfully");
    }

//    @PutMapping("/{userId}")
//    @Operation(summary = "Update user information")
//    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User user) {
//        userService.updateUser(user);
//        return ResponseEntity.ok("Update user successfully");
//    }
    @GetMapping("/{userId}/posts")
    @Operation(summary = "Get all posts of specific user")
    public ResponseEntity<?> getAllUserPost(@PathVariable("userId") String id){
        List<Post> postList= postService.getAllUserPost(id);
        System.out.println(postList);
        return ResponseEntity.ok(postList);
    }


    @Secured("ROLE_MANAGER")
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a specified user/ Require role: \"MANAGER\"")
    public ResponseEntity<?> DeleteUserById(@PathVariable("userId") String id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("Delete user successfully");
    }

    @GetMapping("/{userId}/balance")
    @Operation(summary = "Get user balance")
    public ResponseEntity<?> getUserBalance(@PathVariable String userId) {
        UserBalanceDto balanceDto = new UserBalanceDto(userService.getUserBalance(userId));
        return ResponseEntity.ok(balanceDto);
    }

    @PatchMapping("/{userId}/profile")
    @Operation(summary = "Update user profile")
    public ResponseEntity<?> updateUserProfile(@PathVariable String userId, @Valid @RequestBody UserProfileDto profile) throws IOException {
        String message = userService.updateUserProfile(userId,profile.getEmail(),
                profile.getPhone(),
                profile.getName(),
                profile.getFullName(),
                profile.getAvatar());
        return ResponseEntity.ok(message);
    }


    @PostMapping("/{userId}/posts/{postId}/payments")
    @Operation(summary = "Create a payment for post of specified user")
    public ResponseEntity<?> updateUserProfile(@PathVariable String userId, @PathVariable String postId, @Valid @RequestBody CreatePaymentDto createPaymentDto) {
        String message = paymentService.createPaymentForUserPost(userId, postId,
                createPaymentDto.getDescription(),
                createPaymentDto.getDay());
        return ResponseEntity.ok(message);
    }


    @GetMapping("/{userId}/recharges")
    @Operation(summary = "Get all recharges of specified user with filter and pagination")
    public ResponseEntity<?> getRechargesByUserId(@RequestParam(required = true) String userId,@RequestParam(required = false) String status,
                                             @Min(5) @RequestParam(defaultValue = "5", required = false) int size,
                                             @Min(1) @RequestParam(defaultValue = "1",required = false) int pageIndex) {
        PaginationDto recharges= rechargeService.getRechargeByUserId(userId,status,size,pageIndex);
        return ResponseEntity.ok(recharges);
    }
    @PutMapping("/{userId}/profile/password")
    @Operation(summary = "Update user password")
    public ResponseEntity<?> updateUserPassword(@PathVariable String userId, @Valid @RequestBody PasswordChangeRequestDto req) {
        String message = userService.updateUserPassword(userId,req.getOldPassword(),req.getNewPassword());
        return ResponseEntity.ok(message);
    }

}
