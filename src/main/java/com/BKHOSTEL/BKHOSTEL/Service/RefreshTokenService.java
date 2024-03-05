package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.RefreshTokenDaoImpl;
import com.BKHOSTEL.BKHOSTEL.DAO.UserDaoImpl;
import com.BKHOSTEL.BKHOSTEL.Dto.RefreshTokenResponse;
import com.BKHOSTEL.BKHOSTEL.Entity.RefreshToken;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Entity.UserDetail;
import com.BKHOSTEL.BKHOSTEL.Exception.TokenRefreshException;
import com.BKHOSTEL.BKHOSTEL.Exception.UserNotFoundException;
import com.BKHOSTEL.BKHOSTEL.Repository.RefreshTokenRepository;
import com.BKHOSTEL.BKHOSTEL.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${JWT_REFRESH_EXPIRATION}")
    private long JWT_REFRESH_EXPIRATION;

    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;

    private JwtTokenService jwtTokenService;

    private UserDaoImpl userDaoImpl;

    private RefreshTokenDaoImpl refreshTokenDaoImpl;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtTokenService jwtTokenService, UserDaoImpl userDaoImpl, RefreshTokenDaoImpl refreshTokenDaoImpl) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.userDaoImpl = userDaoImpl;
        this.refreshTokenDaoImpl = refreshTokenDaoImpl;
    }




    @Transactional
    public RefreshToken generateRefreshToken(User user) {
        RefreshToken refreshToken =new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(JWT_REFRESH_EXPIRATION));
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        System.out.println("set refresh token successfully");
//        System.out.println("generate refresh token"+user.toString());
        return refreshToken;
    }


    @Transactional
    public RefreshTokenResponse refreshToken(String refreshTokenString){
        System.out.println("start refresh");
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenString).
                orElseThrow(()->new TokenRefreshException("Refresh token is not found in database"));

        System.out.println(refreshToken);

        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            RefreshToken test = refreshTokenDaoImpl.findByToken(refreshTokenString);
            User user = test.getUser();
            user.setRefreshToken(null);
            userDaoImpl.save(user);
//            User user =refreshToken.getUser();
//            user.setRefreshToken(null);
//            userRepository.save(user);
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException("Refresh token was expired");
        }
        String token= jwtTokenService.generateToken(refreshToken.getUser());
        return new RefreshTokenResponse(token, refreshToken.getToken());


    }


}
