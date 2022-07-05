package com.project.chatter.model.view.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChatDetailsView {

    private Long id;
    private List<String> members;
    private String name;
    @JsonProperty("email")
    private String otherUserEmail;
    private List<MessageView> messages;
    private List<String> seenBy;

    public ChatDetailsView(Long id, List<String> members, String name, List<MessageView> messages, List<String> seenBy) {
        this.id = id;
        this.members = members;
        this.name = name;
        this.messages = messages;
        this.seenBy = seenBy;
    }

    public Long getId() {
        return id;
    }

    public ChatDetailsView setId(Long id) {
        this.id = id;
        return this;
    }

    public List<String> getMembers() {
        return members;
    }

    public ChatDetailsView setMembers(List<String> members) {
        this.members = members;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChatDetailsView setName(String name) {
        this.name = name;
        return this;
    }

    public String getOtherUserEmail() {
        return otherUserEmail;
    }

    public ChatDetailsView setOtherUserEmail(String otherUserEmail) {
        this.otherUserEmail = otherUserEmail;
        return this;
    }

    public List<MessageView> getMessages() {
        return messages;
    }

    public ChatDetailsView setMessages(List<MessageView> messages) {
        this.messages = messages;
        return this;
    }

    public List<String> getSeenBy() {
        return seenBy;
    }

    public ChatDetailsView setSeenBy(List<String> seenBy) {
        this.seenBy = seenBy;
        return this;
    }
}
