package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Anotation.ValidStatus;
import com.BKHOSTEL.BKHOSTEL.Dto.ProcessPaymentResponseDto;
import com.BKHOSTEL.BKHOSTEL.Dto.RechargePaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Service.Client.ClientRechargeService;
import com.BKHOSTEL.BKHOSTEL.Service.RechargeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/recharges")
@Validated
@SecurityRequirement(name = "bearerAuth")
public class RechargeController {
    private ClientRechargeService clientRechargeService;
    private RechargeService rechargeService;

    @Autowired
    public RechargeController(ClientRechargeService clientRechargeService, RechargeService rechargeService) {
        this.clientRechargeService = clientRechargeService;
        this.rechargeService = rechargeService;
    }

    @ResponseBody
    @GetMapping("/payment_url")
    public ResponseEntity<?> doPaymentProcess(HttpServletRequest req, HttpServletResponse res, @RequestParam @Min(value = 10000,message = "So tien nap toi thieu 10000 vnd") int amount) throws Exception {
        String paymentUrl= clientRechargeService.processRecharge(req,res);
        return ResponseEntity.ok(new ProcessPaymentResponseDto(paymentUrl));
    }

    @ResponseBody
    @GetMapping("")
    public ResponseEntity<?> getAllRecharges(@RequestParam(required = false) String userId,@RequestParam(required = false) String status,
                                             @Min(5) @RequestParam(defaultValue = "5", required = false) int size,
                                             @Min(1) @RequestParam(defaultValue = "1",required = false) int pageIndex) {
        RechargePaginationDto recharges= rechargeService.getAllRecharge(userId,status,size,pageIndex);
        return ResponseEntity.ok(recharges);
    }




    @GetMapping("/result")
    public String doPaymentProcess(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String resultHtmlPath = clientRechargeService.rechargeReturnUrl(req,res);
        return resultHtmlPath;
    }

    @GetMapping("/ipn_url")
    public void updateRechargeProcess(HttpServletRequest req, HttpServletResponse res) throws Exception {
        clientRechargeService.saveRecharge(req,res);
    }

}
