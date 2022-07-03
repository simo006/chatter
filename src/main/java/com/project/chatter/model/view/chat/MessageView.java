package com.project.chatter.model.view.chat;

import java.time.LocalDateTime;

public class MessageView {

    private Long id;
    private String sender;
    private String senderEmail;
    private String message;
    private LocalDateTime dateTimeSent;

    public MessageView(Long id, String sender, String senderEmail, String message, LocalDateTime dateTimeSent) {
        this.id = id;
        this.sender = sender;
        this.senderEmail = senderEmail;
        this.message = message;
        this.dateTimeSent = dateTimeSent;
    }

    public Long getId() {
        return id;
    }

    public MessageView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public MessageView setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public MessageView setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageView setMessage(String message) {
        this.message = message;
        return this;
    }

    public LocalDateTime getDateTimeSent() {
        return dateTimeSent;
    }

    public MessageView setDateTimeSent(LocalDateTime dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
        return this;
    }
}
