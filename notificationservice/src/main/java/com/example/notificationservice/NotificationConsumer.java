package com.example.notificationservice;

import org.springframework.kafka.annotation.KafkaListener;

public class NotificationConsumer {
    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("Received notification: " + message);
        // Xử lý thông báo, ví dụ gửi email hoặc hiển thị trên UI
    }
}
