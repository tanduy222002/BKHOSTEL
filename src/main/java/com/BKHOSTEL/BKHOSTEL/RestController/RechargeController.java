package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Dto.ProcessPaymentResponseDto;
import com.BKHOSTEL.BKHOSTEL.Service.Client.RechargeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/recharges")
@Validated
@SecurityRequirement(name = "bearerAuth")
public class RechargeController {
    private RechargeService rechargeService;

    @Autowired
    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @ResponseBody
    @GetMapping("")
    public ResponseEntity<?> doPaymentProcess(HttpServletRequest req, HttpServletResponse res, @RequestParam @Min(value = 10000,message = "So tien nap toi thieu 10000 vnd") int amount) throws Exception {
        String paymentUrl= rechargeService.processRecharge(req,res);
        return ResponseEntity.ok(new ProcessPaymentResponseDto(paymentUrl));
    }

    @GetMapping("/result")
    public String doPaymentProcess(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String resultHtmlPath = rechargeService.rechargeReturnUrl(req,res);
        return resultHtmlPath;
    }

    @GetMapping("/ipn_url")
    public void updateRechargeProcess(HttpServletRequest req, HttpServletResponse res) throws Exception {
        rechargeService.saveRecharge(req,res);
    }

}
