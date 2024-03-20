package com.BKHOSTEL.BKHOSTEL.Exception;

public class UserIdMisMatchException extends RuntimeException{
    public UserIdMisMatchException() {
        super("Id mismatch with this account");
    }

    public UserIdMisMatchException(String message) {
        super(message);
    }
}
