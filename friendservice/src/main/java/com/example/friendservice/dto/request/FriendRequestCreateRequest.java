package com.example.friendservice.dto.request;

import com.example.commonservice.customEnum.ValidEnum;
import com.example.friendservice.eenum.FriendRequestEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestCreateRequest {

    @NotNull(message = "Id account sent không được de trong")
    private int idAccountSent;

    @NotNull(message = "Id account receive không được de trong")
    private int idAccountReceive;


    @Length(max = 120, message = "Đường dẫn avatar không được quá 120 ký tự")
    private String pathAvartar = "";

    @NotNull
    @Size(max = 40, message = "First name không được dài hơn 40 ký tự")
    private String firstName;

    @NotNull
    @Size(max = 40, message = "Last name không được dài hơn 40 ký tự")
    private String lastName;

    @Size(max = 200, message = "Nội dung không được dài quá 120 ký tự")
    private String content = "";

}

