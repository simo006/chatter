package com.project.chatter.service;

import com.project.chatter.model.view.chat.ChatDetailsView;
import com.project.chatter.model.view.chat.ChatView;
import com.project.chatter.model.view.chat.MessageView;
import com.project.chatter.model.view.chat.SeenChatView;

import java.util.List;

public interface ChatService {

    void populateChatRoomAndMessages();

    List<ChatView> getChats();

    ChatDetailsView getChat(Long chatId);

    MessageView sendMessage(Long chatId, String message, String senderEmail);

    boolean isSubscriptionValid(long chatId, String currentUserEmail);

    String getChatRoomSubscriptionName(long chatId);

    SeenChatView seenChat(Long chatId, String userEmail);
}
