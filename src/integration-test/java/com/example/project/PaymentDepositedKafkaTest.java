package com.example.project;

import com.example.project.domain.PaymentDeposit;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigInteger;
import java.util.Currency;
import java.util.Map;

import static com.example.project.config.kafka.PaymentDepositProcessor.PAYMENT_DEPOSITED_INPUT;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ActiveProfiles({"test"})
@TestPropertySource(properties = {
        "spring.cloud.stream.bindings.payment-deposited-input.consumer.concurrency=1",
        "spring.cloud.stream.kafka.bindings.payment-deposited-input.consumer.configuration.max.poll.records=1"
})
@DirtiesContext
@SpringBootTest
class PaymentDepositedKafkaTest {

    private static final String KAFKA_TOPIC = PAYMENT_DEPOSITED_INPUT;

    @SpyBean
    private PaymentDepositService paymentDepositService;

    @SpyBean
    private PaymentDepositHandler paymentDepositHandler;

    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setUp() {
        Map<String, Object> producerProperties = KafkaTestUtils.producerProps("localhost:9092");
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProperties));
        kafkaTemplate.setDefaultTopic(KAFKA_TOPIC);
    }

    @Test
    void receivesPaymentDeposit() {
        PaymentDeposit expectedPaymentDeposit =
                new PaymentDeposit("778", Currency.getInstance("EUR"), BigInteger.valueOf(12000));

        kafkaTemplate.send(new ProducerRecord<>(KAFKA_TOPIC, toJsonNode(expectedPaymentDeposit).toString()));

        verify(paymentDepositService, timeout(1000)).receiveDeposit(expectedPaymentDeposit);
    }

    @Test
    void sendsPaymentDeposit() {
        PaymentDeposit expectedPaymentDeposit =
                new PaymentDeposit("777", Currency.getInstance("EUR"), BigInteger.valueOf(12000));

        paymentDepositService.sendDeposit();

        verify(paymentDepositHandler, timeout(1000)).handle(expectedPaymentDeposit);
    }

    private JsonNode toJsonNode(PaymentDeposit pd) {
        return JsonNodeFactory.instance.objectNode()
                .put("account_id", pd.getAccountId())
                .put("currency", pd.getCurrency().toString())
                .put("sum", pd.getSum());
    }

}
