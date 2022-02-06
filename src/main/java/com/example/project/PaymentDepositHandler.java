package com.example.project;

import com.example.project.config.kafka.DTOSerializer;
import com.example.project.config.kafka.PaymentDepositProcessor;
import com.example.project.config.kafka.PaymentDepositedDeserializer;
import com.example.project.domain.PaymentDeposit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.project.config.kafka.PaymentDepositProcessor.PAYMENT_DEPOSITED_INPUT;
import static com.example.project.config.kafka.PaymentDepositProcessor.PAYMENT_DEPOSITED_OUTPUT;

@Component
@EnableBinding(PaymentDepositProcessor.class)
public class PaymentDepositHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentDepositedDeserializer.class);

    private final DTOSerializer dtoSerializer;
    private MessageChannel channel;

    @Autowired(required = false)
    public void setChannel(@Qualifier(PAYMENT_DEPOSITED_OUTPUT) MessageChannel channel) {
        this.channel = channel;
    }

    public PaymentDepositHandler(DTOSerializer dtoSerializer) {
        this.dtoSerializer = dtoSerializer;

        //A trigger for demo purpose only
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        makeAnExampleDeposit();
                    }
                },
                3000
        );
    }

    @StreamListener(PAYMENT_DEPOSITED_INPUT)
    public void handle(PaymentDeposit paymentDeposit) {
        LOGGER.info("Received paymentDeposit: {}", paymentDeposit);
    }

    public void makeAnExampleDeposit() {
        PaymentDeposit paymentDeposit = new PaymentDeposit("666", "Euro", BigInteger.ONE);
        channel.send(dtoSerializer.serialize(paymentDeposit));
    }


}
