package com.BKHOSTEL.BKHOSTEL.RestController;
import com.BKHOSTEL.BKHOSTEL.Dto.*;

import com.BKHOSTEL.BKHOSTEL.Entity.District;
import com.BKHOSTEL.BKHOSTEL.Entity.Otp;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Service.*;
import com.BKHOSTEL.BKHOSTEL.Service.Client.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@Validated
@RequestMapping(value = "/auth")
public class AuthController {
    private ImageService imageService;
    private LocationService locationService;
    private AuthService authService;

    private RentalServiceService rentalService;
    private RefreshTokenService refreshTokenService;

    private ForgotPasswordService forgotPasswordService;
    @Autowired
    public AuthController(ImageService imageService, LocationService locationService, AuthService authService, RentalServiceService rentalService, RefreshTokenService refreshTokenService, ForgotPasswordService forgotPasswordService) {
        this.imageService = imageService;
        this.locationService = locationService;
        this.authService = authService;
        this.rentalService = rentalService;
        this.refreshTokenService = refreshTokenService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Get json web token by authentication")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Sign in successfully",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = AuthResponse.class)) }),
//            @ApiResponse(responseCode = "400", description = "Invalid information",
//                    content = @Content),
////            @ApiResponse(responseCode = "401", description = "Authorities is not authorized",
////                    content = @Content),
////            @ApiResponse(responseCode = "403", description = "User is not allowed to access this resource",
////                    content = @Content),
//            @ApiResponse(responseCode = "404", description = "User is not found",
//                    content = @Content),
//            @ApiResponse(responseCode = "500", description = "Internal  Server Error",
//                    content = @Content)
//    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        System.out.println("authenticateUser controller");
        AuthResponse authResponse = authService.authenticateUser(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Create a new account")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        AuthResponse authResponse = authService.signUp(signUpRequest.getUserName(),
                signUpRequest.getPassword(),
                signUpRequest.getEmail(),
                signUpRequest.getPhone());
        return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/refresh-token")
    @Operation(summary = "Get a new refresh token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody String refreshToken) {
        RefreshTokenResponse response= refreshTokenService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/otps")
    @Operation(summary = "Send request otp to get otp code by sending email address")
    public ResponseEntity<?> requestOtp(@Valid @RequestBody GenerateOtpRequestDto otpRequest) throws Exception {
        Otp otp=forgotPasswordService.generateOtp(otpRequest.getIdentifier(),otpRequest.getUserName());
        GenerateOtpResponseDto res = new GenerateOtpResponseDto(otp.getExpiredDate(),"Otp is sent to email address: "+otpRequest.getIdentifier());
        return ResponseEntity.ok(res);
    }




    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/password")
    @Operation(summary = "Renew password with token got from verify otp")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto resetPasswordDto) {
        String message = forgotPasswordService.resetPassword(
                resetPasswordDto.getNewPassword()
        );
        ResetPasswordResponseDto res = new ResetPasswordResponseDto(message);
        return ResponseEntity.ok(res);

    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/log-out")
    @Operation(summary = "log out account")
    public ResponseEntity<?> logout() {
        authService.logOut();
        return ResponseEntity.ok("Logout successfully");

    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Verify otp that sent to email and get token using for reset password")
    public ResponseEntity<?> verifyOtp(@RequestBody @Valid VerifyOtpRequestDto reqDto ) {
        String token= forgotPasswordService.verifyOtp(reqDto.getCode(), reqDto.getIdentifier());
        return ResponseEntity.ok(new VerifyOtpResponseDto(token));

    }


}
