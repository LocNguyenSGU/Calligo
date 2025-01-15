package com.example.notificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    // Lắng nghe từ Kafka topic "notifications"
    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void listen(String message) {
        // Khi nhận được thông báo từ Kafka, in ra message
        System.out.println("Notification received: " + message);
    }
}
