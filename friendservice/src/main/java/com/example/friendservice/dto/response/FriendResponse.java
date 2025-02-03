package com.example.friendservice.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendResponse {
    private int idFriend;
    private int idAccountSent;
    private int idAccountReceive;
    private String pathAvartar;
    private String firstName;
    private String lastName;
    private LocalDateTime timeCreate;
}
