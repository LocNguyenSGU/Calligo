package com.example.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @Email
    @NotBlank(message = "Email khong duoc de trong")
    private String email;
    @NotBlank(message = "Password khong duoc de trong")
    @Size(min = 6, message = "Password it nhat 6 ki tu")
    private String password;
    @NotBlank(message = "RePassword khong duoc de trong")
    @Size(min = 6, message = "RePassword it nhat 6 ki tu")
    private String rePassword;

    public boolean isPasswordEquals() {
        return password.equals(rePassword);
    }
}
