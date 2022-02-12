package com.example.project;

import com.example.project.config.kafka.PaymentDepositProcessor;
import com.example.project.domain.PaymentDeposit;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import static com.example.project.config.kafka.PaymentDepositProcessor.PAYMENT_DEPOSITED_INPUT;

@Component
@EnableBinding(PaymentDepositProcessor.class)
public class PaymentDepositHandler {

    private final PaymentDepositService paymentDepositService;

    public PaymentDepositHandler(PaymentDepositService paymentDepositService) {
        this.paymentDepositService = paymentDepositService;
    }

    @StreamListener(PAYMENT_DEPOSITED_INPUT)
    public void handle(PaymentDeposit paymentDeposit) {
        paymentDepositService.receiveDeposit(paymentDeposit);
    }

}
