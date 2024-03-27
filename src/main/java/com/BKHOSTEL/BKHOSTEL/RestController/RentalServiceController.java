package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Dto.PasswordChangeRequestDto;
import com.BKHOSTEL.BKHOSTEL.Dto.UpdateRentalServiceDto;
import com.BKHOSTEL.BKHOSTEL.Dto.UserProfileDto;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Service.RentalServiceService;
import com.BKHOSTEL.BKHOSTEL.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping(value = "/services")
//@SecurityRequirement(name = "bearerAuth")
@Validated
public class RentalServiceController {

    private RentalServiceService rentalService;

    @Autowired
    public RentalServiceController(RentalServiceService rentalService) {
        this.rentalService = rentalService;
    }
    @PostMapping("")
    @Secured("ROLE_MANAGER")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a rental service / Require role: \"MANAGER\"")
    public ResponseEntity<?> createService(@Valid @RequestBody RentalService rentalService) throws IOException {
        String message=this.rentalService.createService(rentalService);
        return ResponseEntity.ok(message);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{serviceId}")
    @Secured("ROLE_MANAGER")
    @Operation(summary = "Update a rental service / Require role: \"MANAGER\"")
    public ResponseEntity<?> updateValue(@PathVariable String serviceId,@Valid @RequestBody UpdateRentalServiceDto service) {
        RentalService updatedService = this.rentalService.updateValues(serviceId,service.getCategory(),
                service.getDailyPrice(),service.getWeeklyPrice(),
                service.getMonthlyPrice(),service.getDesc(),
                service.getSuitableFor());
        return ResponseEntity.ok(updatedService);
    }
    @GetMapping("")
    @Operation(summary = "Get all rental services")
    public ResponseEntity<?> getAllService()  {
        List<RentalService> services=rentalService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{serviceId}")
    @Secured("ROLE_MANAGER")
    @Operation(summary = "Update a rental service / Require role: \"MANAGER\"")
    public ResponseEntity<?> updateValue(@PathVariable String serviceId) {
        rentalService.deleteById(serviceId);
        return ResponseEntity.ok("Delete service successfully");
    }






}
