package com.project.chatter.model.view.chat;

import java.time.LocalDateTime;

public class ChatView {

    private Long id;
    private String names;
    private String lastMessage;
    private String sender;
    private LocalDateTime dateTimeSent;
    private boolean seen;

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
