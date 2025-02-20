package com.example.chatservice.observe;

import com.example.chatservice.entity.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageSubject {
    private final Map<Integer, List<MessageObserve>> observerMap = new ConcurrentHashMap<>();

    public void addObserver(Integer conversationId, MessageObserve observer) {
        observerMap.computeIfAbsent(conversationId, k -> new CopyOnWriteArrayList<>()).add(observer);
    }

    public void removeObserver(Integer conversationId, MessageObserve observer) {
        List<MessageObserve> observers = observerMap.get(conversationId);
        if (observers != null) {
            observers.remove(observer);
            if (observers.isEmpty()) {
                observerMap.remove(conversationId);
            }
        }
    }

    public void notifyObservers(Integer conversationId, Message message) {
        List<MessageObserve> observers = observerMap.get(conversationId);
        if (observers != null) {
            observers.forEach(observer -> observer.onMessageReceived(message));
        }
    }
}
