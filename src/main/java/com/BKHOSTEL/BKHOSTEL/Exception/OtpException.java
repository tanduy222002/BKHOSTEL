package com.BKHOSTEL.BKHOSTEL.Exception;

public class OtpException extends RuntimeException{
    public OtpException() {
        super("Otp is not valid");
    }

    public OtpException(String message) {
        super(message);
    }
}
