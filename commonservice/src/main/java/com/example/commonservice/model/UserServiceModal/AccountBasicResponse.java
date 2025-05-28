package com.example.commonservice.model.UserServiceModal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBasicResponse implements Serializable {
    private int idAccount;
    private String firstName;
    private String lastName;
    private String imgAvatar;
}
