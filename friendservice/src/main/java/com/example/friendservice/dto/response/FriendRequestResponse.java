package com.example.friendservice.dto.response;

import com.example.friendservice.eenum.FriendRequestEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestResponse {
    private int idAccountSent;
    private String pathAvartar;
    private String firstName;
    private String lastName;
    private String content;
    private LocalDateTime timeRequest;
    private FriendRequestEnum status;
}
