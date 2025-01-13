package com.example.friendservice.dto.request;

import com.example.commonservice.customEnum.ValidEnum;
import com.example.friendservice.eenum.FriendRequestEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendCreateRequest {
    @NotNull
    private int idAccountSent;

    @NotNull
    private int idAccountReceive;

    @Length(max = 120, message = "Đường dẫn avatar không được quá 120 ký tự")
    private String pathAvartar = "";

    @NotNull(message = "First name khong duoc bo trong")
    @Size(max = 40, message = "First name không được dài hơn 40 ký tự")
    private String firstName;

    @NotNull(message = "Last name khong duoc bo trong")
    @Size(max = 40, message = "Last name không được dài hơn 40 ký tự")
    private String lastName;

    @ValidEnum(enumClass = FriendRequestEnum.class)
    private FriendRequestEnum status;
}
