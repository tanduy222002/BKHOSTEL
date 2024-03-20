package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.Dto.AuthResponse;
import com.BKHOSTEL.BKHOSTEL.Entity.RefreshToken;
import com.BKHOSTEL.BKHOSTEL.Entity.Role;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Entity.UserDetail;
import com.BKHOSTEL.BKHOSTEL.Exception.UserNameExistsException;
import com.BKHOSTEL.BKHOSTEL.Repository.RoleRepository;
import com.BKHOSTEL.BKHOSTEL.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    JwtTokenService jwtTokenService;

    RefreshTokenService refreshTokenService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtTokenService jwtTokenService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
    }



    @Transactional
    public AuthResponse authenticateUser (String userName, String password){
        System.out.println("authenticateUser");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));

        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetail userDetails = (UserDetail) authentication.getPrincipal();
        User user = userDetails.getUser();
        String jwt = jwtTokenService.generateToken(user);
        if(user.getRefreshToken()==null){
            refreshTokenService.generateRefreshToken(user);
            System.out.println(user);
        }
        String role = userDetails.getAuthorities().toString();
        return new AuthResponse(
                user.getId().toString(),
                jwt,
                role,
                user.getRefreshToken().getToken(),
                "Create successfully");
    }

    @Transactional
    public AuthResponse signUp(String userName, String password, String email, String phone){
        if (userRepository.existsByUserName(userName)){
            throw new UserNameExistsException("Username already exists");
        }
        User user =new User(userName, encoder.encode(password),email,phone);


        String role = "ROLE_USER";
        Optional<Role> optionalValue = Optional.ofNullable(roleRepository.findByName(role));
        Role result = optionalValue.orElseGet(()->{
            Role newRole = new Role(role);
            roleRepository.save(newRole);
            return newRole;
            });

        user.addRole(result);
        System.out.println("before save user: "+user);
        user=userRepository.save(user);
        System.out.println("after save user:" +user);

        result.addUser(user);
        roleRepository.save(result);

        System.out.println("generate token");
        String jwt = jwtTokenService.generateToken(user);
        System.out.println("generated refresh token");
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user);
        System.out.println("after generated refresh token");
        return new AuthResponse(user.getId().toString()
                ,jwt
                ,result.getName(),
                refreshToken.getToken(),
                "Signup successfully");

    }
    public void checkValidRole(List<String> roles){
        List<String> acceptedRole = Arrays.asList("ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER");
        for (String role : roles){
            if(!acceptedRole.contains(role)){
                throw new RuntimeException("Invalid role: " + role);
            }
        }
    }
}
