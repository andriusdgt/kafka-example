package com.example.project.config.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentDepositProcessor {

    String PAYMENT_DEPOSITED_INPUT = "payment-deposited";
    String PAYMENT_DEPOSITED_OUTPUT = "payment-deposited";

    @Input(PAYMENT_DEPOSITED_INPUT)
    SubscribableChannel paymentDepositInput();

    @Output(PAYMENT_DEPOSITED_OUTPUT)
    MessageChannel paymentDepositOutput();

}