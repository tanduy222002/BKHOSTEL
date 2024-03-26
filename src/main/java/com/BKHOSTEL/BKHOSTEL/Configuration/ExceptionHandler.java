package com.BKHOSTEL.BKHOSTEL.Configuration;

import com.BKHOSTEL.BKHOSTEL.Dto.ErrorResponseDto;
import com.BKHOSTEL.BKHOSTEL.Exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.validation.ConstraintViolationException;
import org.apache.el.parser.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {
        @org.springframework.web.bind.annotation.ExceptionHandler
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
//            List<String> details = new ArrayList<>();
//            for (ObjectError error : e.getBindingResult().getAllErrors()) {
//                details.add(error.getDefaultMessage());
//            }
            String detail = String.valueOf(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return new ResponseEntity<>(new ErrorResponseDto(new Date(),
                        HttpStatus.BAD_REQUEST.toString(),
                        detail),
                    HttpStatus.BAD_REQUEST);

        }
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleContrainViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage()),
                HttpStatus.BAD_REQUEST);

    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),HttpStatus.NOT_FOUND.toString(),e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleRefreshTokenException(TokenRefreshException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleRuntimeException(AuthenticationException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleExpiredToken(ExpiredJwtException e) {
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),HttpStatus.BAD_REQUEST.toString(),"JWT expired"),
                HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleInvalidToken(MalformedJwtException e) {
        return new ResponseEntity("Invalid JWT Token", HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUnsupportedToken(UnsupportedJwtException e) {
        return new ResponseEntity("Unsupported JWT Token", HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleIncorrectOldPassword(IncorrectPasswordException e) {
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),HttpStatus.BAD_REQUEST.toString(),e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }



    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleIllegalArgumentToken(IllegalArgumentException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleIoException(IOException e) {
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleExistsAccount(UserNameExistsException e) {
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),HttpStatus.BAD_REQUEST.toString(),e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleIdMisMatchException(UserIdMisMatchException e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),HttpStatus.FORBIDDEN.toString(),e.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleDefaultException(Exception e) {
        System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleServiceNotFoundException(RentalServiceNotFoundException e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(new Date(),HttpStatus.NOT_FOUND.toString(),e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
















}
