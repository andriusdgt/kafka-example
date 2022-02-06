package com.example.project.config.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class DTOSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DTOSerializer.class);

    private final ObjectMapper objectMapper;

    public DTOSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> Message<byte[]> serialize(T message) {
        try {
            return MessageBuilder
                    .withPayload(objectMapper.writeValueAsString(message).getBytes(StandardCharsets.UTF_8))
                    .build();
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize: {}", message);
        }

        return null;
    }


}
