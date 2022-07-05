package com.project.chatter.model.view.chat;

import java.time.LocalDateTime;

public record MessageView(Long id, Long chatId, String sender, String senderEmail, String message, LocalDateTime dateTimeSent) {

}
