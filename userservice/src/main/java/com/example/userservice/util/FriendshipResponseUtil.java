package com.example.userservice.util;

import com.example.userservice.dto.response.FriendService.FriendshipResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class FriendshipResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static FriendshipResponse parseFriendshipResponse(Object object) {
        if (object instanceof Map) {
            Map<String, Object> responseMap = (Map<String, Object>) object;

            // Lấy giá trị code từ response
            int code = (int) responseMap.get("code");
            log.info("Received response code: {}", code);

            if (code == 200) {
                // Lấy phần "data"
                Object dataObject = responseMap.get("data");
                if (dataObject instanceof Map) {
                    FriendshipResponse friendshipResponse = objectMapper.convertValue(dataObject, FriendshipResponse.class);
                    log.info("Converted FriendshipResponse: {}", friendshipResponse);
                    return friendshipResponse;
                } else {
                    log.error("Unexpected data format: {}", dataObject);
                    throw new RuntimeException("Invalid data format from friendClient");
                }
            } else {
                log.error("FriendClient returned error code: {}", code);
                throw new RuntimeException("FriendClient API error, code: " + code);
            }
        } else {
            log.error("Unexpected response type: {}", object.getClass().getName());
            throw new RuntimeException("Invalid response type from friendClient");
        }
    }
}
