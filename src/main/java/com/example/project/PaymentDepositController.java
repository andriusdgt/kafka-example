package com.example.project;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment/deposit")
public class PaymentDepositController {

    private final PaymentDepositService paymentDepositService;

    public PaymentDepositController(PaymentDepositService paymentDepositService) {
        this.paymentDepositService = paymentDepositService;
    }

    @PostMapping("/send")
    public void sendDeposit() {
        paymentDepositService.sendDeposit();
    }

}
