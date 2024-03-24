package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Service.Client.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payments")
@Validated
public class PaymentController {
    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("")
    public ResponseEntity<?> doPaymentProcess(HttpServletRequest req, HttpServletResponse res, @RequestParam @Min(value = 10000,message = "So tien nap toi thieu 10000 vnd") int amount) throws Exception {
        String paymentUrl=paymentService.processPayment(req,res);
        return ResponseEntity.status(302).header("Location", paymentUrl).build();


    }
}
