package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.Entity.Role;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Entity.UserDetail;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtTokenService {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    //Thời gian có hiệu lực của chuỗi jwt
    @Value("${JWT_EXPIRATION}")
    private long JWT_EXPIRATION;
//    private final long JWT_EXPIRATION = 1L;



    // Tạo ra jwt từ thông tin user
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
        String role = user.getRole().getName();

        // Tạo chuỗi json web token từ username của user.
        return Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(now)
                .claim("role", role)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUserNameFromJWT(String token) {

            Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
                String userName = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build().parseSignedClaims(token)
                    .getPayload().getSubject();

            return userName;
    }






    public boolean validateToken(String authToken) throws ServletException {
        try {
            System.out.println("Try to validate");
            Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
            System.out.println(key);
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build().parseSignedClaims(authToken);

            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        } catch(Exception e){
            System.out.println("Exception");
        }
        return false;
    }
}