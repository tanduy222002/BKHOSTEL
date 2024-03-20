package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Dto.PasswordChangeRequestDto;
import com.BKHOSTEL.BKHOSTEL.Dto.UserProfileDto;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Service.RentalServiceService;
import com.BKHOSTEL.BKHOSTEL.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping(value = "/services")
//@SecurityRequirement(name = "bearerAuth")
//@Validated
public class RentalServiceController {

    private RentalServiceService rentalService;

    @Autowired
    public RentalServiceController(RentalServiceService rentalService) {
        this.rentalService = rentalService;
    }
    @PostMapping("")
    public ResponseEntity<?> createService(@Valid @RequestBody RentalService rentalService) throws IOException {
        String message=this.rentalService.createService(rentalService);
        return ResponseEntity.ok(message);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllService()  {
        List<RentalService> services=rentalService.getAllServices();
        return ResponseEntity.ok(services);
    }






}
