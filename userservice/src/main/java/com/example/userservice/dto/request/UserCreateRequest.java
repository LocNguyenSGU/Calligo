package com.example.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "Full name must not be empty")
    @Size(max = 40, message = "Full name must not exceed 40 characters")
    private String fullName;

    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    @NotNull(message = "Id account must not be empty")
    private int idAccount;
}