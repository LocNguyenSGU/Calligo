package com.example.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "message_read_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadStatus {
    @Id
    private String idMessageReadStatus;
    private String idConversation;
    private String idLastMessageSeen;
    private LocalDateTime timeSeen;
    private List<SeenInfo> seenBy; // Danh sách người đã xem tin nhắn đó

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeenInfo {
        private String idAccount; // ID của người đã seen
        private LocalDateTime timeSeen; // Thời gian họ seen tin nhắn
    }
}
