package com.project.chatter.model.view.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ChatView {

    private Long id;
    private String names;
    @JsonProperty("email")
    private String otherUserEmail;
    private String lastMessage;
    private String sender;
    private LocalDateTime dateTimeSent;
    private boolean seen;

    public ChatView() {
    }

    public ChatView(Long id, String names, boolean seen) {
        this.id = id;
        this.names = names;
        this.seen = seen;
    }

    public Long getId() {
        return id;
    }

    public ChatView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNames() {
        return names;
    }

    public ChatView setNames(String names) {
        this.names = names;
        return this;
    }

    public String getOtherUserEmail() {
        return otherUserEmail;
    }

    public ChatView setOtherUserEmail(String otherUserEmail) {
        this.otherUserEmail = otherUserEmail;
        return this;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public ChatView setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public ChatView setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public LocalDateTime getDateTimeSent() {
        return dateTimeSent;
    }

    public ChatView setDateTimeSent(LocalDateTime dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
        return this;
    }

    public boolean isSeen() {
        return seen;
    }

    public ChatView setSeen(boolean seen) {
        this.seen = seen;
        return this;
    }
}
