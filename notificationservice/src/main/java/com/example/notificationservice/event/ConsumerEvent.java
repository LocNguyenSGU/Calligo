package com.example.notificationservice.event;

import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerEvent {
    @KafkaListener(topics = "notifications", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        // Khi nhận được thông báo từ Kafka, in ra message
        System.out.println("Notification received: " + message);
    }

}
