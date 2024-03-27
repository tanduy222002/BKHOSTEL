package com.BKHOSTEL.BKHOSTEL.Exception;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException() {
        super("invalid request");
    }
    public InvalidRequestException(String message) {
        super(message);
    }
}
