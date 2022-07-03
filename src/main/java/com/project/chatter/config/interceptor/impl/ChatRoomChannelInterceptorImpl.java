package com.project.chatter.config.interceptor.impl;

import com.project.chatter.config.interceptor.ChatRoomChannelInterceptor;
import com.project.chatter.service.ChatService;
import com.project.chatter.web.exception.NotFoundError;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChatRoomChannelInterceptorImpl implements ChatRoomChannelInterceptor {

    private final ChatService chatService;

    public ChatRoomChannelInterceptorImpl(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            String currentUserEmail = headerAccessor.getUser().getName();
            long chatId = extractChatId(headerAccessor.getDestination())
                    .orElseThrow(() -> new NotFoundError("No chat found"));

            if (!chatService.isSubscriptionValid(chatId, currentUserEmail)) {
                throw new NotFoundError("No chat found");
            }
        }

        return message;
    }

    private Optional<Long> extractChatId(String destination) {
        Pattern pattern = Pattern.compile("/chat-room/(\\d+)/messages");
        Matcher matcher = pattern.matcher(destination);

        if (!matcher.find()) {
            return Optional.empty();
        }

        return Optional.of(Long.parseLong(matcher.group(1)));
    }
}
