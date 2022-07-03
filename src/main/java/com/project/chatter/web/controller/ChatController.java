package com.project.chatter.web.controller;

import com.project.chatter.model.dto.SendMessageDto;
import com.project.chatter.model.view.basic.FieldErrorView;
import com.project.chatter.model.view.chat.ChatDetailsView;
import com.project.chatter.model.view.chat.ChatView;
import com.project.chatter.model.view.basic.SuccessView;
import com.project.chatter.model.view.chat.MessageView;
import com.project.chatter.service.ChatService;
import com.project.chatter.web.exception.IllegalArgumentError;
import com.project.chatter.web.exception.RequestBodyValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/{id}")
    public ResponseEntity<SuccessView> sendMessage(@RequestBody @Valid SendMessageDto sendMessageDto,
                                                   BindingResult bindingResult, @PathVariable("id") Long chatId) {
        if (bindingResult.hasErrors()) {
            throwRequestBodyValidationError(bindingResult);
        }

        return ResponseEntity.ok(okView("Chat data saved",
                chatService.sendMessage(chatId, sendMessageDto.getMessage())));
    }
}
