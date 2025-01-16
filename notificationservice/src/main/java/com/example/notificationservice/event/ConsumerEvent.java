package com.example.notificationservice.event;

import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerEvent {
    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000L, multiplier = 2),
            autoCreateTopics = "true",
            include = {RetryException.class, RuntimeException.class},
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "notifications", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        // Khi nhận được thông báo từ Kafka, in ra message
        System.out.println("Notification received: " + message);
        throw new RuntimeException("Loi tu tao");
    }
    @DltHandler
    void processDltMessage(@Payload String message) {
        System.out.println("DLT receive message: " + message);
    }

}
