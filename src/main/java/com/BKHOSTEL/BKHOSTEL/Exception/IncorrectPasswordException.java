package com.BKHOSTEL.BKHOSTEL.Exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("Old password is incorrect");
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
