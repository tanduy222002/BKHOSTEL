package com.BKHOSTEL.BKHOSTEL.Exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("resource is not found");
    }
    public NotFoundException(String message) {
        super(message);
    }
}
