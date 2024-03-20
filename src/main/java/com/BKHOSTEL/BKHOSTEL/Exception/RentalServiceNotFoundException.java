package com.BKHOSTEL.BKHOSTEL.Exception;

public class RentalServiceNotFoundException extends RuntimeException {
    public RentalServiceNotFoundException() {
        super("Rental service was not found");

    }

    public RentalServiceNotFoundException(String message) {
        super(message);
    }
}
