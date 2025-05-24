package com.example.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipCreatedEvent {
    private String senderId;
    private String receiverId;
    private LocalDateTime acceptTime;
}
