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

    public void notifyObservers(String conversationId, Message message) {
        List<MessageObserve> observers = observerMap.get(conversationId);
        
        if (observers != null) {
            observers.forEach(observer -> observer.onMessageReceived(message));
        }
    }
}
