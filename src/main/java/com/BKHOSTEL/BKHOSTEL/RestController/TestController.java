package com.BKHOSTEL.BKHOSTEL.RestController;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping(value = "/test")
@SecurityRequirement(name = "bearerAuth")
public class TestController {

    @GetMapping("")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Test for cloud deploy");
    }
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/admin")
//    public void testAdmin(){
//        System.out.println("Admin is tested");
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/user")
//    public void testUser(){
//        System.out.println("User is tested");
//    }
//
//    @PreAuthorize("hasRole('Manager')")
//    @GetMapping("/manager")
//    public void testManager(){
//        System.out.println("manager is tested");
//    }
}
