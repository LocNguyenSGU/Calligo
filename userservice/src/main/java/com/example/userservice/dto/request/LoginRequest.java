package com.example.userservice.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email khong duoc de trong")
    @Email
    private String email;

    @NotBlank(message = "Password khong duoc de trong")
    @Size(min = 6, message = "Password it nhat 6 ki tu")
    private String password;
}
