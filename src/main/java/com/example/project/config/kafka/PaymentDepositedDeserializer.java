package com.example.project.config.kafka;

import com.example.project.domain.PaymentDeposit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PaymentDepositedDeserializer implements Deserializer<PaymentDeposit> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentDepositedDeserializer.class);

    private final ObjectMapper objectMapper;

    public PaymentDepositedDeserializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public PaymentDeposit deserialize(String topic, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, PaymentDeposit.class);
        } catch (IOException e) {
            LOGGER.error("Failed to deserialize PaymentDeposit. Topic: " + topic, e);
        }
        return null;
    }

}
