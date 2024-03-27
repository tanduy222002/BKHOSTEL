package com.BKHOSTEL.BKHOSTEL.Exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException() {
        super("balance is not enought to pay for this payment");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
