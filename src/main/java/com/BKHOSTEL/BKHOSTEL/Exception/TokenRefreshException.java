package com.BKHOSTEL.BKHOSTEL.Exception;

public class TokenRefreshException extends RuntimeException{
    public TokenRefreshException() {
        super("Token Refresh Exception");
    }

    public TokenRefreshException(String message) {
        super(message);
    }
}
