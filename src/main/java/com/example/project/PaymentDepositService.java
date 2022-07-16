package com.example.project;

import com.example.project.domain.PaymentDeposit;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Currency;

import static com.example.project.config.kafka.PaymentDepositProcessor.PAYMENT_DEPOSITED_OUTPUT;

@Service
public class PaymentDepositService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentDepositService.class);

    private final KafkaTemplate<String, PaymentDeposit> kafkaTemplate;

    public PaymentDepositService(KafkaTemplate<String, PaymentDeposit> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void receiveDeposit(PaymentDeposit paymentDeposit) {
        LOGGER.info("Received paymentDeposit, processing: {}", paymentDeposit);
        processDeposit();
        LOGGER.info("Processed paymentDeposit: {}", paymentDeposit);
    }

    public void sendDeposit() {
        PaymentDeposit pd = new PaymentDeposit("777", Currency.getInstance("EUR"), BigInteger.valueOf(12000));

        kafkaTemplate.send(new ProducerRecord<>(PAYMENT_DEPOSITED_OUTPUT, pd));

        LOGGER.info("Sent paymentDeposit: {}", pd);
    }

    private void processDeposit() {
        // Do some heavy lifting
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

}
