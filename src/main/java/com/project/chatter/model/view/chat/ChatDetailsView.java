package com.project.chatter.model.view.chat;

import java.util.List;

public class ChatDetailsView {

    private List<String> members;
    private String name;
    private List<MessageView> messages;

    public ChatDetailsView(List<String> members, String name, List<MessageView> messages) {
        this.members = members;
        this.name = name;
        this.messages = messages;
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

    public List<MessageView> getMessages() {
        return messages;
    }

    public ChatDetailsView setMessages(List<MessageView> messages) {
        this.messages = messages;
        return this;
    }
}
