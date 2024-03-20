package com.BKHOSTEL.BKHOSTEL.RestController;
import com.BKHOSTEL.BKHOSTEL.Dto.AuthRequest;
import com.BKHOSTEL.BKHOSTEL.Dto.AuthResponse;
import com.BKHOSTEL.BKHOSTEL.Dto.RefreshTokenResponse;

import com.BKHOSTEL.BKHOSTEL.Dto.SignUpRequest;
import com.BKHOSTEL.BKHOSTEL.Entity.District;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Service.AuthService;
import com.BKHOSTEL.BKHOSTEL.Service.Client.ImageService;
import com.BKHOSTEL.BKHOSTEL.Service.LocationService;
import com.BKHOSTEL.BKHOSTEL.Service.RefreshTokenService;
import com.BKHOSTEL.BKHOSTEL.Service.RentalServiceService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@Validated
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthController {
    private ImageService imageService;
    private LocationService locationService;
    private AuthService authService;

    private RentalServiceService rentalService;
    private RefreshTokenService refreshTokenService;
    @Autowired
    public AuthController(RentalServiceService rentalService,LocationService locationService, ImageService imageService, AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService; this.refreshTokenService=refreshTokenService;
        this.imageService = imageService;
        this.locationService = locationService;
        this.rentalService = rentalService;
    }

    @PostMapping("/sign-in")
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
        AuthResponse authResponse = authService.authenticateUser(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        AuthResponse authResponse = authService.signUp(signUpRequest.getUserName(),
                signUpRequest.getPassword(),
                signUpRequest.getEmail(),
                signUpRequest.getPhone());
        return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody String refreshToken) {
        RefreshTokenResponse response= refreshTokenService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test_img")
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        imageService.upLoadImageWithFile(file);
        return "ok";
    }

    @PostMapping("/test_img_64")
    public String uploadImageBase64(@RequestBody String base64Img) throws IOException {
        imageService.upLoadImageWithBase64(base64Img);
        return "ok";
    }
    @GetMapping("test_districts")
    public List<District> getDistrict() throws IOException {
        return locationService.getAllDistricts();
    }

    @PostMapping("test_services")
    public ResponseEntity<?> createService(@Valid @RequestBody RentalService rentalService) throws IOException {
        String message=this.rentalService.createService(rentalService);
        return ResponseEntity.ok(message);
    }

}
