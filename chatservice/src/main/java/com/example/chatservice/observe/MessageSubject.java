package com.example.chatservice.observe;

import com.example.chatservice.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MessageSubject {
    private final Map<String, List<MessageObserve>> observerMap = new ConcurrentHashMap<>();

    public void addObserver(String conversationId, MessageObserve observer) {

        observerMap.computeIfAbsent(conversationId, k -> new CopyOnWriteArrayList<>()).add(observer);

        for (Map.Entry<String, List<MessageObserve>> entry : observerMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Observers size: " + entry.getValue().size());
        }
    }

    public void removeObserver(String conversationId, MessageObserve observer) {
        List<MessageObserve> observers = observerMap.get(conversationId);
        if (observers != null) {
            observers.remove(observer);
            if (observers.isEmpty()) {
                observerMap.remove(conversationId);
            }
        }
    }

    public void notifyObservers(String conversationId, Message message, String senderSessionId) {
        System.out.println("Conversation id lay: " + conversationId);
        List<MessageObserve> observers = observerMap.get(conversationId);
        System.out.println("📨 Gửi tin nhắn: " + message.getContent());
        System.out.println(observers);

        if (observers != null) {
            observers.forEach(observer -> {
                System.out.println("tin nhắn lặp qua: " + observer.getSocketSessionId());
                // So sánh sessionId để không gửi lại cho sender
                if (!observer.getSocketSessionId().equals(senderSessionId)) {
                    System.out.println("tin nhắn gửi cho: " + observer.getSocketSessionId());
                    observer.onMessageReceived(message);
                }
            });
        }
    }
}
