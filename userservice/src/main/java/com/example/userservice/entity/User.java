package com.example.userservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 255)
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_account")
    private Account account;
}