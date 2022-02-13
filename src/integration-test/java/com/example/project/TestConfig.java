package com.example.project;

import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public BeanPostProcessor paymentDepositServicePostProcessor() {
        return new ProxiedMockPostProcessor(PaymentDepositService.class);
    }

    /**
     * See https://github.com/spring-projects/spring-boot/issues/7033#issuecomment-393213222 for
     * the rationale behind this. I want real functionality to happen in the proxied
     * {@literal @}StreamListener, but I also want to directly validate that methods were called
     * that I expected
     *
     * @author Phillip Verheyden (phillipuniverse)
     */
    static class ProxiedMockPostProcessor implements BeanPostProcessor {
        private final Class<?> mockedClass;

        public ProxiedMockPostProcessor(Class<?> mockedClass) {
            this.mockedClass = mockedClass;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (mockedClass.isInstance(bean)) {
                return Mockito.mock(mockedClass, AdditionalAnswers.delegatesTo(bean));
            }
            return bean;
        }
    }
}