package com.example.apigateway.dto.responseFetch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBasicResponse {
    private int idAccount;
    private String firstName;
    private String lastName;
    private String imgAvatar;
}
