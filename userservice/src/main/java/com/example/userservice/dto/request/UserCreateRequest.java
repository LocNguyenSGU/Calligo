package com.example.userservice.dto.request;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    private String fullName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
}
