package com.example.project;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {KafkaExampleApplication.class, TestConfig.class})
@ActiveProfiles("test")
public @interface IntegrationTest {
}
