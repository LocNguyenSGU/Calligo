package com.example.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBasicResponse {
    private int idAccount;
    private String firstName;
    private String lastName;
    private String imgAvatar;
}
