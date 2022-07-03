package com.project.chatter.service;

import com.project.chatter.model.dto.UserDetailsDto;
import com.project.chatter.model.view.chat.ChatDetailsView;
import com.project.chatter.model.view.chat.ChatView;
import com.project.chatter.model.view.chat.MessageView;

import java.util.List;

public interface ChatService {

    void populateChatRoomAndMessages();

    List<ChatView> getChats();

    ChatDetailsView getChat(Long chatId);

    MessageView sendMessage(Long chatId, String message, UserDetailsDto userDetailsDto);
}
