package com.project.chatter.web.controller;

import com.project.chatter.model.dto.SendMessageDto;
import com.project.chatter.model.dto.UserDetailsDto;
import com.project.chatter.model.view.basic.SuccessView;
import com.project.chatter.model.view.chat.ChatDetailsView;
import com.project.chatter.model.view.chat.ChatView;
import com.project.chatter.model.view.chat.MessageView;
import com.project.chatter.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController extends BaseController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<SuccessView> getChats() {
        List<ChatView> chats = chatService.getChats();

        if (chats.isEmpty()) {
            return ResponseEntity.ok(okView("No chats found"));
        }

        return ResponseEntity.ok(okView("Extracted all chats", chats));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessView> getChat(@PathVariable("id") Long chatId) {
        ChatDetailsView chatDetailsView = chatService.getChat(chatId);

        if (chatDetailsView.getMessages().isEmpty()) {
            return ResponseEntity.ok(okView("No chat info found", chatDetailsView));
        }

        return ResponseEntity.ok(okView("Extracted chat info", chatDetailsView));
    }

    @MessageMapping("/chat-room/{id}")
    @SendTo("/chat-room/{id}/messages")
    public MessageView handleRequest(@DestinationVariable(value = "id") Long chatId,
                                     @Payload @Valid Message<SendMessageDto> message, Principal principal) {
        UserDetailsDto userDetailsDto = (UserDetailsDto) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        SendMessageDto messageDto = message.getPayload();

        return chatService.sendMessage(chatId, messageDto.getMessage(), userDetailsDto);

    @MessageMapping("/chat-room/{id}/seen")
    @SendTo("/chat-room/{id}/messages")
    public SeenChatView sendMessageNotificationToUsersInRoom(@DestinationVariable(value = "id") Long chatId, Principal principal) {
        return chatService.seenChat(chatId, principal.getName());
    }
}
