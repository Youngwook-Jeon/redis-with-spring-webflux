package com.young.redisspring.chat.config;

import com.young.redisspring.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;

@Configuration
public class ChatRoomSocketConfig {

    @Autowired
    private ChatRoomService chatRoomService;

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, WebSocketHandler> map = Map.of(
                "/chat", chatRoomService
        );
        return new SimpleUrlHandlerMapping(map, -1); // 다른 우선순위가 0인 컨트롤러들보다 먼저 적용
    }
}
