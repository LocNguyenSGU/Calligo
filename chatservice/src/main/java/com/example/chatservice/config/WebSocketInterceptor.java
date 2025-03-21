package com.example.chatservice.config;

import com.example.chatservice.observe.Impl.WebSocketMessageObserve;
import com.example.chatservice.observe.MessageObserve;
import com.example.chatservice.observe.MessageSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageSubject messageSubject;

    // Quản lý sessionId -> observer instance
    private final Map<String, MessageObserve> sessionObserverMap = new ConcurrentHashMap<>();
    // Quản lý sessionId -> các conversationId đã đăng ký
    private final Map<String, Set<String>> sessionConversationsMap = new ConcurrentHashMap<>();

    @Autowired
    public WebSocketInterceptor(@Lazy SimpMessagingTemplate simpMessagingTemplate,
                                MessageSubject messageSubject) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageSubject = messageSubject;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        if (accessor.getCommand() == null) return message;

        switch (accessor.getCommand()) {
            case SUBSCRIBE: {
                String destination = accessor.getDestination();
                if (destination != null && destination.startsWith("/topic/conversation/")) {
                    String idConversation = destination.substring("/topic/conversation/".length());
                    System.out.println("SUBSCRIBE - sessionId: " + sessionId + ", idConversation: " + idConversation);

                    // Nếu chưa có observer cho sessionId này, tạo mới
                    sessionObserverMap.computeIfAbsent(sessionId, sid ->
                            new WebSocketMessageObserve(sessionId, simpMessagingTemplate, destination)
                    );

                    MessageObserve observer = sessionObserverMap.get(sessionId);

                    // Lấy danh sách conversation đã đăng ký
                    Set<String> conversations = sessionConversationsMap.computeIfAbsent(sessionId, k -> ConcurrentHashMap.newKeySet());

                    // Nếu observer chưa đăng ký vào conversationId thì thêm
                    if (!conversations.contains(idConversation)) {
                        messageSubject.addObserver(idConversation, observer);
                        conversations.add(idConversation);
                        System.out.println("➕ Observer added to conversation: " + idConversation);
                    } else {
                        System.out.println("✅ Already subscribed to conversation: " + idConversation);
                    }
                }
                break;
            }

            case DISCONNECT: {
                System.out.println("DISCONNECT - sessionId: " + sessionId);
                MessageObserve observer = sessionObserverMap.remove(sessionId);
                Set<String> conversations = sessionConversationsMap.remove(sessionId);

                if (observer != null && conversations != null) {
                    for (String conversationId : conversations) {
                        messageSubject.removeObserver(conversationId, observer);
                        System.out.println("➖ Removed observer from conversation: " + conversationId);
                    }
                } else {
                    System.out.println("⚠️ No observer/conversations found for sessionId: " + sessionId);
                }
                break;
            }

            default:
                break;
        }

        return message;
    }
}