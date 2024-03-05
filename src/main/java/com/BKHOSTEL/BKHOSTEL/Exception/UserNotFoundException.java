package com.BKHOSTEL.BKHOSTEL.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("user is not found");
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}
