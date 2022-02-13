package com.example.project;

import com.example.project.domain.PaymentDeposit;
import com.example.project.kafka.KafkaUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigInteger;
import java.util.Map;

import static com.example.project.config.kafka.PaymentDepositProcessor.PAYMENT_DEPOSITED_INPUT;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@IntegrationTest
@EmbeddedKafka(ports = 9094, topics = PAYMENT_DEPOSITED_INPUT)
@ActiveProfiles({"test"})
@TestPropertySource(properties = {
        "spring.cloud.stream.kafka.binder.brokers=localhost:9094",
        "spring.cloud.stream.bindings.payment-deposited-input.consumer.concurrency=1",
        "spring.cloud.stream.kafka.bindings.payment-deposited-input.consumer.configuration.max.poll.records=1"
})
@DirtiesContext
class PaymentDepositedKafkaTest {

    private static final String KAFKA_TOPIC = PAYMENT_DEPOSITED_INPUT;

    @Autowired
    private KafkaUtils kafkaUtils;

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    @Autowired
    private PaymentDepositService paymentDepositService;

    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setUp() {

        Map<String, Object> producerProperties = KafkaTestUtils.producerProps(kafkaEmbedded);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProperties));
        kafkaTemplate.setDefaultTopic(KAFKA_TOPIC);

        kafkaUtils.waitUntilPartitionsAssigned();
    }

    @Test
    void sendsPaymentDeposit() {
        PaymentDeposit expectedPaymentDeposit = new PaymentDeposit("777", "Euro", BigInteger.valueOf(12000));

        kafkaTemplate.send(new ProducerRecord<>(KAFKA_TOPIC, toJsonNode(expectedPaymentDeposit).toString()));

        verify(paymentDepositService, timeout(5000)).receiveDeposit(expectedPaymentDeposit);
    }

    private JsonNode toJsonNode(PaymentDeposit pd) {
        return JsonNodeFactory.instance.objectNode()
                .put("account_id", pd.getAccountId())
                .put("currency", pd.getCurrency())
                .put("sum", pd.getSum());
    }

}
