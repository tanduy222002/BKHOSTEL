package com.BKHOSTEL.BKHOSTEL.Exception;

public class RechargeFailException extends RuntimeException{
    public RechargeFailException() {
        super("There was an problem when trying to do recharge");
    }

    public RechargeFailException(String message) {
        super(message);
    }
}
