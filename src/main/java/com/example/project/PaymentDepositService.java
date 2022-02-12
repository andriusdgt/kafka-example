package com.example.project;

import com.example.project.config.kafka.DTOSerializer;
import com.example.project.domain.PaymentDeposit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.project.config.kafka.PaymentDepositProcessor.PAYMENT_DEPOSITED_OUTPUT;

@Service
public class PaymentDepositService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentDepositService.class);

    private final DTOSerializer dtoSerializer;
    private MessageChannel channel;

    @Autowired(required = false)
    public void setChannel(@Qualifier(PAYMENT_DEPOSITED_OUTPUT) MessageChannel channel) {
        this.channel = channel;
    }

    public PaymentDepositService(DTOSerializer dtoSerializer) {
        this.dtoSerializer = dtoSerializer;

        //A trigger for demo purpose only
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        sendDeposit();
                    }
                },
                3000
        );
    }

    public void receiveDeposit(PaymentDeposit paymentDeposit) {
        LOGGER.info("Received paymentDeposit: {}", paymentDeposit);
    }

    public void sendDeposit() {
        PaymentDeposit paymentDeposit = new PaymentDeposit("777", "Euro", BigInteger.valueOf(12000));
        channel.send(dtoSerializer.serialize(paymentDeposit));
    }

}
