package com.BKHOSTEL.BKHOSTEL.Service.Client;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ClientRechargeService {
    public String processRecharge(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

    public String rechargeReturnUrl(HttpServletRequest req, HttpServletResponse res);

    public void saveRecharge(HttpServletRequest req, HttpServletResponse res);
}
