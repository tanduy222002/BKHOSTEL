package com.BKHOSTEL.BKHOSTEL.Service;

import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {
    public int generateOtp(){
        return (int) ((Math.random() * (999999 - 100000)) + 100000);
    }

}
