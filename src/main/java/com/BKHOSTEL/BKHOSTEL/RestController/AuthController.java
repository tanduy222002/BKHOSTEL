package com.BKHOSTEL.BKHOSTEL.RestController;
import com.BKHOSTEL.BKHOSTEL.Dto.AuthRequest;
import com.BKHOSTEL.BKHOSTEL.Dto.AuthResponse;
import com.BKHOSTEL.BKHOSTEL.Dto.RefreshTokenResponse;

import com.BKHOSTEL.BKHOSTEL.Service.AuthService;
import com.BKHOSTEL.BKHOSTEL.Service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private RefreshTokenService refreshTokenService;
    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService; this.refreshTokenService=refreshTokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Get json web token by authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign in successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid information",
                    content = @Content),
//            @ApiResponse(responseCode = "401", description = "Authorities is not authorized",
//                    content = @Content),
//            @ApiResponse(responseCode = "403", description = "User is not allowed to access this resource",
//                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error",
                    content = @Content)
    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        System.out.println("authenticateUser controller");
        AuthResponse authResponse = authService.authenticateUser(authRequest.getUserName(), authRequest.getPassword());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequest signUpRequest) {
        AuthResponse authResponse = authService.signUp(signUpRequest.getUserName(), signUpRequest.getPassword(), signUpRequest.getRoles());
        return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody String refreshToken) {
        RefreshTokenResponse response= refreshTokenService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

}
