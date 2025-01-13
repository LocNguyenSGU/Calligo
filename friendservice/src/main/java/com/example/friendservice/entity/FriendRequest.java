package com.example.friendservice.entity;

import com.example.friendservice.eenum.FriendRequestEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend_requests")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFriendRequest;
    @NotNull
    private int idAccountSent;
    @NotNull
    private int idAccountReceive;
    @Length(max = 120)
    private String pathAvartar = "";
    @NotNull
    @Size(max = 40)
    private String firstName;
    @NotNull
    @Size(max = 40)
    private String lastName;
    private String content = "";
    private LocalDateTime timeRequest;
    @Enumerated(EnumType.STRING)
    private FriendRequestEnum status;
}
