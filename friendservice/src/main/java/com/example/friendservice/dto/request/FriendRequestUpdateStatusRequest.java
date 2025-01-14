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
public class FriendRequestUpdateStatusRequest {
    @ValidEnum(enumClass = FriendRequestEnum.class, message = "Invalid action value")
    private String status;
}
