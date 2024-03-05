package com.BKHOSTEL.BKHOSTEL.Exception;

public class UserNameExistsException extends RuntimeException {
    public UserNameExistsException() {
        super("Username already exists");
    }
    public UserNameExistsException(String message) {
        super(message);
    }

}
