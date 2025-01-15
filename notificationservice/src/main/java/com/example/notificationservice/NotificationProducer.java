package com.example.notificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
@EnableKafka
public class NotificationProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "notifications";

    public void sendNotification(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Sent notification: " + message);
    }
}
