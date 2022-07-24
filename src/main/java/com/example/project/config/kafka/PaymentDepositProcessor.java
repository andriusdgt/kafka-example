package com.example.project.config.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentDepositProcessor {

    String PAYMENT_DEPOSITED_TOPIC_NAME = "payment-deposited";
    String PAYMENT_DEPOSITED_INPUT_PROCESSABLE = "payment-deposited-processable";
    String PAYMENT_DEPOSITED_INPUT_NOTIFICATION = "payment-deposited-notification";

    @Input(PAYMENT_DEPOSITED_INPUT_PROCESSABLE)
    SubscribableChannel paymentDepositInput();

    @Input(PAYMENT_DEPOSITED_INPUT_NOTIFICATION)
    SubscribableChannel paymentDepositInputNotification();

}
