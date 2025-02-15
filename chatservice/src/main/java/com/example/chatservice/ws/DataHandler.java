package com.example.chatservice.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
public class DataHandler extends TextWebSocketHandler {
    @Override
    public void handleMessage (WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        log.info("message: " + message);
        session.sendMessage(new TextMessage("HEllo Kbang"));
    }
}
