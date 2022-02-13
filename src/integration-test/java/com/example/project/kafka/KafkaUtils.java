package com.example.project.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class KafkaUtils {

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    public void waitUntilPartitionsAssigned() {
        for (MessageListenerContainer listenerContainer : kafkaListenerEndpointRegistry.getListenerContainers())
            if (kafkaEmbedded.getTopics().containsAll(getTopics(listenerContainer)))
                ContainerTestUtils.waitForAssignment(listenerContainer, kafkaEmbedded.getPartitionsPerTopic());
    }

    private List<String> getTopics(MessageListenerContainer listenerContainer) {
        return Arrays.asList(
                Optional.ofNullable(listenerContainer.getContainerProperties().getTopics()).orElse(new String[0])
        );
    }

}
