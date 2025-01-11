package com.example.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OKMessage {
    private int code = 200;
    private String message;
    private HttpStatus status = HttpStatus.OK;

    public OKMessage(String message) {
        this.message = message;
    }
}
