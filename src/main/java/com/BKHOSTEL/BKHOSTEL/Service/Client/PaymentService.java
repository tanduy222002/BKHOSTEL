package com.BKHOSTEL.BKHOSTEL.Service.Client;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PaymentService {
    public String processPayment(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
}
