package com.example.userservice.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int idUser;
    private String fullName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
}
