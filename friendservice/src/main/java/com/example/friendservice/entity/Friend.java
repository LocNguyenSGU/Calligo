package com.example.friendservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFriend;
    @NotNull
    private int idAccountSent;
    private String pathAvartar = "";
    @NotNull
    @Size(max = 40)
    private String firstName;
    @NotNull
    @Size(max = 40)
    private String lastName;
    @NotNull
    private int idAccountReceive;
    @NotNull
    private LocalDateTime timeCreate;

    @JoinColumn(name="idFriendRequest")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private FriendRequest friendRequest;
}
