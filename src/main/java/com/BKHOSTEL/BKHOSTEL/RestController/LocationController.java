package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value ="/location")

public class LocationController {
    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/districts")
    @Operation(summary = "Get districts")
    public ResponseEntity<?> getAllDistricts() throws IOException {
        return ResponseEntity.ok(
                locationService.getAllDistricts()
        );
    }
    @GetMapping("/districts/{id}/wards")
    @Operation(summary = "Get wards in district")
    public ResponseEntity<?> getAllWardsInDistrict(@PathVariable("id") String id) throws IOException {
        return ResponseEntity.ok(
                locationService.getAllWardFromDistrict(id)
        );
    }
}
