package com.example.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallMessage {
    private String type; // "offer", "answer", "ice-candidate", "reject", "end-call"
    private Long from;   // ID của người gửi
    private Long to;     // ID của người nhận
    private String sdp;  // SDP offer/answer nếu có
    private String candidate;
}
